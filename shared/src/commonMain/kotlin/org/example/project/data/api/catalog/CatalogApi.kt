package org.example.project.data.api.catalog

import org.example.project.data.api.catalog.model.GetCatalogResponseBody
import org.example.project.data.api.catalog.model.GetCategoryResponseBody
import org.example.project.data.api.catalog.model.GetProductResponseBody
import org.example.project.data.api.catalog.model.GetProductsResponseBody

interface CatalogApi {
    suspend fun getCategories(): GetCatalogResponseBody
    suspend fun getCategory(id: Long) : GetCategoryResponseBody
    suspend fun getProduct(productId: Long): GetProductResponseBody
    suspend fun getAllProducts(): GetProductsResponseBody
    suspend fun getProductsByCategory(categoryId: Long): GetProductsResponseBody
}
