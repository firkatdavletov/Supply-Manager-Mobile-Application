package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.edit_category.DefaultEditCategoryComponent
import org.example.project.features.edit_category.EditCategoryCallbacks
import org.example.project.features.edit_category.EditCategoryComponent
import org.koin.dsl.module

fun editCategoryModule() = module {
    single<EditCategoryComponent> {
            (componentContext: ComponentContext, categoryId: Long?, callbacks: EditCategoryCallbacks) ->
        DefaultEditCategoryComponent(
            componentContext = componentContext,
            snackBarManager = get(),
            callbacks = callbacks,
            categoriesStore = get(),
            categoryId = categoryId,
        )
    }
}
