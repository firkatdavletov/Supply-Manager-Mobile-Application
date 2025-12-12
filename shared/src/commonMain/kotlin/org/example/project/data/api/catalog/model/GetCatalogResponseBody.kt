package org.example.project.data.api.catalog.model

import kotlinx.serialization.Serializable
import org.example.project.data.api.ResponseModel
import org.example.project.data.entities.CategoryEntity

@Serializable
data class GetCatalogResponseBody(
    val catalog: List<CategoryEntity>,
    override val success: Boolean,
    override val error: String?,
    override val code: Int?,
) : ResponseModel
