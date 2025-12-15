package org.example.project.feature.launch

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.DeliveryAppTheme
import com.example.ui.theme.AppTypography
import org.example.project.R

@Composable
fun LaunchContent(
    isLoading: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier,
    onRetryClicked: () -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(196.dp)
                .clip(CircleShape)
                .background(Color(0xFF000000))
                .align(Alignment.Center),
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                painter = painterResource(R.drawable.my_foodbox_logo_2),
                contentDescription = null
            )
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            } else if (isError) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        onRetryClicked()
                    }
                ) {
                    Text(
                        text = "Повторить"
                    )
                }
            }
        }
    }
}

@Preview(
    widthDp = 400,
    heightDp = 800,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun LaunchScreen_Preview_Night() {
    DeliveryAppTheme(
        dynamicColor = false
    ) {
        LaunchContent(
            isLoading = false,
            isError = true,
        ) { }
    }
}

@Preview(
    widthDp = 400,
    heightDp = 800,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun LaunchScreen_Preview() {
    DeliveryAppTheme(
        dynamicColor = false
    ) {
        LaunchContent(
            isLoading = false,
            isError = true,
        ) { }
    }
}