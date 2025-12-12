package org.example.project.data.datastore.remote.catalog

import kotlinx.coroutines.flow.Flow
import org.example.project.data.api.catalog.model.GetCatalogResponseBody
import org.example.project.data.entities.CategoryEntity
import org.example.project.data.entities.ProductEntity

interface CatalogRemoteDataStore {
    suspend fun getCatalog(): GetCatalogResponseBody
    fun getProducts(categoryId: Long): Flow<Pair<CategoryEntity, List<ProductEntity>>>
}