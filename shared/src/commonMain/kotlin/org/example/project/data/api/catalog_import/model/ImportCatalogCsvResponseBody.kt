package org.example.project.data.api.catalog_import.model

import kotlinx.serialization.Serializable
import org.example.project.data.api.ResponseModel

@Serializable
data class ImportCatalogCsvResponseBody(
    override val success: Boolean,
    override val error: String?,
    override val code: Int?,
    val message: String? = null,
    val details: String? = null,
) : ResponseModel
