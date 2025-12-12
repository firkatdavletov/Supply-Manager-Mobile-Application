package org.example.project.feature.ui_components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.DeliveryAppTheme

@Composable
fun SelectedButton(
    title: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    if (selected) {
        Button(
            modifier = modifier,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            onClick = onClick
        ) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = title
            )
        }
    } else {
        OutlinedButton(
            modifier = modifier,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primaryContainer
            ),
            onClick = onClick
        ) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = title
            )
        }
    }
}

@Preview
@Composable
private fun SelectedButton_Preview() {
    DeliveryAppTheme {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            SelectedButton(
                modifier = Modifier.weight(1f),
                title = "Доставка",
                selected = true
            )
            SelectedButton(
                modifier = Modifier.weight(1f),
                title = "Самовывоз",
                selected = false
            )
        }
    }
}