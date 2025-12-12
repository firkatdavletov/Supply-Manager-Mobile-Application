package org.example.project

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import org.example.project.di.appIntroductionModule
import org.example.project.di.appModule
import org.example.project.di.authorizationModule
import org.example.project.di.cartModule
import org.example.project.di.catalogModule
import org.example.project.di.currentOrderModule
import org.example.project.di.homeModule
import org.example.project.di.launchModule
import org.example.project.di.mapModule
import org.example.project.di.paymentModule
import org.example.project.di.platformModule
import org.example.project.di.profileModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class InboxApplication: Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
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
                profileModule()
            )
        }
        MapKitFactory.setApiKey("ae6b93e5-52ca-4ab3-879c-34d8728b59b5")
    }
}