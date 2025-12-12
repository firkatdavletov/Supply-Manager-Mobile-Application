package org.example.project.features.base

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.Lifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.example.project.features.DefaultDialogComponent
import org.example.project.features.DialogComponent
import org.example.project.features.SnackBarManager
import org.example.project.features.utils.toUserMessage

abstract class BaseComponent<State : Reducer.ViewState, Event : Reducer.ViewEvent, Effect : Reducer.ViewEffect>(
    componentContext: ComponentContext,
    val initialState: State,
    private val reducer: Reducer<State, Event, Effect>,
    private val snackBarManager: SnackBarManager? = null
) : ComponentContext by componentContext {
    private val job = SupervisorJob()
    protected val coroutineScope = CoroutineScope(job + Dispatchers.IO)

    private val _state: MutableValue<State> = MutableValue(initialState)
    open val state: Value<State> = _state

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect: SharedFlow<Effect> = _effect

    private val dialogNavigation = SlotNavigation<DialogConfig>()

    private val _dialogSlot = childSlot(
            source = dialogNavigation,
            serializer = null,
            handleBackButton = true,
            childFactory = { config, _ ->
                DefaultDialogComponent(
                    title = "Dialog",
                    message = config.message,
                    onDismissed = dialogNavigation::dismiss,
                )
            }
        )

    open val dialogSlot: Value<ChildSlot<*, DialogComponent>> = _dialogSlot

    init {
        lifecycle.subscribe(
            object : Lifecycle.Callbacks {

                override fun onStart() {
                    onStarted()
                }

                override fun onResume() {
                    super.onResume()
                    initDataLoad()
                }

                override fun onStop() {
                    onStoped()
                }

                override fun onDestroy() {
                    onDispose()
                    super.onDestroy()
                }
            }
        )
    }

    abstract fun onEvent(event: Event)

    open fun onStarted() {}
    open fun initDataLoad() {}
    open fun onDispose() {}
    open fun onStoped() {}

    private fun sendEffect(effect: Effect) {
        coroutineScope.launch {
            _effect.emit(effect)
        }
    }

    protected fun setState(reducer: State.() -> State) {
        val newState = state.value.reducer()
        with(Dispatchers.Main) {
            _state.update { newState }
        }
    }

    protected fun reduce(event: Event) {
        setState {
            reducer.reduce(this, event)
        }
    }

    protected fun handleEvent(event: Event) {
        reducer.handleEvent(event)?.let {
            sendEffect(it)
        }
    }

    protected fun showAlertDialog(message: String?) {
        dialogNavigation.activate(
            DialogConfig(message ?: "Что-то пошло не так")
        )
    }

    protected fun showError(message: String?) {
        coroutineScope.launch {
            snackBarManager?.showError(message ?: "Что-то пошло не так")
        }
    }

    protected fun showThrowError(throwable: Throwable) {
        coroutineScope.launch {
            snackBarManager?.showError(throwable.toUserMessage())
        }
    }

    @Serializable
    private data class DialogConfig(
        val message: String,
    )
}