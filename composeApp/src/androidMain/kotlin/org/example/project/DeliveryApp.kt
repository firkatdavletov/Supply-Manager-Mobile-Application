package org.example.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compose.DeliveryAppTheme
import org.example.project.features.SnackBarManager
import org.example.project.navigation.RootComponent
import org.example.project.navigation.RootContent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun DeliveryApp(
    snackBarManager: SnackBarManager,
    rootComponent: RootComponent
) {
    val snackbarHostState = SnackbarHostState()
    LaunchedEffect(rootComponent) {
        snackBarManager.messages.collect {
            snackbarHostState.showSnackbar(
                object : SnackbarVisuals {
                    override val message: String = it
                    override val actionLabel: String = ""
                    override val withDismissAction: Boolean = false
                    override val duration: SnackbarDuration = SnackbarDuration.Short
                }
            )
        }
    }
    DeliveryAppTheme(
        dynamicColor = false
    ) {
        Scaffold(
            snackbarHost = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    SnackbarHost(
                        modifier = Modifier.padding(8.dp),
                        hostState = snackbarHostState,
                        snackbar = {
                            Snackbar(it)
                        }
                    )
                }
            },
            contentWindowInsets = WindowInsets(0,0,0,0)
        ) { scaffoldPadding ->
            RootContent(
                modifier = Modifier.padding(scaffoldPadding),
                component = rootComponent
            )
        }
    }
}