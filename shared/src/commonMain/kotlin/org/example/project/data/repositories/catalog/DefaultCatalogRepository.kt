package org.example.project.data.repositories.catalog

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
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
) : CatalogRepository {

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

    override fun getCategory(id: Long): Flow<ResultModel<CategoryModel>> {
        return flow {
            val response = catalogRemoteDataStore.getCategory(id)

            if (response.success && response.category != null) {
                val model = categoryMapper.toModel(response.category)
                emit(ResultModel.Success(model))
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCategoryById(id: Long): Flow<CategoryModel?> {
        TODO()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getProducts(categoryId: Long): Flow<List<ProductModel>> {
        return flow {
        }
    }

    override fun getProduct(id: Long): Flow<ResultModel<ProductModel>> {
        return flow {
            emit(ResultModel.Loading)
            val response = catalogRemoteDataStore.getProduct(id)

            if (response.success && response.product != null) {
                emit(ResultModel.Success(productMapper.toModel(response.product)))
            } else {
                emit(ResultModel.Error(response.error, response.code))
            }
        }
    }

    override fun getRemoteCategories(): Flow<ResultModel<List<CategoryModel>>> {
        return flow {
            emit(ResultModel.Loading)
            val response = catalogRemoteDataStore.getCategories()
            if (response.success) {
                val model = categoryMapper.toModel(response.catalog)
                _catalogSubject.emit(model)
                emit(ResultModel.Success(model))
            } else {
                emit(ResultModel.Error(response.error, response.code))
            }
        }
    }
}