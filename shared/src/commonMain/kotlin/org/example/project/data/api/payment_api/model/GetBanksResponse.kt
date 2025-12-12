package org.example.project.data.api.payment_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.entities.BankInfoEntity

@Serializable
data class GetBanksResponse(
    val banks: List<BankInfoEntity>
)
