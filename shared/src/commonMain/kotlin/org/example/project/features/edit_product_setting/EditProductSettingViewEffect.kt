package org.example.project.features.edit_product_setting

import org.example.project.features.base.Reducer

sealed interface EditProductSettingViewEffect : Reducer.ViewEffect {
    data object None : EditProductSettingViewEffect
}
