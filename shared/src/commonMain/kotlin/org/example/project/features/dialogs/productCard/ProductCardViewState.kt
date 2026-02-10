package org.example.project.features.dialogs.productCard

import org.example.project.domain.models.ProductModel
import org.example.project.features.base.Reducer

data class ProductCardViewState(
    val isLoading: Boolean,
    val product: ProductModel?,
) : Reducer.ViewState