package org.example.project.features.catalog

import org.example.project.domain.models.CategoryModel
import org.example.project.domain.models.ProductModel
import org.example.project.features.base.Reducer

data class CatalogViewState(
    val title: String = "",
    val isLoading: Boolean = true,
    val categories: List<CategoryModel> = emptyList(),
    val products: List<ProductModel> = emptyList(),
    val amount: Long = 0,
) : Reducer.ViewState