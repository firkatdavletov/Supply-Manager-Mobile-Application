package org.example.project.feature.search_address

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.compose.DeliveryAppTheme
import org.example.project.R
import org.example.project.domain.models.CityModel
import org.example.project.domain.models.DeliveryInfoModel
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.DepartmentModel
import org.example.project.domain.models.GeoAddressModel

@Composable
fun SearchAddressContent(
    deliveryType: DeliveryType,
    query: String,
    isLoading: Boolean,
    isSearching: Boolean,
    departments: List<DepartmentModel>,
    addresses: List<GeoAddressModel>,
    onQueryChanged: (String) -> Unit = {},
    onSelectAddress: (GeoAddressModel) -> Unit = {},
    onBackClicked: () -> Unit = {},
    onMapClicked: () -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
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
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                        .focusRequester(focusRequester),
                    shape = RoundedCornerShape(50),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        errorBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        errorTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        cursorColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_loupe_16),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    value = query,
                    onValueChange = {
                        onQueryChanged(it)
                    }
                )
            }
            if (isSearching) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator()
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            text = "Поиск..."
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    when (deliveryType) {
                        DeliveryType.PICKUP -> {
                            items(addresses.size) {
                                val address = addresses[it]
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = address.toString()
                                    )
                                }
                            }
                        }
                        DeliveryType.DELIVERY -> {
                            items(addresses.size) {
                                val address = addresses[it]
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .padding(8.dp)
                                        .clickable {
                                            onSelectAddress(address)
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            text = address.toString()
                                        )

                                        val deliveryPrice = address.deliveryInfo?.deliveryPrice?.toInt()
                                        if (deliveryPrice != null && deliveryPrice > 0) {
                                            Text(
                                                style = MaterialTheme.typography.titleSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                text = "Доставка $deliveryPrice ₽"
                                            )
                                        }
                                    }
                                    Text(
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        text = address.city.name
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .navigationBarsPadding()
                    .imePadding(),
                onClick = onMapClicked
            ) {
                Text(text = "Выбрать на карте")
            }
        }

        if (isLoading) {
            Dialog(
                onDismissRequest = {}
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SearchAddressContent_Preview() {
    DeliveryAppTheme {
        SearchAddressContent(
            deliveryType = DeliveryType.DELIVERY,
            isLoading = false,
            isSearching = false,
            query = "state.query",
            departments = listOf(
                DepartmentModel(
                    id = 0,
                    name = "hjffgsg",
                    city = CityModel(id = 0, name = "fdf", subCities = emptyList(), latitude = 0.0, longitude = 0.0),
                    latitude = 0.0,
                    longitude = 0.0,
                    workingHours = emptyList(),
                    currentWorkingHours = null,
                    isWorkingNow = true
                )
            ),
            addresses = listOf(
                GeoAddressModel(
                    city = CityModel(name = "Белорецк", latitude = 0.0, longitude = 0.0, id = 0, subCities = emptyList()),
                    street = "ул. Косоротова",
                    house = "8",
                    entrance = null,
                    deliveryInfo = DeliveryInfoModel(deliveryPrice = 100.0, freeDeliveryPrice = 1000.0),
                    deliveryTime = 20,
                    latitude = 0.0,
                    longitude = 0.0,
                    uri = null
                )
            )
        )
    }
}
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SearchAddressContent_Preview_Night() {
    DeliveryAppTheme {
        SearchAddressContent(
            deliveryType = DeliveryType.DELIVERY,
            isLoading = false,
            isSearching = false,
            query = "state.query",
            departments = listOf(
                DepartmentModel(
                    id = 0,
                    name = "hjffgsg",
                    city = CityModel(id = 0, name = "fdf", subCities = emptyList(), latitude = 0.0, longitude = 0.0),
                    latitude = 0.0,
                    longitude = 0.0,
                    workingHours = emptyList(),
                    currentWorkingHours = null,
                    isWorkingNow = true
                )
            ),
            addresses = emptyList()
        )
    }
}