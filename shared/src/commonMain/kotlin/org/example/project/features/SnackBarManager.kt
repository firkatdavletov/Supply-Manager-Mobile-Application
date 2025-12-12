package org.example.project.features

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class SnackBarManager {
    private val _messages: MutableSharedFlow<String> = MutableSharedFlow(0)

    val messages: MutableSharedFlow<String>
        get() = _messages

    suspend fun showError(message: String) {
        _messages.emit(message)
    }
}