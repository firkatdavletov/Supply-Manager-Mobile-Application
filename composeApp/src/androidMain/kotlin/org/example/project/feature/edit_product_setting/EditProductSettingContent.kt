package org.example.project.feature.edit_product_setting

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import com.example.compose.DeliveryAppTheme
import org.example.project.R

@Composable
fun EditProductSettingContent(
    title: String,
    name: String,
    description: String,
    price: String,
    imageUrl: String,
    isLoading: Boolean,
    onBackClicked: () -> Unit = {},
    onNameChanged: (String) -> Unit = {},
    onDescriptionChanged: (String) -> Unit = {},
    onPriceChanged: (String) -> Unit = {},
    onImageUrlChanged: (String) -> Unit = {},
    onSaveClicked: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp),
            ) {
                IconButton(
                    onClick = onBackClicked,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_16),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    text = title,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = onNameChanged,
                    singleLine = true,
                    label = { Text("Название товара") },
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    onValueChange = onDescriptionChanged,
                    label = { Text("Описание (необязательно)") },
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = price,
                    onValueChange = onPriceChanged,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    label = { Text("Цена в копейках") },
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = imageUrl,
                    onValueChange = onImageUrlChanged,
                    singleLine = true,
                    label = { Text("URL изображения (необязательно)") },
                )

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    onClick = onSaveClicked,
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp,
                        )
                    } else {
                        Text(text = "Сохранить")
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun EditProductSettingContentPreview() {
    DeliveryAppTheme {
        EditProductSettingContent(
            title = "Редактирование товара",
            name = "Пицца Маргарита",
            description = "Томаты, сыр",
            price = "129900",
            imageUrl = "https://example.com/product.png",
            isLoading = false,
        )
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EditProductSettingContentNightPreview() {
    DeliveryAppTheme {
        EditProductSettingContent(
            title = "Новый товар",
            name = "",
            description = "",
            price = "",
            imageUrl = "",
            isLoading = false,
        )
    }
}
