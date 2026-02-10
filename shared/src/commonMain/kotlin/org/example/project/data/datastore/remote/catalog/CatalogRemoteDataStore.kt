package org.example.project.data.datastore.remote.catalog

import org.example.project.data.api.catalog.model.GetCatalogResponseBody
import org.example.project.data.api.catalog.model.GetCategoryResponseBody
import org.example.project.data.api.catalog.model.GetProductResponseBody

interface CatalogRemoteDataStore {
    suspend fun getCategories(): GetCatalogResponseBody

    suspend fun getProduct(id: Long): GetProductResponseBody

    suspend fun getCategory(id: Long): GetCategoryResponseBody
}