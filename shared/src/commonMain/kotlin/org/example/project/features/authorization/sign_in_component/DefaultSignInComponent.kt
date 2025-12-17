package org.example.project.features.authorization.sign_in_component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.domain.models.AuthTypesModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.usecase.auth.GetAuthTypesUseCase
import org.example.project.domain.usecase.auth.SendVerificationCodeUseCase
import org.example.project.features.SnackBarManager

class DefaultSignInComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val getAuthTypesUseCase: GetAuthTypesUseCase,
    private val sendVerificationCodeUseCase: SendVerificationCodeUseCase,
    private val callbacks: SignInCallbacks,
    private val fromScreen: String?,
) : SignInComponent(componentContext, snackBarManager) {

    init {
        coroutineScope.launch {
            getAuthTypesUseCase.invoke(Unit)
                .catch {
                    onEvent(SignInViewEvent.OnThrowError(it))
                }
                .collect { result ->
                    withContext(Dispatchers.Main) {
                        when (result) {
                            is ResultModel.Error -> {
                                onEvent(SignInViewEvent.OnError(result.message))
                            }
                            ResultModel.Loading -> {
                                onEvent(SignInViewEvent.OnLoading)
                            }
                            is ResultModel.Success<*> -> {
                                onEvent(SignInViewEvent.OnGetAuthTypes((result.data as AuthTypesModel).types))
                            }
                        }
                    }
                }
        }
    }

    override fun initDataLoad() {

    }

    override fun onEvent(event: SignInViewEvent) {
        when (event) {
            SignInViewEvent.OnBackClicked -> {
                callbacks.onBack()
            }
            is SignInViewEvent.AuthTypeClicked -> {
                reduce(event)
                verify(state.value.phoneNumber)
            }
            is SignInViewEvent.LoginClicked -> {
                reduce(event)
                verify(event.phoneNumber)
            }
            is SignInViewEvent.OnGetAuthTypes -> {
                reduce(event)
            }

            is SignInViewEvent.OnPhoneNumberChanged -> reduce(event)
            is SignInViewEvent.OnShowMessage -> reduce(event)
            SignInViewEvent.OnSkipClicked -> callbacks.navigateToHome()
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

    private fun verify(phone: String) {
        val formattedPhone =  phone.filter { it.isDigit() }
        if (!validatePhoneNumber(formattedPhone)) {
            onEvent(SignInViewEvent.OnError("Введите правильный номер телефона: $formattedPhone"))
            return
        }
        when (state.value.selectedAuthType) {
            "sms" -> {
                sendVerificationCode("7$formattedPhone")
            }
        }
    }

    private fun sendVerificationCode(phone: String) {
        coroutineScope.launch {
            sendVerificationCodeUseCase.invoke(phone)
                .catch {
                    onEvent(SignInViewEvent.OnThrowError(it))
                }
                .collect { status ->
                    if (status) {
                        withContext(Dispatchers.Main) {
                            callbacks.navigateToVerify(
                                phone,
                                state.value.selectedAuthType,
                                fromScreen
                            )
                        }
                    } else {
                        onEvent(SignInViewEvent.OnError(null))
                    }
                }
        }
    }

    private fun validatePhoneNumber(phone: String): Boolean {
        if (phone.length != 10) return false

        phone.forEach {
            if (!it.isDigit()) return false
        }

        return true
    }
}