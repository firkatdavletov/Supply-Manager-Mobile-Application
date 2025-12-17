package org.example.project.features

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.example.project.features.base.IosComponent
import org.example.project.features.base.Reducer

class SnackBarManager: IosComponent {
    private val _messages: MutableSharedFlow<String> = MutableSharedFlow(0)

    val messages: MutableSharedFlow<String>
        get() = _messages

    suspend fun showError(message: String) {
        _messages.emit(message)
    }

    override fun observeEvents(onEvent: (String) -> Unit): () -> Unit {
        val job = CoroutineScope(Dispatchers.Main).launch {
            _messages.collect { onEvent(it) }
        }
        return { job.cancel() }
    }
}