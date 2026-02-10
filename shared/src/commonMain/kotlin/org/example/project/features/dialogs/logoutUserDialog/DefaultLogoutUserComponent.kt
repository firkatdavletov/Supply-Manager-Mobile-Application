package org.example.project.features.dialogs.logoutUserDialog

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.domain.models.ResultModel
import org.example.project.domain.usecase.user.LogoutUserUseCase
import org.example.project.features.SnackBarManager

class DefaultLogoutUserComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val callbacks: LogoutUserDialogCallbacks,
) : LogoutUserComponent(
        componentContext = componentContext,
        initialState = LogoutUserDialogViewState(false),
        reducer = LogoutUserDialogReducer(),
        snackBarManager = snackBarManager,
    ) {
    override fun onEvent(event: LogoutUserDialogViewEvent) {
        when (event) {
            LogoutUserDialogViewEvent.OnConfirm -> {
                confirm()
            }

            LogoutUserDialogViewEvent.OnDismiss -> {
                callbacks.onDismiss()
            }

            is LogoutUserDialogViewEvent.OnError -> {
                reduce(event)
                showError(event.error)
            }

            is LogoutUserDialogViewEvent.OnThrowError -> {
                reduce(event)
                showThrowError(event.throwable)
            }

            LogoutUserDialogViewEvent.OnLoading -> {
                reduce(event)
            }
        }
    }

    private fun confirm() {
        coroutineScope.launch {
            logoutUserUseCase
                .invoke(Unit)
                .catch {
                    withContext(Dispatchers.Main) {
                        onEvent(LogoutUserDialogViewEvent.OnThrowError(it))
                    }
                }.collect { resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {
                            withContext(Dispatchers.Main) {
                                onEvent(LogoutUserDialogViewEvent.OnError(resultModel.message ?: "Что-то пошло не так"))
                            }
                        }

                        ResultModel.Loading -> {
                            withContext(Dispatchers.Main) {
                                onEvent(LogoutUserDialogViewEvent.OnLoading)
                            }
                        }

                        is ResultModel.Success<Boolean> -> {
                            if (resultModel.data) {
                                withContext(Dispatchers.Main) {
                                    callbacks.onSuccess()
                                }
                            }
                        }
                    }
                }
        }
    }
}