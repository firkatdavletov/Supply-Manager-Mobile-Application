package org.example.project.domain.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import org.example.project.domain.models.CategoryModel
import org.example.project.domain.models.ProductModel
import org.example.project.domain.models.ResultModel

interface CatalogRepository {
    val catalogSubject: SharedFlow<List<CategoryModel>>
    fun getCategories(): Flow<List<CategoryModel>>
    fun getCategoryById(id: Long): Flow<CategoryModel?>
    fun getProducts(categoryId: Long): Flow<List<ProductModel>>
    fun getProduct(productId: Long): Flow<ProductModel?>
    fun loadCatalog(): Flow<ResultModel<Boolean>>
}