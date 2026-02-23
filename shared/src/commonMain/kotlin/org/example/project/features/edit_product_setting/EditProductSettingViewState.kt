package org.example.project.features.edit_product_setting

import org.example.project.features.base.Reducer

data class EditProductSettingViewState(
    val title: String,
    val productId: Long?,
    val name: String,
    val description: String,
    val price: String,
    val imageUrl: String,
    val isLoading: Boolean = false,
) : Reducer.ViewState
