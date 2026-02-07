package org.example.project.features.base

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.Lifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
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

    init {
        lifecycle.subscribe(
            object : Lifecycle.Callbacks {
                override fun onCreate() {
                    this@BaseComponent.onCreate()
                }

                override fun onStart() {
                    this@BaseComponent.onStart()
                }

                override fun onResume() {
                    this@BaseComponent.onResume()
                }

                override fun onPause() {
                    this@BaseComponent.onPause()
                }

                override fun onStop() {
                    this@BaseComponent.onStop()
                }

                override fun onDestroy() {
                    this@BaseComponent.onDestroy()
                }
            }
        )
    }

    abstract fun onEvent(event: Event)
    protected suspend fun onEffect(effect: Effect) {}

    open fun onCreate() {}
    open fun onStart() {}
    open fun onResume() {}
    open fun onPause() {}
    open fun onStop() {}
    open fun onDestroy() {}

    protected fun setState(reducer: State.() -> State) {
        val newState = state.value.reducer()
        _state.update { newState }
    }

    protected fun reduce(event: Event) {
        setState {
            reducer.reduce(this, event)
        }
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
}