package org.example.project.domain.usecase.catalog

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.ProductModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.CatalogRepository
import org.example.project.domain.usecase.base.IOUseCase

class GetProductUseCase(
    private val catalogRepository: CatalogRepository,
) : IOUseCase<Long, ResultModel<ProductModel>>() {
    override fun execute(param: Long): Flow<ResultModel<ProductModel>> {
        return catalogRepository.getProduct(param)
    }
}