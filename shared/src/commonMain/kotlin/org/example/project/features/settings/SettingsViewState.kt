package org.example.project.features.settings

import org.example.project.features.base.Reducer

data class SettingsViewState(
    val title: String = "Настройки",
    val items: List<SettingsMenuItem> = listOf(
        SettingsMenuItem.CATALOG,
        SettingsMenuItem.STORES,
        SettingsMenuItem.ACCOUNTS,
        SettingsMenuItem.DELIVERY_TERMS,
    ),
    val selectedItem: SettingsMenuItem? = null,
) : Reducer.ViewState
