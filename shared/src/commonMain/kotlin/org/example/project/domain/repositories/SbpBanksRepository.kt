package org.example.project.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.BankInfoModel

interface SbpBanksRepository {
    fun getBanks(canStoreToken: Boolean): Flow<List<BankInfoModel>>
}