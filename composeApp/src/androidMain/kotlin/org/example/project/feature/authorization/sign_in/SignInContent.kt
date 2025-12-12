package org.example.project.feature.authorization.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.DeliveryAppTheme
import com.example.ui.theme.AppTypography
import org.example.project.feature.ui_components.DefaultTextField
import org.example.project.utils.PhoneNumberVisualTransformation

@Composable
fun SignInContent(
    modifier: Modifier,
    isLoading: Boolean,
    phoneNumber: String,
    onPhoneNumberChanged: (String) -> Unit = {},
    authTypes: List<String>,
    onAuthTypeClicked: (String) -> Unit = {},
 ) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.systemBarsPadding(),
                style = AppTypography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground,
                text = "Войти"
            )
            Text(
                style = AppTypography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                text = "Подтвердите свой номер телефона"
            )
            Spacer(modifier = Modifier.height(40.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 40.dp, start = 16.dp, end = 16.dp)
            ) {
                DefaultTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = phoneNumber,
                    onValueChange = {
                        if (it.length <= 10) {
                            onPhoneNumberChanged(it)
                        }
                        if (it.length == 10) {
                            keyboardController?.hide()
                        }
                    },
                    visualTransformation = PhoneNumberVisualTransformation()
                )
                Spacer(modifier = Modifier.weight(1f))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        count = authTypes.size,
                        key = {
                            it
                        },
                        span = {
                            if (authTypes.size == 1) {
                                GridItemSpan(2)
                            } else if (authTypes.size % 2 == 1 && it == authTypes.lastIndex) {
                                GridItemSpan(2)
                            } else {
                                GridItemSpan(1)
                            }
                        }
                    ) {
                        Button(
                            onClick = {
                                onAuthTypeClicked(authTypes[it])
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        ) {
                            Text(
                                text = when (authTypes[it]) {
                                    "sms" -> "СМС"
                                    "call" -> "По звонку"
                                    else -> ""
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.navigationBarsPadding(),
                    style = AppTypography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    text = "Выбирая способ подтверждения, вы соглашаетесь с Политикой конфиденциальности"
                )
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
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
            authTypes = listOf("sms", "call"),
            phoneNumber = "",
        )
    }
}