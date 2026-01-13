package org.example.project

import org.example.project.di.appIntroductionModule
import org.example.project.di.appModule
import org.example.project.di.authorizationModule
import org.example.project.di.cartModule
import org.example.project.di.catalogModule
import org.example.project.di.currentOrderModule
import org.example.project.di.dialogsModule
import org.example.project.di.homeModule
import org.example.project.di.launchModule
import org.example.project.di.mapModule
import org.example.project.di.paymentModule
import org.example.project.di.platformModule
import org.example.project.di.profileModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            appModule(),
            launchModule(),
            mapModule(),
            homeModule(),
            cartModule(),
            paymentModule(),
            appIntroductionModule(),
            authorizationModule(),
            platformModule(),
            currentOrderModule(),
            catalogModule(),
            profileModule(),
            dialogsModule(),
        )
    }
}