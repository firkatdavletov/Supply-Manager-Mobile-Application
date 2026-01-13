package org.example.project.data.datastore.remote.catalog

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.example.project.data.api.catalog.CatalogApi
import org.example.project.data.api.catalog.model.GetCatalogResponseBody
import org.example.project.data.api.catalog.model.GetProductResponseBody
import org.example.project.data.entities.CategoryEntity
import org.example.project.data.entities.ProductEntity

class DefaultCatalogRemoteDataStore(
    private val catalogApi: CatalogApi,
): CatalogRemoteDataStore {
    override suspend fun getCatalog(): GetCatalogResponseBody {
        return catalogApi.getCatalog()
    }

    override suspend fun getProduct(id: Int): GetProductResponseBody {
        return catalogApi.getProduct(id)
    }

    override fun getProducts(categoryId: Long): Flow<Pair<CategoryEntity, List<ProductEntity>>> {
        return flow {
            emit(catalogApi.getProductsByCategory(categoryId))
        }.map {
            it.category to it.products
        }
    }
}