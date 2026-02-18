package org.example.project.features.authorization.signInComponent

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.domain.models.ResultModel
import org.example.project.domain.usecase.auth.LoginByEmailUseCase
import org.example.project.domain.usecase.user.LoadUserUseCase
import org.example.project.features.SnackBarManager

class DefaultSignInComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val loginByEmailUseCase: LoginByEmailUseCase,
    private val loadUserUseCase: LoadUserUseCase,
    private val callbacks: SignInCallbacks,
) : SignInComponent(componentContext, snackBarManager) {

    override fun onEvent(event: SignInViewEvent) {
        when (event) {
            SignInViewEvent.OnBackClicked -> {
                callbacks.onBack()
            }

            is SignInViewEvent.OnLoginChanged -> {
                reduce(event)
            }

            is SignInViewEvent.OnPasswordChanged -> {
                reduce(event)
            }

            SignInViewEvent.OnLoginClicked -> {
                reduce(event)
                login()
            }

            is SignInViewEvent.OnError -> {
                reduce(event)
                showError(event.message)
            }

            SignInViewEvent.OnLoading -> {
                reduce(event)
            }

            is SignInViewEvent.OnThrowError -> {
                reduce(event)
                showThrowError(event.throwable)
            }
        }
    }

    private fun login() {
        val login = state.value.login.trim()
        val password = state.value.password

        if (login.isEmpty() || password.isEmpty()) {
            onEvent(SignInViewEvent.OnError("Введите логин и пароль"))
            return
        }
        val params = LoginByEmailUseCase.Params(email = login, password = password)
        coroutineScope.launch {
            loginByEmailUseCase
                .invoke(params)
                .catch {
                    onEvent(SignInViewEvent.OnThrowError(it))
                }.collect { isSuccess ->
                    withContext(Dispatchers.Main) {
                        if (isSuccess) {
                            loadUser()
                        } else {
                            onEvent(SignInViewEvent.OnError("Не удалось выполнить вход"))
                        }
                    }
                }
        }
    }

    private fun loadUser() {
        coroutineScope.launch {
            loadUserUseCase
                .invoke(Unit)
                .catch {
                    onEvent(SignInViewEvent.OnThrowError(it))
                }.collect { resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {
                            onEvent(SignInViewEvent.OnError(resultModel.message))
                        }

                        ResultModel.Loading -> {}

                        is ResultModel.Success<Boolean> -> {
                            withContext(Dispatchers.Main) {
                                if (resultModel.data) {
                                    callbacks.navigateToHome()
                                } else {
                                    onEvent(SignInViewEvent.OnError("Не удалось выполнить вход"))
                                }
                            }
                        }
                    }
                }
        }
    }
}