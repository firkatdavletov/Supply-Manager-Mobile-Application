package org.example.project.data.api.cart_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.entities.CartEntity

@Serializable
data class RemoveAllResponseBody(
    val cart: CartEntity
)