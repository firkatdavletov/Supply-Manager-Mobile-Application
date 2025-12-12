package org.example.project.features.main_tabs.sbp_banks

import org.example.project.features.base.Reducer

sealed interface SbpBanksViewEffect : Reducer.ViewEffect {
    data object None : SbpBanksViewEffect
}