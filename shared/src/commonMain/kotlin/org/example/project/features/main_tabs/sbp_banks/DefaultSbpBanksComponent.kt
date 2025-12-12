package org.example.project.features.main_tabs.sbp_banks

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch
import org.example.project.domain.usecase.sbp_banks.GetSbpBanksUseCase

class DefaultSbpBanksComponent(
    componentContext: ComponentContext,
    private val getSbpBanksUseCase: GetSbpBanksUseCase,
    private val callbacks: SbpBanksCallbacks,
    qrLink: String,
    private val canStoreToken: Boolean,
) : SbpBanksComponent(
    componentContext = componentContext,
    initialState = SbpBanksViewState(
        isLoading = false,
        banks = emptyList(),
        qrLink = qrLink
    ),
    initialEffect = SbpBanksViewEffect.None,
    reducer = SbpBanksReducer()
) {
    init {
        initDataLoad()
    }
    override fun onEvent(event: SbpBanksViewEvent) {
        when (event) {
            is SbpBanksViewEvent.OnBanksLoaded -> reduce(event)
            is SbpBanksViewEvent.OnBackClicked -> {
                callbacks.navigateToBack()
            }

            is SbpBanksViewEvent.OnBankClicked -> {

            }
        }
    }

    override fun initDataLoad() {
        loadBanks()
    }

    private fun loadBanks() {
        coroutineScope.launch {
            val params = GetSbpBanksUseCase.Params(canStoreToken = canStoreToken)
            getSbpBanksUseCase.invoke(params).collect {
                onEvent(SbpBanksViewEvent.OnBanksLoaded(it))
            }
        }
    }
}