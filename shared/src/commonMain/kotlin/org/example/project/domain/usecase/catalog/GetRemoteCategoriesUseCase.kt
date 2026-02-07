package org.example.project.domain.usecase.catalog

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.CategoryModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.CatalogRepository
import org.example.project.domain.usecase.base.IOUseCase

class GetRemoteCategoriesUseCase(
    private val catalogRepository: CatalogRepository
): IOUseCase<Unit, ResultModel<List<CategoryModel>>>() {
    override fun execute(param: Unit): Flow<ResultModel<List<CategoryModel>>> {
        return catalogRepository.getRemoteCategories()
    }
}