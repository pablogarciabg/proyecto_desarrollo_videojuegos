package com.pmdm.mygamestore.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.pmdm.mygamestore.presentation.ui.theme.dimens

@Composable
fun TagsFlowRow(items: List<String>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small),
        maxLines = 2
    ) {
      items.forEach { DetailChip(it) }
    }
}