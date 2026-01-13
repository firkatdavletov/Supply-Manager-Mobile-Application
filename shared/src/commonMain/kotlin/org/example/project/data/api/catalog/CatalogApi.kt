package org.example.project.data.api.catalog

import org.example.project.data.api.catalog.model.GetCatalogResponseBody
import org.example.project.data.api.catalog.model.GetProductResponseBody
import org.example.project.data.api.catalog.model.GetProductsResponseBody

interface CatalogApi {
    suspend fun getCatalog(): GetCatalogResponseBody
    suspend fun getProduct(productId: Int): GetProductResponseBody
    suspend fun getProductsByCategory(categoryId: Long): GetProductsResponseBody
}