package org.example.project.feature.catalog_settings

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.DeliveryAppTheme
import org.example.project.R
import org.example.project.features.catalog_settings.CatalogSettingsMenuItem

@Composable
fun CatalogSettingsContent(
    title: String,
    items: List<CatalogSettingsMenuItem>,
    selectedItem: CatalogSettingsMenuItem? = null,
    onBackClicked: () -> Unit = {},
    onItemClicked: (CatalogSettingsMenuItem) -> Unit = {},
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

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(
                    count = items.size,
                ) { index ->
                    val item = items[index]
                    val isSelected = item == selectedItem
                    val rowColor =
                        if (isSelected) {
                            MaterialTheme.colorScheme.surfaceVariant
                        } else {
                            MaterialTheme.colorScheme.background
                        }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(rowColor)
                            .clickable {
                                onItemClicked(item)
                            }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            text = item.title,
                        )
                        Icon(
                            modifier = Modifier.rotate(180f),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_16),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }

                    if (index < items.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 16.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CatalogSettingsContentPreview() {
    DeliveryAppTheme {
        CatalogSettingsContent(
            title = "Настройки каталога",
            items = listOf(
                CatalogSettingsMenuItem.CATEGORY_CARDS,
                CatalogSettingsMenuItem.PRODUCT_CARDS,
                CatalogSettingsMenuItem.CSV_IMPORT,
            ),
            selectedItem = CatalogSettingsMenuItem.CATEGORY_CARDS,
        )
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CatalogSettingsContentNightPreview() {
    DeliveryAppTheme {
        CatalogSettingsContent(
            title = "Настройки каталога",
            items = listOf(
                CatalogSettingsMenuItem.CATEGORY_CARDS,
                CatalogSettingsMenuItem.PRODUCT_CARDS,
                CatalogSettingsMenuItem.CSV_IMPORT,
            ),
            selectedItem = CatalogSettingsMenuItem.PRODUCT_CARDS,
        )
    }
}
