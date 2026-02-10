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
import org.example.project.domain.models.UnitOfMeasure
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
    private val clearCartUseCase: ClearCartUseCase,
) : PaymentComponent(
        componentContext = componentContext,
        initialState = PaymentViewState(
            isLoading = true,
            comment = "",
            totalAmount = 0,
        ),
        snackBarManager = snackBarManager,
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
            is PaymentViewEvent.OnCartChanged -> {
                reduce(event)
            }

            is PaymentViewEvent.OnPaymentTypesLoaded -> {
                reduce(event)
            }

            is PaymentViewEvent.OnChangeDeliveryType -> {
                reduce(event)
                updateDeliveryAddress(event.deliveryType)
            }

            PaymentViewEvent.OnBackButtonClicked -> {
                callbacks.navigateBack()
            }

            PaymentViewEvent.OnConfirmButtonClicked -> {
                createOrder()
            }

            PaymentViewEvent.OnChangeAddress -> {
                callbacks.navigateToMap()
            }

            is PaymentViewEvent.OnIsPrivateHouseChanged -> {
                reduce(event)
            }

            is PaymentViewEvent.OnEntranceInputError -> {
                reduce(event)
            }

            is PaymentViewEvent.OnFlatInputError -> {
                reduce(event)
            }

            is PaymentViewEvent.OnEntranceChanged -> {
                reduce(event)
            }

            is PaymentViewEvent.OnFlatChanged -> {
                reduce(event)
            }

            is PaymentViewEvent.OnCommentChanged -> {
                reduce(event)
            }

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
        getPaymentTypesUseCase
            .invoke(Unit)
            .catch {
                onEvent(PaymentViewEvent.OnThrowError(it))
            }.collect {
                onEvent(PaymentViewEvent.OnPaymentTypesLoaded(it))
            }
    }

    private fun createOrder() {
    }

    private fun updateDeliveryAddress(deliveryType: DeliveryType) {
    }
}