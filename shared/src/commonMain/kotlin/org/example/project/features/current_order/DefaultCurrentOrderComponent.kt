package org.example.project.features.current_order

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.OrderModel
import org.example.project.domain.models.OrderStatus
import org.example.project.domain.models.ResultModel
import org.example.project.domain.usecase.order.CancelOrderUseCase
import org.example.project.domain.usecase.order.CompleteOrderUseCase
import org.example.project.domain.usecase.order.GetOrderByIdUseCase
import org.example.project.domain.usecase.order.PendingOrderUseCase
import org.example.project.domain.usecase.order.TakeOrderUseCase
import org.example.project.features.home.HomeComponent
import org.example.project.features.payment.PaymentComponent

class DefaultCurrentOrderComponent(
    componentContext: ComponentContext,
    private val fromScreen: String?,
    private val callbacks: CurrentOrderCallbacks,
    private val getOrderByIdUseCase: GetOrderByIdUseCase,
    private val takeOrderUseCase: TakeOrderUseCase,
    private val completeOrderUseCase: CompleteOrderUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase,
    private val pendingOrderUseCase: PendingOrderUseCase,
    private val orderId: Long,
) : CurrentOrderComponent(
    componentContext = componentContext,
    initialState = CurrentOrderViewState(
        number = "",
        deliveryType = DeliveryType.DELIVERY,
        addressString = "",
        status = OrderStatus.PENDING,
        items = emptyList(),
        totalAmount = 0,
        comment = "",
        createdAt = "",
        customerEmail = "",
        customerPhone = "",
        customerName = "",
        companyName = "",
        deliveryDate = "",
    )
) {

    override fun onStart() {
        super.onStart()
        initData()
    }

    override fun onEvent(event: CurrentOrderViewEvent) {
        when (event) {
            is CurrentOrderViewEvent.OnOrderLoaded -> reduce(event)
            CurrentOrderViewEvent.OnBackClicked -> {
                when (fromScreen) {
                    HomeComponent::class.simpleName -> {
                        callbacks.navigateToBack()
                    }

                    PaymentComponent::class.simpleName -> {
                        callbacks.navigateToHome()
                    }
                }
            }

            CurrentOrderViewEvent.OnCancelOrder -> cancelOrder()
            CurrentOrderViewEvent.OnCompleteOrder -> completeOrder()
            CurrentOrderViewEvent.OnTakeOrder -> takeOrder()
            CurrentOrderViewEvent.OnPendingOrder -> pendingOrder()
        }
    }

    private fun initData() {
        getCurrentOrder()
    }

    private fun getCurrentOrder() {
        coroutineScope.launch {
            getOrderByIdUseCase(orderId)
                .catch {

                }
                .collect { result ->
                    when (result) {
                        is ResultModel.Error -> {

                        }
                        ResultModel.Loading -> {

                        }
                        is ResultModel.Success<OrderModel> -> {
                            onEvent(CurrentOrderViewEvent.OnOrderLoaded(result.data))
                        }
                    }
                }
        }
    }

    private fun takeOrder() {
        coroutineScope.launch {
            takeOrderUseCase.invoke(orderId)
                .catch {  }
                .collect {  resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {}
                        ResultModel.Loading -> {}
                        is ResultModel.Success<OrderModel> -> {
                            onEvent(CurrentOrderViewEvent.OnOrderLoaded(resultModel.data))
                        }
                    }
                }
        }
    }

    private fun completeOrder() {
        coroutineScope.launch {
            completeOrderUseCase.invoke(orderId)
                .catch {  }
                .collect {  resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {}
                        ResultModel.Loading -> {}
                        is ResultModel.Success<OrderModel> -> {
                            onEvent(CurrentOrderViewEvent.OnOrderLoaded(resultModel.data))
                        }
                    }
                }
        }
    }

    private fun cancelOrder() {
        coroutineScope.launch {
            cancelOrderUseCase.invoke(orderId)
                .catch {  }
                .collect {  resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {}
                        ResultModel.Loading -> {}
                        is ResultModel.Success<OrderModel> -> {
                            onEvent(CurrentOrderViewEvent.OnOrderLoaded(resultModel.data))
                        }
                    }
                }
        }
    }

    private fun pendingOrder() {
        coroutineScope.launch {
            pendingOrderUseCase.invoke(orderId)
                .catch {  }
                .collect {  resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {}
                        ResultModel.Loading -> {}
                        is ResultModel.Success<OrderModel> -> {
                            onEvent(CurrentOrderViewEvent.OnOrderLoaded(resultModel.data))
                        }
                    }
                }
        }
    }
}
