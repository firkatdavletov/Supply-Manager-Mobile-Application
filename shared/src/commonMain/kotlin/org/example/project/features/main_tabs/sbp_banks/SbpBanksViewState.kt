package org.example.project.features.main_tabs.sbp_banks

import org.example.project.domain.models.BankInfoModel
import org.example.project.features.base.Reducer

data class SbpBanksViewState(
    val isLoading: Boolean,
    val banks: List<BankInfoModel>,
    val qrLink: String,
) : Reducer.ViewState
