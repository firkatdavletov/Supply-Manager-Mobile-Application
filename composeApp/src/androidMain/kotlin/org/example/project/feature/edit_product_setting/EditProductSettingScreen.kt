package org.example.project.feature.edit_product_setting

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.features.edit_product_setting.EditProductComponent
import org.example.project.features.edit_product_setting.EditProductSettingViewEvent

@Composable
fun EditProductSettingScreen(component: EditProductComponent) {
    val state by component.state.subscribeAsState()

    BackHandler {
        component.onEvent(EditProductSettingViewEvent.OnBackClicked)
    }

    EditProductSettingContent(
        title = state.title,
        name = state.name,
        description = state.description,
        price = state.price,
        imageUrl = state.imageUrl,
        isLoading = state.isLoading,
        onBackClicked = {
            component.onEvent(EditProductSettingViewEvent.OnBackClicked)
        },
        onNameChanged = {
            component.onEvent(EditProductSettingViewEvent.OnNameChanged(it))
        },
        onDescriptionChanged = {
            component.onEvent(EditProductSettingViewEvent.OnDescriptionChanged(it))
        },
        onPriceChanged = {
            component.onEvent(EditProductSettingViewEvent.OnPriceChanged(it))
        },
        onImageUrlChanged = {
            component.onEvent(EditProductSettingViewEvent.OnImageUrlChanged(it))
        },
        onSaveClicked = {
            component.onEvent(EditProductSettingViewEvent.OnSaveClicked)
        },
    )
}