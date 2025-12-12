package org.example.project.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class PaymentTypeEntity(
    val key: String,
    val title: String,
)