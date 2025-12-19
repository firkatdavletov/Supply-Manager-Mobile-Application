package org.example.project.features.map

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.domain.models.AddressModel
import org.example.project.domain.models.DeliveryInfoModel
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.DepartmentModel
import org.example.project.domain.models.GeoAddressModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.CartRepository
import org.example.project.domain.usecase.cart.CreateCartUseCase
import org.example.project.domain.usecase.cart.LoadCartUseCase
import org.example.project.domain.usecase.cart.UpdateDeliveryAddressUseCase
import org.example.project.domain.usecase.departments.GetDepartmentsUseCase
import org.example.project.domain.usecase.geo.GetGeoAddressUseCase
import org.example.project.features.SnackBarManager
import org.example.project.features.home.HomeComponent
import org.example.project.features.launch.LaunchComponent
import org.example.project.features.map.MapViewEvent.OnBackClicked
import org.example.project.features.map.MapViewEvent.OnCartLoaded
import org.example.project.features.map.MapViewEvent.OnChangeDeliveryType
import org.example.project.features.map.MapViewEvent.OnConfirm
import org.example.project.features.map.MapViewEvent.OnMapMoved
import org.example.project.features.map.MapViewEvent.OnMoveToLocation
import org.example.project.features.map.MapViewEvent.OnSearchAddressClicked
import org.example.project.features.map.MapViewEvent.OnShowDepartments
import org.example.project.features.payment.PaymentComponent
import org.example.project.features.utils.DistanceCalculator

class DefaultMapComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val callbacks: MapCallbacks,
    private val getGeoAddressUseCase: GetGeoAddressUseCase,
    private val updateDeliveryAddressUseCase: UpdateDeliveryAddressUseCase,
    private val cartRepository: CartRepository,
    private val createCartUseCase: CreateCartUseCase,
    private val loadCartUseCase: LoadCartUseCase,
    private val getDepartmentsUseCase: GetDepartmentsUseCase,
    private val fromScreen: String?,
) : MapComponent(
    componentContext = componentContext,
    initialState = MapViewState(
        isLoading = true,
        isSearching = true,
        isError = false,
        deliveryType = DeliveryType.DELIVERY,
        city = null,
        deliveryAddress = null,
        cartDepartment = null,
        deliveryInfo = null,
        departments = emptyList(),
        currentPosition = null,
        confirmEnabled = false,
        showLocation = false,
        selectedDepartment = null,
        showBackButton = fromScreen != LaunchComponent::class.simpleName,
        showSearchButton = fromScreen == LaunchComponent::class.simpleName,
        errorMessage = null
    ),
    snackBarManager = snackBarManager,
) {
    private var job: Job? = null
    private var mapIsMoving = false
    private var selectedDeliveryAddress: AddressModel? = null
    private var deliveryInfo: DeliveryInfoModel? = null
    private var departments = emptyList<DepartmentModel>()


    init {
        subscribeToCartSubject()
        loadDepartments()
    }

    private fun loadDepartments() {
        coroutineScope.launch {
            getDepartmentsUseCase.invoke(Unit)
                .catch {
                    onEvent(MapViewEvent.OnThrowError(it))
                }
                .collect {
                    departments = it
                    onEvent(OnShowDepartments(it))
                }
        }
    }

    override fun onEvent(event: MapViewEvent) {
        when (event) {
            is OnCartLoaded -> reduce(event)
            is OnMapMoved -> {
                if (!mapIsMoving) {
                    mapIsMoving = true
                    reduce(event)
                }
                if (event.finished) {
                    mapIsMoving = false
                    if (state.value.deliveryType == DeliveryType.DELIVERY) {
                        findAddress(event.latitude, event.longitude)
                    }
                }
            }

            is OnShowDepartments -> reduce(event)

            is OnMoveToLocation -> {
                reduce(event)
                handleMovingToLocation(event.latitude, event.longitude)
            }

            is OnChangeDeliveryType -> {
                reduce(event)
                handleDeliveryType(event.type)
            }

            is OnConfirm -> {
                reduce(event)
                confirm()
            }

            OnBackClicked -> callbacks.navigateBack()

            OnSearchAddressClicked -> {
                if (state.value.deliveryType == DeliveryType.DELIVERY) {
                    callbacks.navigateToSearchAddress(fromScreen)
                }
            }

            is MapViewEvent.OnDepartmentSelected -> {
                deliveryInfo = DeliveryInfoModel(0.0, null)
                reduce(event)
            }

            is MapViewEvent.OnFoundAddress -> {
                val addressModel = AddressModel(
                    street = event.address.street,
                    house = event.address.house,
                    entrance = event.address.entrance,
                    city = event.address.city,
                    latitude = event.address.latitude,
                    longitude = event.address.longitude
                )
                selectedDeliveryAddress = addressModel
                deliveryInfo = event.address.deliveryInfo
                reduce(event)
            }

            is MapViewEvent.OnThrowError -> {
                showThrowError(event.throwable)
                reduce(event)
            }

            MapViewEvent.OnDefaultError -> {
                showError(null)
                handleEvent(event)
            }

            is MapViewEvent.OnFindAddressError -> reduce(event)

            MapViewEvent.OnLoading -> reduce(event)

            is MapViewEvent.OnError -> {
                showError(event.message)
                reduce(event)
            }
        }
    }

    private fun handleMovingToLocation(latitude: Double, longitude: Double) {
        if (state.value.deliveryType == DeliveryType.DELIVERY) {
            findAddress(latitude, longitude)
        }
    }

    private fun findAddress(latitude: Double, longitude: Double) {
        job?.cancel()
        job = coroutineScope.launch {
            val params = GetGeoAddressUseCase.Params(longitude, latitude)
            getGeoAddressUseCase.invoke(params)
                .catch { throwable ->
                    when (throwable) {
                        is CancellationException -> {}
                        else -> {
                            onEvent(MapViewEvent.OnThrowError(throwable))
                        }
                    }
                }
                .collect { result ->
                    when (result) {
                        ResultModel.Loading -> {}
                        is ResultModel.Error -> {
                            withContext(Dispatchers.Main) {
                                onEvent(MapViewEvent.OnFindAddressError(result.message ?: "Что-то пошло не так"))
                            }
                        }

                        is ResultModel.Success<GeoAddressModel> -> {
                            withContext(Dispatchers.Main) {
                                onEvent(MapViewEvent.OnFoundAddress(result.data))
                            }
                        }
                    }
                }
        }
    }

    private fun subscribeToCartSubject() {
        coroutineScope.launch {
            cartRepository.cartSubject.collect { cart ->
                onEvent(OnCartLoaded(cart))
            }
        }
    }

    private fun handleDeliveryType(deliveryType: DeliveryType) {
        when (deliveryType) {
            DeliveryType.PICKUP -> {}
            DeliveryType.DELIVERY -> {
                val currentPosition = state.value.currentPosition
                if (currentPosition != null) {
                    findAddress(currentPosition.latitude, currentPosition.longitude)
                }
            }
        }
    }

    private fun confirm() {
        if (fromScreen == LaunchComponent::class.simpleName) {
            createCart()
        } else {
            updateCart()
        }
    }

    private fun updateCart() {
        coroutineScope.launch {
            val deliveryType = state.value.deliveryType
            val deliveryAddress = selectedDeliveryAddress
            val cartDepartment = state.value.cartDepartment

            val params = when (deliveryType) {
                DeliveryType.PICKUP -> {
                    if (cartDepartment != null) {
                        UpdateDeliveryAddressUseCase.Params(
                            deliveryType = DeliveryType.PICKUP,
                            departmentId = cartDepartment.id,
                            deliveryInfo = DeliveryInfoModel(0.0, 0.0),
                        )

                    } else {
                        null
                    }
                }

                DeliveryType.DELIVERY -> {
                    if (deliveryAddress != null) {
                        UpdateDeliveryAddressUseCase.Params(
                            deliveryType = DeliveryType.DELIVERY,
                            deliveryAddress = deliveryAddress,
                            deliveryInfo = deliveryInfo ?: return@launch,
                            departmentId = departments.first().id
                        )
                    } else {
                        null
                    }
                }
            }

            if (params == null) {
                onEvent(MapViewEvent.OnDefaultError)
            } else {
                updateDeliveryAddressUseCase.invoke(params)
                    .catch {
                        onEvent(MapViewEvent.OnThrowError(it))
                    }
                    .collect { resultModel ->
                        when (resultModel) {
                            is ResultModel.Error -> {}
                            ResultModel.Loading -> {}
                            is ResultModel.Success<Boolean> -> {
                                if (resultModel.data) {
                                    when (fromScreen) {
                                        HomeComponent::class.simpleName -> {
                                            withContext(Dispatchers.Main) {
                                                callbacks.navigateToHome()
                                            }
                                        }

                                        PaymentComponent::class.simpleName -> {
                                            withContext(Dispatchers.Main) {
                                                callbacks.navigateToPayment()
                                            }
                                        }

                                        else -> {
                                            withContext(Dispatchers.Main) {
                                                callbacks.navigateToHome()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }

    private fun createCart() {
        val deliveryType = state.value.deliveryType
        val deliveryInfo = deliveryInfo
        val deliveryAddress = selectedDeliveryAddress
        val cartDepartment = state.value.cartDepartment
        val params = when (deliveryType) {
            DeliveryType.PICKUP -> {
                if (cartDepartment != null) {
                    CreateCartUseCase.Params(
                        departmentId = cartDepartment.id,
                    )
                } else {
                    null
                }
            }

            DeliveryType.DELIVERY -> {
                if (deliveryAddress != null && deliveryInfo != null) {
                    CreateCartUseCase.Params(
                        deliveryAddress = deliveryAddress,
                        deliveryInfo = deliveryInfo,
                        departmentId = cartDepartment?.id ?: 1
                    )
                } else {
                    null
                }
            }
        }
        if (params == null) return
        coroutineScope.launch {
            createCartUseCase.invoke(params)
                .catch {
                    onEvent(MapViewEvent.OnThrowError(it))
                }
                .collect { result ->
                    when (result) {
                        is ResultModel.Error -> {
                            onEvent(MapViewEvent.OnError(result.message))
                        }

                        ResultModel.Loading -> {
                            onEvent(MapViewEvent.OnLoading)
                        }

                        is ResultModel.Success<*> -> {
                            loadCart()
                        }
                    }
                }
        }
    }

    private suspend fun loadCart() {
        loadCartUseCase(Unit)
            .catch {
                onEvent(MapViewEvent.OnThrowError(it))
            }
            .collect { result ->
                withContext(Dispatchers.Main) {
                    when (result) {
                        is ResultModel.Error -> {
                            onEvent(MapViewEvent.OnError(result.message))
                        }

                        ResultModel.Loading -> {
                            onEvent(MapViewEvent.OnLoading)
                        }

                        is ResultModel.Success<Boolean> -> {
                            callbacks.navigateToHome()
                        }
                    }
                }
            }
    }

    private fun findClosestDepartment(
        lat: Double,
        lon: Double,
        departments: List<DepartmentModel>
    ): DepartmentModel? {
        return departments.minByOrNull { department ->
            DistanceCalculator.haversineDistance(lat, lon, department.latitude, department.longitude)
        }
    }
}
