package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.profile.DefaultProfileComponent
import org.example.project.features.profile.ProfileCallbacks
import org.example.project.features.profile.ProfileComponent
import org.koin.dsl.module

fun profileModule() = module {
    single<ProfileComponent> {  (componentContext: ComponentContext, callback: ProfileCallbacks) ->
        DefaultProfileComponent(
            componentContext,
            get(),
            callback,
            get(),
            get(),
            get(),
            get()
        )
    }
}