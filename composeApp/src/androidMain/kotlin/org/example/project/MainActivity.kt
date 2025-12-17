package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.yandex.mapkit.MapKitFactory
import org.example.project.data.datastore.local.AndroidSecurityStorage
import org.example.project.data.datastore.local.SecurityStorage
import org.example.project.features.SnackBarManager
import org.example.project.navigation.DefaultRootComponent
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    val securityStorage: SecurityStorage by inject<SecurityStorage>()
    val snackBarManager: SnackBarManager by inject<SnackBarManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (securityStorage as AndroidSecurityStorage).initialize(this)
        MapKitFactory.initialize(applicationContext)
        val rootComponent = DefaultRootComponent(defaultComponentContext(), snackBarManager)
        enableEdgeToEdge()
        setContent {
            DeliveryApp(rootComponent = rootComponent)
        }
    }
}