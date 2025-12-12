package org.example.project.domain.usecase.sbp_banks

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.BankInfoModel
import org.example.project.domain.repositories.SbpBanksRepository
import org.example.project.domain.usecase.base.IOUseCase

class GetSbpBanksUseCase(
    private val repository: SbpBanksRepository,
) : IOUseCase<GetSbpBanksUseCase.Params, List<BankInfoModel>>() {
    override fun execute(param: Params): Flow<List<BankInfoModel>> {
        return repository.getBanks(param.canStoreToken)
    }

    data class Params(val canStoreToken: Boolean)
}