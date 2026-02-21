package org.example.project.features.home

import org.example.project.domain.models.OrderPreviewModel
import org.example.project.features.base.Reducer

data class HomeViewState(
    val isLoading: Boolean,
    val userName: String,
    val orders: List<OrderPreviewModel>,
    val deliveredCount: Int,
    val cancelledCount: Int,
    val pendingCount: Int,
    val processingCount: Int,
    val showSettingsButton: Boolean = false,
) : Reducer.ViewState