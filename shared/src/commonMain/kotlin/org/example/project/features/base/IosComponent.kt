package org.example.project.features.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface IosComponent {
    // --- API для iOS подписки на события ---
    fun observeEvents(onEvent: (Reducer.ViewEffect) -> Unit): () -> Unit
}