package org.example.project.features.edit_category

import org.example.project.features.base.Reducer

data class EditCategoryViewState(
    val title: String,
    val categoryId: Long?,
    val name: String,
    val imageUrl: String,
    val isLoading: Boolean = false,
) : Reducer.ViewState
