package org.example.project.dialogs.product_card

import org.example.project.domain.models.ProductModel
import org.example.project.features.base.Reducer

data class ProductCardViewState(
    val isLoading: Boolean,
    val product: ProductModel?
) : Reducer.ViewState
