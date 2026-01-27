package org.example.project.features.catalog

import org.example.project.domain.models.ProductModel
import org.example.project.features.base.Reducer

data class CatalogViewState(
    val title: String,
    val products: List<ProductModel>,
    val amount: Double,
    val productsPrice: Double,
    val freeDeliveryPrice: Double?
): Reducer.ViewState