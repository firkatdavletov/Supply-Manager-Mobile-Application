package org.example.project.domain.usecase.catalog

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.ProductModel
import org.example.project.domain.repositories.CatalogRepository
import org.example.project.domain.usecase.base.IOUseCase

class GetAllProductsUseCase(
    private val catalogRepository: CatalogRepository,
) : IOUseCase<Unit, List<ProductModel>>() {
    override fun execute(param: Unit): Flow<List<ProductModel>> {
        return catalogRepository.getProducts(0L)
    }
}
