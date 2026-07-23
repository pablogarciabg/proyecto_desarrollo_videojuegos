package com.pmdm.mygamestore.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InfoColumn(
    label: String,
    value: String = "",
    content: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = Modifier.width(180.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge
        )
        if (content != null) {
            content()
        } else {
            Text(text = value)
        }
    }
}

@Composable
fun MetacriticBadge(score: Int) {
    Surface(color = Color(0xFF00CE7A), shape = RoundedCornerShape(4.dp)) {
        Text(text = score.toString(), color = Color.White)
    }
}