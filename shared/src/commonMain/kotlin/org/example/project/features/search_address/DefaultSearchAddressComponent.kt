package org.example.project.features.search_address

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.domain.models.AddressModel
import org.example.project.domain.models.DeliveryInfoModel
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.GeoAddressModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.CartRepository
import org.example.project.domain.usecase.cart.UpdateDeliveryAddressUseCase
import org.example.project.domain.usecase.departments.GetDepartmentsUseCase
import org.example.project.domain.usecase.geo.GetGeoAddressUseCase
import org.example.project.domain.usecase.geo.SearchAddressUseCase
import org.example.project.features.SnackBarManager
import org.example.project.features.home.HomeComponent
import org.example.project.features.map.MapComponent
import org.example.project.features.payment.PaymentComponent

class DefaultSearchAddressComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val fromScreen: String?,
    private val cartRepository: CartRepository,
    private val getDepartmentsUseCase: GetDepartmentsUseCase,
    private val searchAddressUseCase: SearchAddressUseCase,
    private val updateDeliveryAddressUseCase: UpdateDeliveryAddressUseCase,
    private val getGeoAddressUseCase: GetGeoAddressUseCase,
    private val callbacks: SearchAddressCallbacks
) : SearchAddressComponent(
    componentContext = componentContext,
    snackBarManager = snackBarManager,
    initialState = SearchAddressViewState(
        isLoading = false,
        isSearching = false,
        query = "",
        deliveryType = DeliveryType.PICKUP,
        departments = emptyList(),
        addresses = emptyList()
    ),
    reducer = SearchAddressReducer()
) {
    private var job: Job? = null
    private val searchFlow = MutableSharedFlow<String>()

    override fun onEvent(event: SearchAddressViewEvent) {
        when (event) {
            SearchAddressViewEvent.OnBackClicked -> callbacks.navigateBack()
            is SearchAddressViewEvent.OnQueryChanged -> {
                reduce(event)
                coroutineScope.launch {
                    searchFlow.emit(event.query)
                }
            }
            is SearchAddressViewEvent.OnCartLoaded -> {
                reduce(event)
            }
            is SearchAddressViewEvent.OnDepartmentsLoaded -> {
                reduce(event)
            }
            is SearchAddressViewEvent.OnSearchComplete -> {
                reduce(event)
            }

            is SearchAddressViewEvent.OnAddressClicked -> {
                if (event.address.uri == null) {
                    onEvent(SearchAddressViewEvent.OnError("Нет информации об адресе"))
                }
                getGeoAddress(event.address.uri!!, event.address.entrance)
            }

            SearchAddressViewEvent.OnMapClicked -> {
                if (fromScreen == MapComponent::class.simpleName) {
                    callbacks.navigateBack()
                } else {
                    callbacks.navigateToMap(fromScreen)
                }
            }

            is SearchAddressViewEvent.OnError -> {
                showError(event.message)
                reduce(event)
            }
            is SearchAddressViewEvent.OnThrowError -> {
                showThrowError(event.throwable)
                reduce(event)
            }
        }
    }

    override fun onCreate() {
        subscribeToCart()
        subscribeToSearch()
    }

    private fun subscribeToCart() {
        coroutineScope.launch {
            cartRepository.cartSubject.collect {
                onEvent(SearchAddressViewEvent.OnCartLoaded(it))
                if (it.deliveryType == DeliveryType.PICKUP) {
                    getDepartments()
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun subscribeToSearch() {
        coroutineScope.launch {
            searchFlow
                .debounce(500)
                .filter {  query ->
                    if (query.isBlank()) {
                        onEvent(SearchAddressViewEvent.OnSearchComplete(emptyList()))
                        false
                    } else {
                        true
                    }
                }
                .collect {
                    search(it)
                }
        }
    }

    private fun search(query: String) {
        job?.cancel()
        job = coroutineScope.launch {
            println("searching: $query")

            searchAddressUseCase.invoke(query)
                .catch {
                    onEvent(SearchAddressViewEvent.OnThrowError(it))
                }
                .collect { resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {
                            onEvent(SearchAddressViewEvent.OnError(resultModel.message))
                        }
                        ResultModel.Loading -> {}
                        is ResultModel.Success<List<GeoAddressModel>> -> {
                            onEvent(SearchAddressViewEvent.OnSearchComplete(resultModel.data))
                        }
                    }
                }
        }
    }

    private fun getDepartments() {
        coroutineScope.launch {
            getDepartmentsUseCase.invoke(Unit)
                .catch {
                    onEvent(SearchAddressViewEvent.OnThrowError(it))
                }
                .collect {
                    onEvent(SearchAddressViewEvent.OnDepartmentsLoaded(it))
                }
        }
    }

    private suspend fun updateAddress(geoAddress: GeoAddressModel) {
        val addressModel = AddressModel(
            street = geoAddress.street,
            house = geoAddress.house,
            entrance = geoAddress.entrance,
            city = geoAddress.city,
            latitude = geoAddress.latitude,
            longitude = geoAddress.longitude,
        )
        val params = UpdateDeliveryAddressUseCase.Params(
            deliveryType = DeliveryType.DELIVERY,
            deliveryAddress = addressModel,
            departmentId = 1,
            deliveryInfo = geoAddress.deliveryInfo ?: DeliveryInfoModel(0.0, 0.0)
        )
        updateDeliveryAddressUseCase.invoke(params)
            .catch {
                onEvent(SearchAddressViewEvent.OnThrowError(it))
            }
            .collect { resultModel ->
                when (resultModel) {
                    is ResultModel.Error -> {
                        onEvent(SearchAddressViewEvent.OnError(resultModel.message))
                    }
                    ResultModel.Loading -> {}
                    is ResultModel.Success<Boolean> -> {
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

    private fun getGeoAddress(uri: String, entrance: Int?) {
        val param = GetGeoAddressUseCase.Params(uri, entrance)
        coroutineScope.launch {
            getGeoAddressUseCase.invoke(param)
                .catch {
                    onEvent(SearchAddressViewEvent.OnThrowError(it))
                }
                .collect { resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {
                            onEvent(SearchAddressViewEvent.OnError(resultModel.message))
                        }
                        ResultModel.Loading -> {}
                        is ResultModel.Success<GeoAddressModel> -> {
                            updateAddress(resultModel.data)
                        }
                    }
                }
        }
    }
}