package org.example.project.features.categories_settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.domain.models.CategoryModel

class CategoriesSettingsStore {
    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categories: StateFlow<List<CategoryModel>> = _categories.asStateFlow()

    fun setCategories(categories: List<CategoryModel>) {
        _categories.value = categories.distinctBy { it.id }
    }

    fun upsertCategory(category: CategoryModel) {
        val current = _categories.value.toMutableList()
        val existingIndex = current.indexOfFirst { it.id == category.id }

        if (existingIndex == -1) {
            current.add(category)
        } else {
            current[existingIndex] = category
        }

        _categories.value = current
    }

    fun getCategoryById(id: Long): CategoryModel? {
        return _categories.value.firstOrNull { it.id == id }
    }

    fun nextCategoryId(): Long {
        return (_categories.value.maxOfOrNull { it.id } ?: 0L) + 1L
    }
}
