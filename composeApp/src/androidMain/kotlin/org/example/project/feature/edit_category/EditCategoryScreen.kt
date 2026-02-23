package org.example.project.feature.edit_category

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.features.edit_category.EditCategoryComponent
import org.example.project.features.edit_category.EditCategoryViewEvent

@Composable
fun EditCategoryScreen(component: EditCategoryComponent) {
    val state by component.state.subscribeAsState()

    BackHandler {
        component.onEvent(EditCategoryViewEvent.OnBackClicked)
    }

    EditCategoryContent(
        title = state.title,
        name = state.name,
        imageUrl = state.imageUrl,
        isLoading = state.isLoading,
        onBackClicked = {
            component.onEvent(EditCategoryViewEvent.OnBackClicked)
        },
        onNameChanged = {
            component.onEvent(EditCategoryViewEvent.OnNameChanged(it))
        },
        onImageUrlChanged = {
            component.onEvent(EditCategoryViewEvent.OnImageUrlChanged(it))
        },
        onSaveClicked = {
            component.onEvent(EditCategoryViewEvent.OnSaveClicked)
        },
    )
}
