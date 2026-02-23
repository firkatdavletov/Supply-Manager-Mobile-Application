package org.example.project.features.edit_product_setting

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent

abstract class EditProductComponent(
    componentContext: ComponentContext,
    initialState: EditProductSettingViewState,
    reducer: EditProductSettingReducer,
    snackBarManager: SnackBarManager? = null,
) : BaseComponent<EditProductSettingViewState, EditProductSettingViewEvent, EditProductSettingViewEffect>(
        componentContext = componentContext,
        initialState = initialState,
        reducer = reducer,
        snackBarManager = snackBarManager,
    )