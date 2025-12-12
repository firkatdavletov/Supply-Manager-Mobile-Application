package org.example.project.domain.usecase.catalog

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.CategoryModel
import org.example.project.domain.repositories.CatalogRepository
import org.example.project.domain.usecase.base.IOUseCase

class GetCategoriesUseCase(
    private val catalogRepository: CatalogRepository,
): IOUseCase<Unit, List<CategoryModel>>() {
    override fun execute(param: Unit): Flow<List<CategoryModel>> {
        return catalogRepository.getCategories()
    }
}