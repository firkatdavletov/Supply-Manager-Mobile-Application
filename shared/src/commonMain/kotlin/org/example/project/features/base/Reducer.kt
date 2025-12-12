package org.example.project.features.base

interface Reducer<State : Reducer.ViewState, Event : Reducer.ViewEvent, Effect : Reducer.ViewEffect> {

    interface ViewState
    interface ViewEvent
    interface ViewEffect

    fun reduce(state: State, event: Event): State
    fun handleEvent(event: Event): Effect?
}