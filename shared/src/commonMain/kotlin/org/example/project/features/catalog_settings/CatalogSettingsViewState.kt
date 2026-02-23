package org.example.project.features.catalog_settings

import org.example.project.features.base.Reducer

data class CatalogSettingsViewState(
    val title: String = "Настройки каталога",
    val items: List<CatalogSettingsMenuItem> = listOf(
        CatalogSettingsMenuItem.CATEGORY_CARDS,
        CatalogSettingsMenuItem.PRODUCT_CARDS,
        CatalogSettingsMenuItem.CSV_IMPORT,
    ),
    val selectedItem: CatalogSettingsMenuItem? = null,
) : Reducer.ViewState
