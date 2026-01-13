package org.example.project.features.authorization.verification_component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.AuthRepository
import org.example.project.domain.repositories.OrderRepository
import org.example.project.domain.usecase.auth.VerifyCodeUseCase
import org.example.project.domain.usecase.user.LoadUserUseCase
import org.example.project.features.SnackBarManager
import org.example.project.features.cart.CartComponent
import org.example.project.features.home.HomeComponent

class DefaultVerificationComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val verifyCodeUseCase: VerifyCodeUseCase,
    private val loadUserUseCase: LoadUserUseCase,
    private val phoneNumber: String,
    private val authType: String,
    private val callbacks: VerifyCallbacks,
    private val fromScreen: String?,
    private val checkId: String?,
    callPhone: String?,
    private val orderRepository: OrderRepository,
    private val authRepository: AuthRepository,
) : VerificationComponent(
    componentContext = componentContext,
    snackBarManager = snackBarManager,
    initialState = VerifyViewState(
        isLoading = false,
        authType = authType,
        callPhone = callPhone,
        phoneNumber = phoneNumber,
        code = "",
        confirmEnabled = false
    )
) {
    private var _callNumberClicked = false
    private var socketIsConnected = false
    private var job: Job? = null

    init {
        coroutineScope.launch {
            println("[DefaultVerificationComponent] : subscribe")
            subscribeToUpdates()
        }
    }

    override fun initDataLoad() {}

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
            VerifyViewEvent.OnCallPhoneClicked -> {
                _callNumberClicked = true
                reduce(event)
            }
            VerifyViewEvent.OnAppBecameActive -> onAppBecameActive()
        }
    }

    override fun onStarted() {
        println("[DefaultVerificationComponent] : onStarted type: $authType, checkId: $checkId, $_callNumberClicked")
        if (authType == "call" && checkId != null && _callNumberClicked && !socketIsConnected) {
            job?.cancel()
            job = coroutineScope.launch {
                println("[DefaultVerificationComponent] : connect")
                authRepository.connect(checkId)
                socketIsConnected = true
            }
        }
    }

    override fun onStoped() {
        println("[DefaultVerificationComponent] : onStoped")
        coroutineScope.launch {
            authRepository.disconnect()
            job?.cancel()
            job = null
            socketIsConnected = false
        }
    }

    private fun onAppBecameActive() {

    }

    private suspend fun subscribeToUpdates() {
        authRepository.updates
            .flowOn(Dispatchers.IO)
            .collect {
                if (it && checkId != null) {
                    loadUser()
                }
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
                withContext(Dispatchers.Main) {
                    onEvent(VerifyViewEvent.OnThrowError(it))
                }
            }
            .collect { resultModel ->
                when (resultModel) {
                    is ResultModel.Error -> {
                        withContext(Dispatchers.Main) {
                            onEvent(VerifyViewEvent.OnError(resultModel.message))
                        }
                    }
                    ResultModel.Loading -> {}
                    is ResultModel.Success<Boolean> -> {
                        if (resultModel.data) {
                            orderRepository.connect()
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