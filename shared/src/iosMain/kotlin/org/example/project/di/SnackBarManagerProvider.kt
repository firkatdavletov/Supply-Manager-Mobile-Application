package org.example.project.di

import org.example.project.features.SnackBarManager
import org.koin.mp.KoinPlatform.getKoin

object SnackBarManagerProvider {
    fun getSnackBarManager(): SnackBarManager = getKoin().get<SnackBarManager>()
}