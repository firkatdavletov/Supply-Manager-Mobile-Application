package org.example.project.domain.models

import kotlinx.serialization.SerialName

data class RegisterSocketModel(
    val socket: String?,

    val telegramChanel: String?,

    val whatsappChanel: String?
)
