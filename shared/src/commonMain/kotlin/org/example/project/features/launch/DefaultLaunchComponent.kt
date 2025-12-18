package org.example.project.features.launch

import com.arkivanov.decompose.ComponentContext
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.data.HttpException
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.OrderRepository
import org.example.project.domain.usecase.cart.LoadCartUseCase
import org.example.project.domain.usecase.catalog.LoadCatalogUseCase
import org.example.project.domain.usecase.user.LoadUserUseCase
import org.example.project.features.SnackBarManager
import org.example.project.features.utils.toUserMessage

class DefaultLaunchComponent(
    componentContext: ComponentContext,
    private val loadUserUseCase: LoadUserUseCase,
    private val loadCatalogUseCase: LoadCatalogUseCase,
    private val loadCartUseCase: LoadCartUseCase,
    private val orderRepository: OrderRepository,
    private val callbacks: LaunchNavigationCallbacks,
    snackBarManager: SnackBarManager,
): LaunchComponent(
    componentContext = componentContext,
    initialState = LaunchViewState(
        isLoading = true,
        isError = false
    ),
    reducer = LaunchReducer(),
    snackBarManager = snackBarManager
) {

    override fun initDataLoad() {
        loadData()
    }

    override fun onEvent(event: LaunchViewEvent) {
        when (event) {
            is LaunchViewEvent.OnError -> {
                reduce(event)
                handleEvent(event)
            }
            LaunchViewEvent.OnReconnect -> {
                reduce(event)
                loadData()
            }
        }
    }

    private fun loadData() {
        coroutineScope.launch {
            loadUserUseCase(Unit)
                .catch {}
                .collect { resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {}
                        ResultModel.Loading -> {}
                        is ResultModel.Success<Boolean> -> {
                            if (resultModel.data) {
                                orderRepository.connect()
                            }
                        }
                    }
                }
        }
        coroutineScope.launch { loadProductData() }
    }

    private suspend fun loadProductData() {
        val catalogFlow = loadCatalogUseCase(Unit)
        val cartFlow = loadCartUseCase(Unit)
        val timerFlow = flow {
            delay(3_000)
            emit(Unit)
        }

        val combinedFlow = combine(cartFlow, catalogFlow, timerFlow) { cartResult, catalogResult, _ ->
            Pair(cartResult, catalogResult)
        }

        combinedFlow
            .catch { throwable ->
                if (throwable is ClientRequestException && throwable.response.status.value == 401) {
                    withContext(Dispatchers.Main) {
                        callbacks.navigateToSelectAddress()
                    }
                } else {
                    showError(throwable.toUserMessage())
                    onEvent(LaunchViewEvent.OnError)
                }
            }
            .collect { commonResult ->
                val cartResult = commonResult.first
                val catalogResult = commonResult.second

                if (catalogResult is ResultModel.Error) {
                    onEvent(LaunchViewEvent.OnError)
                    return@collect
                }

                when (cartResult) {
                    is ResultModel.Error -> {
                        if (cartResult.errorCode == 401) {
                            withContext(Dispatchers.Main) {
                                callbacks.navigateToSelectAddress()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                callbacks.navigateToSelectAddress()
                            }
                        }
                    }
                    ResultModel.Loading -> {}
                    is ResultModel.Success<Boolean> -> {
                        withContext(Dispatchers.Main) {
                            callbacks.navigateToHome()
                        }
                    }
                }
            }
    }
}
