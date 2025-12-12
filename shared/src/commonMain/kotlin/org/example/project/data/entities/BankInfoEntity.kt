package org.example.project.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class BankInfoEntity(
    val bankName: String,
    val logoUrl: String,
    val schema: String,
    val packageName: String? = null,
)