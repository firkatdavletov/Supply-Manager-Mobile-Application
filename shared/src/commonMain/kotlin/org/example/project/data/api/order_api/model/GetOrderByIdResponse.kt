package org.example.project.data.api.order_api.model

import kotlinx.serialization.Serializable
import org.example.project.data.api.ResponseModel
import org.example.project.data.entities.OrderEntity

@Serializable
data class GetOrderByIdResponse(
    val order: OrderEntity?,
    override val success: Boolean,
    override val error: String?,
    override val code: Int?
) : ResponseModel
