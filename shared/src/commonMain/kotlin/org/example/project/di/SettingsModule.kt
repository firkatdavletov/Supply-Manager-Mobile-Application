package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.settings.DefaultSettingsComponent
import org.example.project.features.settings.SettingsCallbacks
import org.example.project.features.settings.SettingsComponent
import org.koin.dsl.module

fun settingsModule() = module {
    single<SettingsComponent> { (componentContext: ComponentContext, callbacks: SettingsCallbacks) ->
        DefaultSettingsComponent(
            componentContext = componentContext,
            callbacks = callbacks,
        )
    }
}
