package org.example.project.features.launch

import com.arkivanov.decompose.ComponentContext
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.OrderRepository
import org.example.project.domain.usecase.user.LoadUserUseCase
import org.example.project.features.SnackBarManager
import org.example.project.features.utils.toUserMessage

class DefaultLaunchComponent(
    componentContext: ComponentContext,
    private val loadUserUseCase: LoadUserUseCase,
    private val orderRepository: OrderRepository,
    private val callbacks: LaunchNavigationCallbacks,
    snackBarManager: SnackBarManager,
) : LaunchComponent(
        componentContext = componentContext,
        initialState = LaunchViewState(
            isLoading = true,
            isError = false,
        ),
        reducer = LaunchReducer(),
        snackBarManager = snackBarManager,
    ) {

    override fun onResume() {
        loadData()
    }

    override fun onEvent(event: LaunchViewEvent) {
        when (event) {
            is LaunchViewEvent.OnError -> {
                reduce(event)
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
                .catch { throwable ->
                    if (throwable is ClientRequestException && throwable.response.status.value == 401) {
                        withContext(Dispatchers.Main) {
                            callbacks.navigateToSignIn()
                        }
                    } else {
                        showError(throwable.toUserMessage())
                        withContext(Dispatchers.Main) {
                            callbacks.navigateToSignIn()
                        }
                    }
                }.collect { resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {
                            callbacks.navigateToSignIn()
                        }

                        ResultModel.Loading -> {}

                        is ResultModel.Success<Boolean> -> {
                            if (resultModel.data) {
                                callbacks.navigateToHome()
                            } else {
                                callbacks.navigateToSignIn()
                            }
                        }
                    }
                }
        }
    }
}