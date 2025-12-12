package org.example.project.data.api.cart_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.api.ResponseModel
import org.example.project.data.entities.CartEntity

@Serializable
class GetCartResponseBody(
    val cart: CartEntity?,
    override val success: Boolean,
    override val error: String?,
    override val code: Int?,
) : ResponseModel