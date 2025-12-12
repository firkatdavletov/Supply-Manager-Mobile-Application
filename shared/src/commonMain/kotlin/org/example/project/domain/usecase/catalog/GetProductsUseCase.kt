package org.example.project.domain.usecase.catalog

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.CategoryModel
import org.example.project.domain.models.ProductModel
import org.example.project.domain.repositories.CatalogRepository
import org.example.project.domain.usecase.base.IOUseCase

class GetProductsUseCase(
    private val catalogRepository: CatalogRepository,
): IOUseCase<Long, List<ProductModel>>() {
    override fun execute(param: Long): Flow<List<ProductModel>> {
        return catalogRepository.getProducts(param)
    }
}