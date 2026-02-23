package org.example.project.features.edit_category

import com.arkivanov.decompose.ComponentContext
import org.example.project.domain.models.CategoryModel
import org.example.project.features.SnackBarManager
import org.example.project.features.categories_settings.CategoriesSettingsStore

class DefaultEditCategoryComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val callbacks: EditCategoryCallbacks,
    private val categoriesStore: CategoriesSettingsStore,
    categoryId: Long?,
) : EditCategoryComponent(
        componentContext = componentContext,
        initialState = buildInitialState(
            category = categoryId?.let(categoriesStore::getCategoryById),
            categoryId = categoryId,
        ),
        reducer = EditCategoryReducer(),
        snackBarManager = snackBarManager,
    ) {

    override fun onEvent(event: EditCategoryViewEvent) {
        when (event) {
            EditCategoryViewEvent.OnBackClicked -> {
                callbacks.navigateBack()
            }

            is EditCategoryViewEvent.OnNameChanged -> {
                reduce(event)
            }

            is EditCategoryViewEvent.OnImageUrlChanged -> {
                reduce(event)
            }

            EditCategoryViewEvent.OnSaveClicked -> {
                save()
            }

            EditCategoryViewEvent.OnLoading -> {
                reduce(event)
            }

            EditCategoryViewEvent.OnSaved -> {
                reduce(event)
            }

            is EditCategoryViewEvent.OnError -> {
                reduce(event)
                showError(event.error)
            }

            is EditCategoryViewEvent.OnThrowError -> {
                reduce(event)
                showThrowError(event.throwable)
            }
        }
    }

    private fun save() {
        val title = state.value.name.trim()
        if (title.isBlank()) {
            onEvent(EditCategoryViewEvent.OnError("Введите название категории"))
            return
        }

        onEvent(EditCategoryViewEvent.OnLoading)

        val imageUrl = state.value.imageUrl.trim().ifBlank { null }
        val categoryId = state.value.categoryId
        val category = if (categoryId == null) {
            CategoryModel(
                id = categoriesStore.nextCategoryId(),
                title = title,
                imageUrl = imageUrl,
                parentCategoryId = null,
                products = emptyList(),
                children = emptyList(),
            )
        } else {
            val oldCategory = categoriesStore.getCategoryById(categoryId)
            if (oldCategory != null) {
                oldCategory.copy(
                    title = title,
                    imageUrl = imageUrl,
                )
            } else {
                CategoryModel(
                    id = categoryId,
                    title = title,
                    imageUrl = imageUrl,
                    parentCategoryId = null,
                    products = emptyList(),
                    children = emptyList(),
                )
            }
        }

        categoriesStore.upsertCategory(category)
        onEvent(EditCategoryViewEvent.OnSaved)
        callbacks.navigateBack()
    }

    companion object {
        private fun buildInitialState(
            category: CategoryModel?,
            categoryId: Long?,
        ): EditCategoryViewState {
            return if (category == null) {
                EditCategoryViewState(
                    title = "Новая категория",
                    categoryId = categoryId,
                    name = "",
                    imageUrl = "",
                )
            } else {
                EditCategoryViewState(
                    title = "Редактирование категории",
                    categoryId = category.id,
                    name = category.title,
                    imageUrl = category.imageUrl.orEmpty(),
                )
            }
        }
    }
}
