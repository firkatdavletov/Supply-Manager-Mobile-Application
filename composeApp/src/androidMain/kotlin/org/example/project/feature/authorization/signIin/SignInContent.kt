package org.example.project.feature.authorization.signIin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.DeliveryAppTheme
import com.example.ui.theme.AppTypography
import org.example.project.feature.ui_components.DefaultTextField

@Composable
fun SignInContent(
    modifier: Modifier,
    isLoading: Boolean,
    login: String,
    password: String,
    isLoginEnabled: Boolean,
    onLoginChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onLoginClicked: () -> Unit = {},
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                style = AppTypography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground,
                text = "Войти",
            )
            Spacer(modifier = Modifier.height(24.dp))
            DefaultTextField(
                modifier = Modifier.fillMaxWidth(),
                value = login,
                onValueChange = onLoginChanged,
            )
            Spacer(modifier = Modifier.height(12.dp))
            DefaultTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = onPasswordChanged,
                visualTransformation = PasswordVisualTransformation(),
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onLoginClicked,
                enabled = isLoginEnabled && !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
            ) {
                Text(text = "Войти")
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@Preview(showSystemUi = true, device = PIXEL_9)
@Composable
private fun SignInContent_Preview() {
    DeliveryAppTheme {
        SignInContent(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            isLoading = false,
            login = "user@example.com",
            password = "password",
            isLoginEnabled = true,
        )
    }
}
