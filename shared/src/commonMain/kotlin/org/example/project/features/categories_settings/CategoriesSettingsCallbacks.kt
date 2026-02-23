package org.example.project.features.categories_settings

data class CategoriesSettingsCallbacks(
    val navigateBack: () -> Unit,
    val navigateToAddCategory: () -> Unit,
    val navigateToEditCategory: (Long) -> Unit,
)
