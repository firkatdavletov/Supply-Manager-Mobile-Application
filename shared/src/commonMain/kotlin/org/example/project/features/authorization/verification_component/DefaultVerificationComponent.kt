package org.example.project.features.authorization.verification_component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.domain.models.ResultModel
import org.example.project.domain.usecase.auth.VerifyCodeUseCase
import org.example.project.domain.usecase.user.LoadUserUseCase
import org.example.project.features.SnackBarManager
import org.example.project.features.cart.CartComponent
import org.example.project.features.home.HomeComponent
import org.example.project.features.map.MapViewEvent

class DefaultVerificationComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val verifyCodeUseCase: VerifyCodeUseCase,
    private val loadUserUseCase: LoadUserUseCase,
    private val phoneNumber: String,
    private val authType: String,
    private val callbacks: VerifyCallbacks,
    private val fromScreen: String?,
) : VerificationComponent(
    componentContext = componentContext,
    snackBarManager = snackBarManager,
    initialState = VerifyViewState(
        isLoading = false,
        phoneNumber = phoneNumber,
        code = "",
        confirmEnabled = false
    )
) {

    override fun initDataLoad() {

    }

    override fun onEvent(event: VerifyViewEvent) {
        when (event) {
            is VerifyViewEvent.OnCodeChanged -> {
                reduce(event)
                handleCodeChanged(event.newValue)
            }
            is VerifyViewEvent.OnError -> {
                reduce(event)
                showError(event.message)
            }
            is VerifyViewEvent.OnThrowError -> {
                reduce(event)
                showThrowError(event.throwable)
            }
            VerifyViewEvent.OnBackClicked -> callbacks.onBack()
        }
    }

    private fun handleCodeChanged(code: String) {
        if (code.length == 4) {
            verifyCode(code)
        }
    }

    private fun verifyCode(code: String) {
        if (state.value.isLoading) return

        setState {
            copy(isLoading = true)
        }
        coroutineScope.launch {
            val params = VerifyCodeUseCase.Params(phoneNumber, code)

            verifyCodeUseCase.invoke(params)
                .catch {
                    onEvent(VerifyViewEvent.OnThrowError(it))
                }
                .collect {
                    if (it) {
                        loadUser()
                    } else {
                        onEvent(VerifyViewEvent.OnError(null))
                    }
                }
        }
    }

    private suspend fun loadUser() {
        loadUserUseCase.invoke(Unit)
            .catch {
                onEvent(VerifyViewEvent.OnThrowError(it))
            }
            .collect { resultModel ->
                when (resultModel) {
                    is ResultModel.Error -> {
                        onEvent(VerifyViewEvent.OnError(resultModel.message))
                    }
                    ResultModel.Loading -> {

                    }
                    is ResultModel.Success<Boolean> -> {
                        if (resultModel.data) {
                            withContext(Dispatchers.Main) {
                                when (fromScreen) {
                                    CartComponent::class.simpleName -> {
                                        callbacks.navigateToPayment()
                                    }
                                    HomeComponent::class.simpleName -> {
                                        callbacks.navigateToHome()
                                    }
                                    else -> {
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