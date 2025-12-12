package org.example.project.features.main_tabs.sbp_banks

import org.example.project.domain.models.BankInfoModel
import org.example.project.features.base.Reducer

sealed interface SbpBanksViewEvent : Reducer.ViewEvent {
    data object OnBackClicked : SbpBanksViewEvent
    data class OnBankClicked(val schema: String) : SbpBanksViewEvent
    data class OnBanksLoaded(val banks: List<BankInfoModel>) : SbpBanksViewEvent
}