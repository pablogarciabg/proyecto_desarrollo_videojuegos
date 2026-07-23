package com.pmdm.mygamestore.presentation.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun ExpandableText(text: String, maxLines: Int = 4) {
    var expanded by remember { mutableStateOf(false) }
    var isClickable by remember { mutableStateOf(false) }

    Column(modifier = Modifier.animateContentSize()) {
        Text(
            text = text,
            maxLines = if (expanded) Int.MAX_VALUE else maxLines,
            onTextLayout = { result ->
                if (result.hasVisualOverflow || result.lineCount > maxLines) isClickable = true
            }
        )
        if (isClickable) {
            Text(
                text = if (expanded) "Show less" else "Show more",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { expanded != expanded }
            )
        }
    }
}