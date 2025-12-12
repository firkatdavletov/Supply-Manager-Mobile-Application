package org.example.project.feature.map_view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.DeliveryAppTheme
import org.example.project.R

@Composable
fun TooltipComponent(
    modifier: Modifier = Modifier,
    isSearching: Boolean,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_marker_point_32),
            tint = MaterialTheme.colorScheme.primaryContainer,
            contentDescription = null
        )
        Spacer(
            modifier = Modifier
                .height(if (isSearching) 6.dp else 3.dp)
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_marker_ellipse_18),
            tint = MaterialTheme.colorScheme.primaryContainer,
            contentDescription = null
        )
    }
}

@Preview()
@Composable
private fun TooltipComponent_Preview() {
    DeliveryAppTheme {
        TooltipComponent(
            modifier = Modifier,
            isSearching = false,
        )
    }
}