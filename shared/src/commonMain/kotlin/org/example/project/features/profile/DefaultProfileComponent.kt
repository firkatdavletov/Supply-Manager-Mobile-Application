package org.example.project.features.profile

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.domain.models.ResultModel
import org.example.project.domain.models.UserModel
import org.example.project.domain.repositories.UserRepository
import org.example.project.domain.usecase.user.DeleteUserUseCase
import org.example.project.domain.usecase.user.LogoutUserUseCase
import org.example.project.domain.usecase.user.UpdateUserUseCase
import org.example.project.features.SnackBarManager
import org.example.project.features.utils.toUserMessage

class DefaultProfileComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val callbacks: ProfileCallbacks,
    private val userRepository: UserRepository,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
) : ProfileComponent(
    componentContext = componentContext,
    initialState = ProfileViewState(
        name = "",
        phone = "",
        isLoading = true
    ),
    reducer = ProfileViewReducer(),
    snackBarManager = snackBarManager,
) {
    private var _user: UserModel? = null

    override fun onStarted() {
        coroutineScope.launch {
            userRepository.userSubject.collect { userModel ->
                if (userModel == null) return@collect
                _user = userModel
                onEvent(ProfileViewEvent.OnUserLoaded(userModel))
            }
        }
    }
    override fun onEvent(event: ProfileViewEvent) {
        when (event) {
            ProfileViewEvent.OnBackClicked -> {
                callbacks.navigateBack()
            }
            ProfileViewEvent.OnDelete -> {
                reduce(event)
                delete()
            }
            ProfileViewEvent.OnSave -> {
                reduce(event)
                save()
            }
            ProfileViewEvent.OnLogout -> {
                reduce(event)
                logout()
            }
            is ProfileViewEvent.OnError -> {
                reduce(event)
                showError(event.error)
            }
            is ProfileViewEvent.OnThrowError -> {
                reduce(event)
                showThrowError(event.throwable)
            }
            is ProfileViewEvent.OnNameChanged -> reduce(event)
            is ProfileViewEvent.OnUserLoaded -> reduce(event)
        }
    }

    private fun logout() {
        coroutineScope.launch {
            logoutUserUseCase.invoke(Unit)
                .catch {
                    onEvent(ProfileViewEvent.OnThrowError(it))
                }
                .collect { resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {
                            onEvent(ProfileViewEvent.OnError(resultModel.message))
                        }
                        ResultModel.Loading -> {}
                        is ResultModel.Success<Boolean> -> {
                            if (resultModel.data) {
                                withContext(Dispatchers.Main) {
                                    callbacks.navigateBack()
                                }
                            } else {
                                onEvent(ProfileViewEvent.OnError(null))
                            }
                        }
                    }
                }
        }
    }

    private fun delete() {
        coroutineScope.launch {
            deleteUserUseCase.invoke(Unit)
                .catch {
                    onEvent(ProfileViewEvent.OnError(it.toUserMessage()))
                }
                .collect { resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {
                            onEvent(ProfileViewEvent.OnError(resultModel.message ?: "Что-то пошло не так"))
                        }
                        ResultModel.Loading -> {}
                        is ResultModel.Success<Boolean> -> {
                            if (resultModel.data) {
                                withContext(Dispatchers.Main) {
                                    callbacks.navigateBack()
                                }
                            }
                        }
                    }
                }
        }
    }

    private fun save() {
        val userModel = _user?.copy(name = state.value.name) ?: return

        coroutineScope.launch {
            updateUserUseCase.invoke(userModel)
                .catch {
                    onEvent(ProfileViewEvent.OnError(it.toUserMessage()))
                }
                .collect { resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {
                            onEvent(ProfileViewEvent.OnError(resultModel.message ?: "Что-то пошло не так"))
                        }
                        ResultModel.Loading -> {}
                        is ResultModel.Success<Boolean> -> {
                            if (resultModel.data) {
                                withContext(Dispatchers.Main) {
                                    callbacks.navigateBack()
                                }
                            }
                        }
                    }
                }
        }
    }
}