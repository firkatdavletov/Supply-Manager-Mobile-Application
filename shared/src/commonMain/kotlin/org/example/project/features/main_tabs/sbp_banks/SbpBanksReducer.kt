package org.example.project.features.main_tabs.sbp_banks

import org.example.project.features.base.Reducer

class SbpBanksReducer : Reducer<SbpBanksViewState, SbpBanksViewEvent, SbpBanksViewEffect> {
    override fun reduce(
        state: SbpBanksViewState,
        event: SbpBanksViewEvent
    ): SbpBanksViewState {
        return when (event) {
            is SbpBanksViewEvent.OnBanksLoaded -> state.copy(
                isLoading = false,
                banks = event.banks,
            )

            else -> state
        }
    }

    override fun handleEvent(event: SbpBanksViewEvent): SbpBanksViewEffect? {
        TODO("Not yet implemented")
    }
}