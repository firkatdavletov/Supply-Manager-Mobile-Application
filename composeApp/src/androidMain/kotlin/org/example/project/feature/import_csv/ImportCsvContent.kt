package org.example.project.feature.import_csv

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.DeliveryAppTheme
import org.example.project.R
import org.example.project.domain.models.CatalogImportMode

@Composable
fun ImportCsvContent(
    title: String,
    modes: List<CatalogImportMode>,
    selectedMode: CatalogImportMode,
    selectedFileName: String?,
    isLoading: Boolean,
    responseTitle: String?,
    responseDetails: String?,
    isResponseError: Boolean,
    onBackClicked: () -> Unit = {},
    onModeSelected: (CatalogImportMode) -> Unit = {},
    onSelectCsvClicked: () -> Unit = {},
    onImportClicked: () -> Unit = {},
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
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "Режим импорта",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                modes.forEach { mode ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onModeSelected(mode)
                            }
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = selectedMode == mode,
                            onClick = {
                                onModeSelected(mode)
                            },
                        )
                        Text(
                            text = mode.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSelectCsvClicked,
                    enabled = !isLoading,
                ) {
                    Text(
                        text = "Выбрать CSV",
                    )
                }

                Text(
                    text = selectedFileName ?: "Файл не выбран",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onImportClicked,
                    enabled = selectedFileName != null && !isLoading,
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(20.dp)
                                .padding(vertical = 2.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    } else {
                        Text(
                            text = "Импортировать",
                        )
                    }
                }

                if (responseTitle != null) {
                    val backgroundColor =
                        if (isResponseError) {
                            MaterialTheme.colorScheme.errorContainer
                        } else {
                            MaterialTheme.colorScheme.secondaryContainer
                        }
                    val textColor =
                        if (isResponseError) {
                            MaterialTheme.colorScheme.onErrorContainer
                        } else {
                            MaterialTheme.colorScheme.onSecondaryContainer
                        }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = backgroundColor,
                                shape = RoundedCornerShape(12.dp),
                            )
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = responseTitle,
                            style = MaterialTheme.typography.titleSmall,
                            color = textColor,
                        )

                        if (!responseDetails.isNullOrBlank()) {
                            Text(
                                text = responseDetails,
                                style = MaterialTheme.typography.bodyMedium,
                                color = textColor,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ImportCsvContentPreview() {
    DeliveryAppTheme {
        ImportCsvContent(
            title = "Импорт CSV",
            modes = CatalogImportMode.entries,
            selectedMode = CatalogImportMode.PRODUCTS,
            selectedFileName = "products.csv",
            isLoading = false,
            responseTitle = "Импорт выполнен",
            responseDetails = "Обработано 20 строк",
            isResponseError = false,
        )
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ImportCsvContentNightPreview() {
    DeliveryAppTheme {
        ImportCsvContent(
            title = "Импорт CSV",
            modes = CatalogImportMode.entries,
            selectedMode = CatalogImportMode.CATEGORIES,
            selectedFileName = null,
            isLoading = false,
            responseTitle = "Ошибка импорта",
            responseDetails = "Некорректный формат CSV",
            isResponseError = true,
        )
    }
}
