package org.example.project.di

import org.example.project.data.datastore.local.AndroidSecurityStorage
import org.example.project.data.datastore.local.SecurityStorage
import org.koin.dsl.module

actual fun platformModule() = module {
    single<SecurityStorage> { AndroidSecurityStorage() }
}