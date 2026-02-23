package org.example.project.domain.models

enum class CatalogImportMode(
    val title: String,
    val serverValue: String,
) {
    PRODUCTS(
        title = "Импорт товаров",
        serverValue = "products",
    ),
    CATEGORIES(
        title = "Импорт категорий",
        serverValue = "categories",
    ),
}
