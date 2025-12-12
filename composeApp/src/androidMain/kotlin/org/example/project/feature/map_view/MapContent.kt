package org.example.project.feature.map_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.compose.DeliveryAppTheme
import org.example.project.R
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.DepartmentModel
import org.example.project.feature.ui_components.SelectedButton
import org.example.project.features.map.UiPoint

@Composable
fun MapContent(
    modifier: Modifier = Modifier,
    addressString: String?,
    deliveryInfo: String?,
    isLoading: Boolean,
    isSearching: Boolean,
    isConfirmEnabled: Boolean,
    moveToLocation: Boolean,
    deliveryType: DeliveryType,
    departments: List<DepartmentModel>,
    selectedDepartment: Int? = null,
    location: UiPoint?,
    onSelectDeliveryType: (DeliveryType) -> Unit = {},
    onConfirm: () -> Unit = {},
    onBackButtonClicked: () -> Unit = {},
    onMapMoved: (Double, Double, Int, Boolean) -> Unit = { _, _, _, _ -> },
    onSelectDepartment: (Int) -> Unit = {_ -> },
    onRequestLocation: () -> Unit = {},
    onSearchClicked: () -> Unit = {},
    showBackButton: Boolean = true,
    showMap: Boolean = true,
) {
    val context = LocalContext.current
    if (isLoading) {
        Dialog(
            onDismissRequest = {}
        ) {
            CircularProgressIndicator()
        }
    }

    Box(
        modifier = modifier
    ) {
        if (showMap) {
            YandexMapView(
                modifier = Modifier
                    .fillMaxSize(),
                context = context,
                position = location,
                moveToLocation = moveToLocation,
                onMapMoved = { lat, lon, reason, finished ->
                    onMapMoved(lat, lon, reason, finished)
                },
                showDepartments = deliveryType == DeliveryType.PICKUP,
                selectedDepartment = selectedDepartment,
                departments = departments,
                onSelectDepartment = onSelectDepartment
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                if (showBackButton) {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        onClick = onBackButtonClicked
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_16),
                            tint = MaterialTheme.colorScheme.primaryContainer,
                            contentDescription = null,
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                if (deliveryType == DeliveryType.DELIVERY) {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        onClick = onSearchClicked
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_loupe_16),
                            tint = MaterialTheme.colorScheme.primaryContainer,
                            contentDescription = null,
                        )
                    }
                }
            }
            if (addressString != null) {
                Text(
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    text = addressString
                )
            }
            if (deliveryInfo != null) {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    text = deliveryInfo,
                )
            }
        }
        if (deliveryType == DeliveryType.DELIVERY) {
            TooltipComponent(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-24).dp),
                isSearching = isSearching,
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp),
                shape = RoundedCornerShape(50),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 4.dp
                ),
                onClick = {
                    onRequestLocation()
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_marker_point),
                    contentDescription = null,
                )
            }

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SelectedButton(
                        modifier = Modifier
                            .weight(1f),
                        title = "Доставка",
                        selected = deliveryType == DeliveryType.DELIVERY,
                        onClick = {
                            if (deliveryType == DeliveryType.PICKUP) {
                                onSelectDeliveryType(DeliveryType.DELIVERY)
                            }
                        }
                    )
                    SelectedButton(
                        modifier = Modifier
                            .weight(1f),
                        title = "Самовывоз",
                        selected = deliveryType == DeliveryType.PICKUP,
                        onClick = {
                            if (deliveryType == DeliveryType.DELIVERY) {
                                onSelectDeliveryType(DeliveryType.PICKUP)
                            }
                        }
                    )
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp
                    ),
                    shape = RoundedCornerShape(50),
                    enabled = isConfirmEnabled,
                    onClick = onConfirm,
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.background,
                        text = "Подтвердить"
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MapContent_Preview() {
    DeliveryAppTheme {
        MapContent(
            modifier = Modifier.fillMaxSize(),
            addressString = "ул. Богдана Хмельницкого, 2А",
            deliveryInfo = "Доставка 100 ₽",
            isLoading = false,
            isSearching = true,
            moveToLocation = false,
            isConfirmEnabled = false,
            location = null,
            deliveryType = DeliveryType.DELIVERY,
            showMap = false,
            showBackButton = true,
            departments = emptyList()
        )
    }
}