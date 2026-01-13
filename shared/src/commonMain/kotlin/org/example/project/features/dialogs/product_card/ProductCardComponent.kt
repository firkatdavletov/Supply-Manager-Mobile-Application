package org.example.project.features.dialogs.product_card

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent

abstract class ProductCardComponent(
    componentContent: ComponentContext,
    initialState: ProductCardViewState,
    reducer: ProductCardReducer,
    snackBarManager: SnackBarManager,
) : BaseComponent<ProductCardViewState, ProductCardViewEvent, ProductCardViewEffect>(
    componentContext = componentContent,
    initialState = initialState,
    reducer = reducer,
    snackBarManager = snackBarManager,
)