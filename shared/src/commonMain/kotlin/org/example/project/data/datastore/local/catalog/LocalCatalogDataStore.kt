package org.example.project.data.datastore.local.catalog

import kotlinx.coroutines.flow.Flow
import org.example.project.data.entities.CategoryEntity
import org.example.project.data.entities.ProductEntity

interface LocalCatalogDataStore {
    fun getCategories(): Flow<List<CategoryEntity>>
    fun saveCategories(categories: List<CategoryEntity>)
    fun getProducts(categoryId: Long): Flow<List<ProductEntity>?>
    fun getProduct(productId: Long): Flow<ProductEntity?>
    fun getCategory(id: Long): Flow<CategoryEntity?>
}