package org.example.project.features.payment

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.domain.models.AddressModel
import org.example.project.domain.models.CartItemModel
import org.example.project.domain.models.DeliveryInfoModel
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.OrderItemModel
import org.example.project.domain.models.OrderModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.CartRepository
import org.example.project.domain.usecase.cart.ClearCartUseCase
import org.example.project.domain.usecase.cart.UpdateDeliveryAddressUseCase
import org.example.project.domain.usecase.order.CreateOrderUseCase
import org.example.project.domain.usecase.payment.GetPaymentTypesUseCase
import org.example.project.features.SnackBarManager

class DefaultPaymentComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val callbacks: PaymentCallbacks,
    private val cartRepository: CartRepository,
    private val getPaymentTypesUseCase: GetPaymentTypesUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val updateCartAddressUseCase: UpdateDeliveryAddressUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : PaymentComponent(
    componentContext = componentContext,
    initialState = PaymentViewState(
        isLoading = true,
        deliveryType = DeliveryType.DELIVERY,
        addressString = null,
        departmentName = null,
        isPrivateHome = false,
        entrance = "",
        flat = "",
        comment = "",
        productPrice = 0,
        deliveryPrice = 0,
        totalAmount = 0,
        paymentTypes = emptyList(),
    ),
    snackBarManager = snackBarManager
) {
    private var cartItems: List<CartItemModel> = emptyList()
    private var deliveryAddress: AddressModel? = null
    private var departmentId: Int? = null
    private var deliveryInfoModel: DeliveryInfoModel? = null


    override fun onStart() {
        super.onStart()
        subscribeToCart()
    }

    override fun onEvent(event: PaymentViewEvent) {
        when (event) {
            is PaymentViewEvent.OnCartChanged -> reduce(event)
            is PaymentViewEvent.OnPaymentTypesLoaded -> reduce(event)
            is PaymentViewEvent.OnChangeDeliveryType -> {
                reduce(event)
                updateDeliveryAddress(event.deliveryType)
            }
            PaymentViewEvent.OnBackButtonClicked -> callbacks.navigateBack()
            PaymentViewEvent.OnConfirmButtonClicked -> createOrder()
            PaymentViewEvent.OnChangeAddress -> callbacks.navigateToMap()
            is PaymentViewEvent.OnIsPrivateHouseChanged -> reduce(event)
            is PaymentViewEvent.OnEntranceInputError -> reduce(event)
            is PaymentViewEvent.OnFlatInputError -> reduce(event)
            is PaymentViewEvent.OnEntranceChanged -> reduce(event)
            is PaymentViewEvent.OnFlatChanged -> reduce(event)
            is PaymentViewEvent.OnCommentChanged -> reduce(event)
            is PaymentViewEvent.OnError -> {
                reduce(event)
                showError(event.message)
            }
            is PaymentViewEvent.OnThrowError -> {
                reduce(event)
                showThrowError(event.throwable)
            }
        }
    }

    private fun subscribeToCart() {
        coroutineScope.launch {
            cartRepository.cartSubject.collect { cartModel ->
                withContext(Dispatchers.Main) {
                    onEvent(PaymentViewEvent.OnCartChanged(cartModel))
                }
                cartItems = cartModel.items
                deliveryAddress = cartModel.deliveryAddress
                departmentId = cartModel.department.id
                deliveryInfoModel = cartModel.deliveryInfo
                loadPaymentTypes()
            }
        }
    }

    private suspend fun loadPaymentTypes() {
        getPaymentTypesUseCase.invoke(Unit)
            .catch {
                onEvent(PaymentViewEvent.OnThrowError(it))
            }
            .collect {
                onEvent(PaymentViewEvent.OnPaymentTypesLoaded(it))
            }
    }

    private fun validateInput(): Boolean {
        return when (state.value.deliveryType) {
            DeliveryType.PICKUP -> true
            DeliveryType.DELIVERY -> {
                if (state.value.addressString == null) {
                    return false
                } else {
                    if (state.value.isPrivateHome) {
                        true
                    } else {
                        val entrance = validateEntrance()
                        val flat = validateFlat()
                        return entrance && flat
                    }
                }
            }
        }
    }

    private fun validateEntrance(): Boolean {
        return if (state.value.entrance.isBlank()) {
            onEvent(PaymentViewEvent.OnEntranceInputError("Введите подъезд"))
            onEvent(PaymentViewEvent.OnError("Введите номер подъезда"))
            false
        } else {
            true
        }
    }

    private fun validateFlat(): Boolean {
        return if (state.value.flat.isBlank()) {
            onEvent(PaymentViewEvent.OnFlatInputError("Введите номер квартиры"))
            onEvent(PaymentViewEvent.OnError("Введите номер квартиры"))
            false
        } else {
            true
        }
    }

    private fun createOrder() {
        if (!validateInput()) return

        val departmentId = departmentId ?: return
        coroutineScope.launch {
            val params = CreateOrderUseCase.Params(
                deliveryType = state.value.deliveryType,
                amount = state.value.totalAmount.toFloat(),
                deliveryPrice = state.value.deliveryPrice.toFloat(),
                products = cartItems.map { cartItemModel ->
                    OrderItemModel(
                        productId = cartItemModel.productId,
                        name = cartItemModel.title,
                        quantity = cartItemModel.quantity,
                        price = cartItemModel.price
                    )
                },
                deliveryAddress = deliveryAddress?.copy(
                    entrance = state.value.entrance.toIntOrNull(),
                    flat = state.value.flat
                ),
                comment = state.value.comment,
                departmentId = departmentId
            )
            createOrderUseCase.invoke(params)
                .catch {
                    onEvent(PaymentViewEvent.OnThrowError(it))
                }
                .collect { result ->
                    when (result) {
                        is ResultModel.Error -> {
                            PaymentViewEvent.OnError(result.message)
                        }
                        ResultModel.Loading -> {

                        }
                        is ResultModel.Success<OrderModel> -> {

                            val order = result.data

                            clearCartUseCase.invoke(Unit)
                                .catch {
                                    onEvent(PaymentViewEvent.OnThrowError(it))
                                }
                                .collect { result ->
                                    when (result) {
                                        is ResultModel.Error -> {
                                            onEvent(PaymentViewEvent.OnError(result.message))
                                        }
                                        ResultModel.Loading -> {}
                                        is ResultModel.Success<*> -> {
                                            updateDeliveryAddress(state.value.deliveryType)
                                            withContext(Dispatchers.Main) {
                                                callbacks.navigateToOrder(order.id)
                                            }
                                        }
                                    }
                                }

                        }
                    }
                }
        }
    }

    private fun updateDeliveryAddress(deliveryType: DeliveryType) {
        val params = when (state.value.deliveryType) {
            DeliveryType.PICKUP -> {
                val departmentId = departmentId ?: return
                UpdateDeliveryAddressUseCase.Params(
                    deliveryType = deliveryType,
                    deliveryAddress = null,
                    departmentId = departmentId,
                    comment = state.value.comment,
                    deliveryInfo = deliveryInfoModel!!
                )
            }
            DeliveryType.DELIVERY -> {
                val deliveryAddress = deliveryAddress ?: return
                val departmentId = departmentId ?: return
                UpdateDeliveryAddressUseCase.Params(
                    deliveryType = deliveryType,
                    deliveryAddress = deliveryAddress.copy(
                        entrance = state.value.entrance.toIntOrNull(),
                        flat = state.value.flat
                    ),
                    departmentId = departmentId,
                    comment = state.value.comment,
                    deliveryInfo = deliveryInfoModel!!
                )
            }
        }

        coroutineScope.launch {
            updateCartAddressUseCase.invoke(params)
                .catch {
                    onEvent(PaymentViewEvent.OnThrowError(it))
                }
                .collect { resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {
                            onEvent(PaymentViewEvent.OnError(resultModel.message))
                        }
                        ResultModel.Loading -> {}
                        is ResultModel.Success<Boolean> -> {
                            if (!resultModel.data) {
                                onEvent(PaymentViewEvent.OnError(null))
                            }
                        }
                    }
                }
        }
    }
}