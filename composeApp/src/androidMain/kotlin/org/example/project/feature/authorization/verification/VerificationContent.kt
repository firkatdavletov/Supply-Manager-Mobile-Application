package org.example.project.feature.authorization.verification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AppTypography

@Composable
fun VerificationContent(
    code: String,
    modifier: Modifier = Modifier,
    onCodeChanged: (String) -> Unit = {},
    onBackButtonClicked: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val requestController = remember { FocusRequester() }

    LaunchedEffect(true) {
        requestController.requestFocus()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.systemBarsPadding(),
            style = AppTypography.displaySmall,
            color = MaterialTheme.colorScheme.onPrimary,
            text = "Авторизация"
        )
        Text(
            style = AppTypography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            text = "Введите код из СМС"
        )
        Spacer(modifier = Modifier.height(40.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(top = 40.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                BasicTextField(
                    modifier = Modifier
                        .focusRequester(focusRequester = requestController),
                    value = code,
                    onValueChange = {
                        onCodeChanged(it.take(4))
                        if (it.length == 4) {
                            keyboardController?.hide()
                        }
                    },
                    textStyle = MaterialTheme.typography.displayMedium.copy(
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.navigationBarsPadding(),
                onClick = onBackButtonClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text(
                    text = "Изменить способ подтверждения"
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun VerificationContent_Preview() {
    VerificationContent(
        code = "1234"
    )
}