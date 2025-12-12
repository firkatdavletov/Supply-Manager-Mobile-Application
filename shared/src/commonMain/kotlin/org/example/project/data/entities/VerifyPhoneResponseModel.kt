package org.example.project.data.entities

import kotlinx.serialization.Serializable
import org.example.project.data.api.ResponseModel

@Serializable
data class VerifyPhoneResponseModel(
    val access: String,
    val refresh: String,
)