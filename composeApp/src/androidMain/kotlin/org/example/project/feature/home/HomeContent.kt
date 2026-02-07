package org.example.project.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.DeliveryAppTheme
import org.example.project.R
import org.example.project.domain.models.CategoryModel
import org.example.project.domain.models.ProductModel
import org.example.project.features.home.OrderUIModel

@Composable
fun HomeContent(
    modifier: Modifier,
    userName: String?,
    addressString: String,
    deliveryInfo: String,
    currentOrders: List<OrderUIModel>,
    categories: List<CategoryModel> = emptyList(),
    totalAmount: Float = 0f,
    onChangeAddressClicked: () -> Unit = {},
    onCategoryClicked: (CategoryModel) -> Unit = {},
    onAddToCart: (ProductModel) -> Unit = {},
    onRemoveFromCart: (ProductModel) -> Unit = {},
    onCartButtonClicked: () -> Unit = {},
    onPersonClicked: () -> Unit = {},
    onOrderClicked: (Long) -> Unit = {},
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val focusRequester = remember { FocusRequester() }

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = {
            currentOrders.size
        }
    )

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                focusRequester.freeFocus()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onChangeAddressClicked()
                        },
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.titleMedium,
                        text = addressString,
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            text = deliveryInfo,
                        )
                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.background)
                                .padding(1.dp)
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(4.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            text = "Изменить",
                        )
                    }
                }
                IconButton(
                    onClick = {
                        onPersonClicked()
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_person_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            LazyVerticalGrid(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (userName != null) {
                    item(
                        span = {
                            GridItemSpan(3)
                        }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onBackground,
                                text = "Привет, $userName!"
                            )
                        }
                    }
                }
                item(
                    span = {
                        GridItemSpan(3)
                    }
                ) {
                    HorizontalPager(
                        state = pagerState,
                        pageSize = PageSize.Fill,
                        pageSpacing = 16.dp,
                    ) {
                        val order = currentOrders[it]

                        HomeOrderView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onOrderClicked(order.id)
                                },
                            orderNumber = order.number,
                            status = order.status,
                            amount = order.amount
                        )
                    }
                }
            }
            if (totalAmount > 0) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp),
                    onClick = onCartButtonClicked
                ) {
                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        text = "${totalAmount.toInt()} ₽"
                    )
                }
            }
        }
    }
}