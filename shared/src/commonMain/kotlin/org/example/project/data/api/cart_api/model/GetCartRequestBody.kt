package org.example.project.data.api.cart_api.model

import kotlinx.serialization.Serializable

@Serializable
data class GetCartRequestBody(
    val deviceId: String,
)
