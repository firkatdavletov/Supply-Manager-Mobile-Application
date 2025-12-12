package org.example.project.features.map

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent
import org.example.project.features.base.IosComponent
import org.example.project.features.base.Reducer

abstract class MapComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    initialState: MapViewState,
) : BaseComponent<MapViewState, MapViewEvent, MapViewEffect>(
    componentContext = componentContext,
    initialState = initialState,
    reducer = MapReducer(),
    snackBarManager = snackBarManager,
), IosComponent {

    abstract override fun onEvent(event: MapViewEvent)

    override fun observeEvents(onEvent: (Reducer.ViewEffect) -> Unit): () -> Unit {
        val job = CoroutineScope(Dispatchers.Main).launch {
            effect.collect { onEvent(it) }
        }
        return { job.cancel() }
    }
}