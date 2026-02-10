package org.example.project.features.authorization.signInComponent

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.domain.models.AuthTypeModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.models.VerifyPhoneNumberModel
import org.example.project.domain.usecase.auth.GetAuthTypesUseCase
import org.example.project.domain.usecase.auth.VerifyPhoneNumberUseCase
import org.example.project.features.SnackBarManager

class DefaultSignInComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val getAuthTypesUseCase: GetAuthTypesUseCase,
    private val verifyPhoneNumberUseCase: VerifyPhoneNumberUseCase,
    private val callbacks: SignInCallbacks,
    private val fromScreen: String?,
) : SignInComponent(componentContext, snackBarManager) {

    init {
        coroutineScope.launch {
            getAuthTypesUseCase
                .invoke(Unit)
                .catch {
                    onEvent(SignInViewEvent.OnThrowError(it))
                }.collect { result ->
                    withContext(Dispatchers.Main) {
                        when (result) {
                            is ResultModel.Error -> {
                                onEvent(SignInViewEvent.OnError(result.message))
                            }

                            ResultModel.Loading -> {
                                onEvent(SignInViewEvent.OnLoading)
                            }

                            is ResultModel.Success<List<AuthTypeModel>> -> {
                                onEvent(SignInViewEvent.OnGetAuthTypes(result.data))
                            }
                        }
                    }
                }
        }
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

            is SignInViewEvent.OnPhoneNumberChanged -> {
                reduce(event)
            }

            is SignInViewEvent.OnShowMessage -> {
                reduce(event)
            }

            SignInViewEvent.OnSkipClicked -> {
                callbacks.navigateToHome()
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

    private fun verify(phone: String) {
        val formattedPhone = phone.filter { it.isDigit() }
        if (!validatePhoneNumber(formattedPhone)) {
            onEvent(SignInViewEvent.OnError("Введите правильный номер телефона: $formattedPhone"))
            return
        }
        when (val type = state.value.selectedAuthType) {
            "sms", "call" -> {
                verifyPhoneNumber("7$formattedPhone", type)
            }
        }
    }

    private fun verifyPhoneNumber(
        phone: String,
        type: String,
    ) {
        val params = VerifyPhoneNumberUseCase.Params(phone, type)
        coroutineScope.launch {
            verifyPhoneNumberUseCase
                .invoke(params)
                .catch {
                    onEvent(SignInViewEvent.OnThrowError(it))
                }.collect { resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {
                            onEvent(SignInViewEvent.OnError(resultModel.message))
                        }

                        ResultModel.Loading -> {}

                        is ResultModel.Success<VerifyPhoneNumberModel> -> {
                            if (resultModel.data.success) {
                                withContext(Dispatchers.Main) {
                                    callbacks.navigateToVerify(
                                        phone,
                                        type,
                                        fromScreen,
                                        resultModel.data.checkId,
                                        resultModel.data.callPhone,
                                    )
                                }
                            }
                        }
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