package org.example.project.data.api.catalog.model

import kotlinx.serialization.Serializable
import org.example.project.data.api.ResponseModel

@Serializable
class GetProductsResponseBody(
    val products: List<ProductDto>,
    override val success: Boolean,
    override val error: String?,
    override val code: Int?,
) : ResponseModel
