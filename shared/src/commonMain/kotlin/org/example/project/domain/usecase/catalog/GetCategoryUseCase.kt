package org.example.project.domain.usecase.catalog

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.CategoryModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.CatalogRepository
import org.example.project.domain.usecase.base.IOUseCase

class GetCategoryUseCase(
    private val catalogRepository: CatalogRepository,
): IOUseCase<Long, ResultModel<CategoryModel>>() {
    override fun execute(param: Long): Flow<ResultModel<CategoryModel>> {
        return catalogRepository.getCategory(param)
    }
}