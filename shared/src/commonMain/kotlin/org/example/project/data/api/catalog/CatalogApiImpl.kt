package org.example.project.data.api.catalog

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path
import org.example.project.data.api.catalog.model.GetCatalogResponseBody
import org.example.project.data.api.catalog.model.GetProductsResponseBody

class CatalogApiImpl(private val httpClient: HttpClient) : CatalogApi {
    override suspend fun getCatalog(): GetCatalogResponseBody {
        return httpClient.get("catalog/categories").body()
    }

    override suspend fun getProductsByCategory(categoryId: Long): GetProductsResponseBody {
        return httpClient.get {
            url {
                path("catalog/categories")
                parameters.append("categoryId", categoryId.toString())
            }
        }.body()
    }
}