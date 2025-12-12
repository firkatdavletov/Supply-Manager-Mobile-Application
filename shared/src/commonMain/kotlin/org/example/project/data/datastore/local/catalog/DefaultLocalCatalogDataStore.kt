package org.example.project.data.datastore.local.catalog

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.project.data.entities.CategoryEntity
import org.example.project.data.entities.ProductEntity

class DefaultLocalCatalogDataStore: LocalCatalogDataStore {
    private var categories: List<CategoryEntity> = emptyList()

    override fun getCategories(): Flow<List<CategoryEntity>> {
        return flow {
            emit(categories)
        }
    }

    override fun saveCategories(categories: List<CategoryEntity>) {
        this.categories = categories
    }

    override fun getProducts(categoryId: Long): Flow<List<ProductEntity>> {
        return flow {
            val products = categories.find { it.id == categoryId }!!.products
            emit(products)
        }
    }

    override fun getProduct(productId: Long): Flow<ProductEntity> {
        return flow {
            val product = categories.flatMap { it.products }.first { it.id == productId }
            emit(product)
        }
    }

    override fun getCategory(id: Long): Flow<CategoryEntity?> {
        return flow {
            val category = categories.firstOrNull{
                it.id == id
            }
            emit(category)
        }
    }
}