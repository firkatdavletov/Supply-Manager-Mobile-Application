package org.example.project.data.repositories.catalog

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.example.project.data.datastore.local.catalog.LocalCatalogDataStore
import org.example.project.data.datastore.remote.catalog.CatalogRemoteDataStore
import org.example.project.data.mapper.CategoryMapper
import org.example.project.data.mapper.ProductMapper
import org.example.project.domain.models.CategoryModel
import org.example.project.domain.models.ProductModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.CatalogRepository

class DefaultCatalogRepository(
    private val catalogRemoteDataStore: CatalogRemoteDataStore,
    private val catalogLocalDataStore: LocalCatalogDataStore,
    private val categoryMapper: CategoryMapper,
    private val productMapper: ProductMapper,
): CatalogRepository {

    private val _catalogSubject = MutableSharedFlow<List<CategoryModel>>(replay = 1)

    override val catalogSubject: SharedFlow<List<CategoryModel>> = _catalogSubject.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCategories(): Flow<List<CategoryModel>> {
        return catalogLocalDataStore.getCategories().flatMapConcat {
            flow {
                emit(categoryMapper.toModel(it))
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCategoryById(id: Long): Flow<CategoryModel?> {
        return catalogLocalDataStore.getCategory(id).map {
            it?.let { categoryMapper.toModel(it)  }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getProducts(categoryId: Long): Flow<List<ProductModel>> {
        return flow {
            val categories = _catalogSubject.replayCache.firstOrNull()
            val products = categories?.firstOrNull { it.id == categoryId }?.products ?: emptyList()
            emit(products)
        }
    }

    override fun getProduct(productId: Long): Flow<ProductModel?> {
        return flow {
            val catalog = catalogSubject.replayCache.firstOrNull()
            val product = catalog?.flatMap { it.products }?.firstOrNull { it.id == productId }
            emit(product)
        }
    }

    override fun loadCatalog(): Flow<ResultModel<Boolean>> {
        return flow {
            emit(ResultModel.Loading)
            val response = catalogRemoteDataStore.getCatalog()
            if (response.success) {
                val model = categoryMapper.toModel(response.catalog)
                _catalogSubject.emit(model)
                emit(ResultModel.Success(true))
            } else {
                emit(ResultModel.Error(response.error, response.code))
            }
        }
    }
}