package org.example.project.domain.usecase.catalog

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.ProductModel
import org.example.project.domain.repositories.CatalogRepository
import org.example.project.domain.usecase.base.IOUseCase

class GetProductUseCase(
    private val catalogRepository: CatalogRepository,
): IOUseCase<Long, ProductModel?>() {
    override fun execute(param: Long): Flow<ProductModel?> {
        return catalogRepository.getProduct(param)
    }
}