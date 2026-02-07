package org.example.project.features.home

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.example.project.domain.models.OrderPreviewModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.OrderRepository
import org.example.project.domain.usecase.order.GetOrdersUseCase
import org.example.project.features.SnackBarManager

class DefaultHomeComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val homeCallbacks: HomeCallbacks,
    private val getOrdersUseCase: GetOrdersUseCase,
    private val orderRepository: OrderRepository,
): HomeComponent (
    componentContext = componentContext,
    snackBarManager = snackBarManager,
    initialState = HomeViewState(
        isLoading = true,
        orders = emptyList(),
        deliveredCount = 0,
        processingCount = 0,
        pendingCount = 0,
        cancelledCount = 0
    ),
    reducer = HomeReducer(),
) {
    override fun onEvent(event: HomeViewEvent) {
        when (event) {
            is HomeViewEvent.OnThrowError -> {
                reduce(event)
                showThrowError(event.throwable)
            }
            is HomeViewEvent.OnCurrentOrderLoaded -> {
                reduce(event)
            }

            is HomeViewEvent.OnOrderClicked -> {
                homeCallbacks.navigateToOrder(event.id)
            }

            HomeViewEvent.OnRefresh -> {
                getCurrentOrders()
            }

            HomeViewEvent.OnAddClicked -> {
                homeCallbacks.navigateToCatalog()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        getCurrentOrders()
    }

    private fun getCurrentOrders() {
        coroutineScope.launch {
            getOrdersUseCase.invoke(Unit)
                .catch {  }
                .collect { resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {}
                        ResultModel.Loading -> {}
                        is ResultModel.Success<List<OrderPreviewModel>> -> {
                            onEvent(HomeViewEvent.OnCurrentOrderLoaded(resultModel.data))
                        }
                    }
                }
        }
    }
}