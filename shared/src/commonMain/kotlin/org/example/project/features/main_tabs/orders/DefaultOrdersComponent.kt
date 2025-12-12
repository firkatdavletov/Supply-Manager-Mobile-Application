package org.example.project.features.main_tabs.orders

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.example.project.domain.usecase.order.GetOrdersUseCase

class DefaultOrdersComponent(
    componentContext: ComponentContext,
    private val getOrdersUseCase: GetOrdersUseCase,
    private val ordersCallbacks: OrdersCallbacks,
) : OrdersComponent(
    componentContext = componentContext,
    initialState = OrdersViewState(
        isLoading = true,
        orders = emptyList()
    )
) {
    override fun onEvent(event: OrdersViewEvent) {
        when (event) {
            is OrdersViewEvent.OnThrowError -> {
                reduce(event)
                showAlertDialog(event.throwable.message)
            }

            is OrdersViewEvent.OnOrdersLoaded -> {
                reduce(event)
            }
        }
    }

    override fun initDataLoad() {
        getOrders()
    }

    private fun getOrders() {
        coroutineScope.launch {
            getOrdersUseCase.invoke(Unit)
                .catch {
                    onEvent(OrdersViewEvent.OnThrowError(it))
                }
                .collect {
                    onEvent(OrdersViewEvent.OnOrdersLoaded(it))
                }
        }
    }
}