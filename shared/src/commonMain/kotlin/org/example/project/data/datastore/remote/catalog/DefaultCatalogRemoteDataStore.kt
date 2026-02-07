package org.example.project.data.datastore.remote.catalog

import org.example.project.data.api.catalog.CatalogApi
import org.example.project.data.api.catalog.model.GetCatalogResponseBody
import org.example.project.data.api.catalog.model.GetCategoryResponseBody
import org.example.project.data.api.catalog.model.GetProductResponseBody

class DefaultCatalogRemoteDataStore(
    private val catalogApi: CatalogApi,
) : CatalogRemoteDataStore {
    override suspend fun getCategories(): GetCatalogResponseBody {
        return catalogApi.getCategories()
    }

    override suspend fun getProduct(id: Int): GetProductResponseBody {
        return catalogApi.getProduct(id)
    }

    override suspend fun getCategory(id: Long): GetCategoryResponseBody {
        return catalogApi.getCategory(id)
    }
}