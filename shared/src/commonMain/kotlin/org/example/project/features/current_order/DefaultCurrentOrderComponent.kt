package org.example.project.features.current_order

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.OrderModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.OrderRepository
import org.example.project.domain.usecase.order.GetOrderByIdUseCase

class DefaultCurrentOrderComponent(
    componentContext: ComponentContext,
    private val callbacks: CurrentOrderCallbacks,
    private val getOrderByIdUseCase: GetOrderByIdUseCase,
    private val orderId: Long,
    private val orderRepository: OrderRepository,
) : CurrentOrderComponent(
    componentContext = componentContext,
    initialState = CurrentOrderViewState(
        number = "",
        deliveryType = DeliveryType.DELIVERY,
        addressString = "",
        status = "",
        items = emptyList(),
        deliveryPrice = 0,
        totalAmount = 0,
        productsPrice = 0,
        comment = ""
    )
) {

    override fun onStarted() {
        super.onStarted()
        initData()
    }

    override fun onEvent(event: CurrentOrderViewEvent) {
        when (event) {
            is CurrentOrderViewEvent.OnOrderLoaded -> reduce(event)
            CurrentOrderViewEvent.OnBackClicked -> {
                callbacks.navigateToBack()
            }
        }
    }

    private fun initData() {
        coroutineScope.launch {
            orderRepository.ordersSubject.collect {
                val currentOrder = it.firstOrNull { orderModel -> orderModel.id == orderId }
                if (currentOrder != null) {
                    onEvent(CurrentOrderViewEvent.OnOrderLoaded(currentOrder))
                }
            }
        }
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
}