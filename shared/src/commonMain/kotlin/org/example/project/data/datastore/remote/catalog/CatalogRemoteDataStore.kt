package org.example.project.data.datastore.remote.catalog

import kotlinx.coroutines.flow.Flow
import org.example.project.data.api.catalog.model.GetCatalogResponseBody
import org.example.project.data.api.catalog.model.GetCategoryResponseBody
import org.example.project.data.api.catalog.model.GetProductResponseBody
import org.example.project.data.entities.CategoryEntity
import org.example.project.data.entities.ProductEntity

interface CatalogRemoteDataStore {
    suspend fun getCategories(): GetCatalogResponseBody
    suspend fun getProduct(id: Int): GetProductResponseBody
    suspend fun getCategory(id: Long): GetCategoryResponseBody
}