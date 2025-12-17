package org.example.project.features.launch

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent
import org.example.project.features.base.IosComponent
import org.example.project.features.base.Reducer

abstract class LaunchComponent(
    initialState: LaunchViewState,
    reducer: LaunchReducer,
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
) : BaseComponent<LaunchViewState, LaunchViewEvent, LaunchViewEffect>(
    componentContext,
    initialState,
    reducer,
    snackBarManager,
) {
    abstract override fun onEvent(event: LaunchViewEvent)
    override val state: Value<LaunchViewState>
        get() = super.state
}
