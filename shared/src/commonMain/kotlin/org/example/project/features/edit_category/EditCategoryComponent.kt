package org.example.project.features.edit_category

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent

abstract class EditCategoryComponent(
    componentContext: ComponentContext,
    initialState: EditCategoryViewState,
    reducer: EditCategoryReducer,
    snackBarManager: SnackBarManager? = null,
) : BaseComponent<EditCategoryViewState, EditCategoryViewEvent, EditCategoryViewEffect>(
    componentContext = componentContext,
    initialState = initialState,
    reducer = reducer,
    snackBarManager = snackBarManager,
)
