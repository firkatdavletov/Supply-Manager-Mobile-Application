package org.example.project.domain.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import org.example.project.domain.models.CategoryModel
import org.example.project.domain.models.ProductModel
import org.example.project.domain.models.ResultModel

interface CatalogRepository {
    val catalogSubject: SharedFlow<List<CategoryModel>>

    fun getCategories(): Flow<List<CategoryModel>>

    fun getCategory(id: Long): Flow<ResultModel<CategoryModel>>

    fun getCategoryById(id: Long): Flow<CategoryModel?>

    fun getProduct(id: Long): Flow<ResultModel<ProductModel>>

    fun getProducts(categoryId: Long): Flow<List<ProductModel>>

    fun getRemoteCategories(): Flow<ResultModel<List<CategoryModel>>>
}