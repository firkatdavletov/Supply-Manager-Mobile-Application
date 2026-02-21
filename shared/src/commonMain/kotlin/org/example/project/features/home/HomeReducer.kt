package org.example.project.features.home

import org.example.project.domain.models.OrderStatus
import org.example.project.domain.models.UserRole
import org.example.project.features.base.Reducer

class HomeReducer() : Reducer<HomeViewState, HomeViewEvent, HomeViewEffect> {
    override fun reduce(
        state: HomeViewState,
        event: HomeViewEvent,
    ): HomeViewState {
        return when (event) {
            is HomeViewEvent.OnCurrentOrderLoaded -> {
                state.copy(
                    isLoading = false,
                    orders = event.orders,
                    deliveredCount = event.orders.count { it.status == OrderStatus.COMPLETED },
                    cancelledCount = event.orders.count { it.status == OrderStatus.CANCELLED },
                    pendingCount = event.orders.count { it.status == OrderStatus.PENDING },
                    processingCount = event.orders.count { it.status == OrderStatus.PROCESSING },
                )
            }

            is HomeViewEvent.OnRefresh -> {
                state.copy(
                    isLoading = true,
                )
            }

            is HomeViewEvent.OnUserLoaded -> {
                state.copy(
                    userName = event.user.name,
                    showSettingsButton = event.user.role == UserRole.ADMIN,
                )
            }

            else -> {
                state
            }
        }
    }

    override fun handleEvent(event: HomeViewEvent): HomeViewEffect? {
        return when (event) {
            else -> null
        }
    }
}