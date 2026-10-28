package com.jsy.podive.ui.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsy.podive.ui.theme.PodiveTheme

enum class SegmentedControlType { Filled, Underline }

@Composable
fun SegmentedControl(
    items: List<String>,
    selectedIdx: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    type: SegmentedControlType = SegmentedControlType.Underline
) {

    when (type) {
        SegmentedControlType.Underline -> {
            UnderlineSegmentedControl(
                items,
                selectedIdx,
                onSelected,
                modifier
            )
        }

        SegmentedControlType.Filled -> {
            FilledSegmentedControl(
                items,
                selectedIdx,
                onSelected,
                modifier
            )
        }
    }
}

@Composable
private fun FilledSegmentedControl(
    items: List<String>,
    selectedIdx: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier
) {

    val indicatorPosition by animateFloatAsState(
        targetValue = selectedIdx.toFloat(),
        animationSpec = spring(
            stiffness = Spring.StiffnessMedium
        ),
        label = "indicatorPosition"
    )

    val indicatorColor = MaterialTheme.colorScheme.surfaceContainerHigh

    Row(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .padding(4.dp)
            .drawBehind {
                val segmentWidth = size.width / items.size
                drawRoundRect(
                    color = indicatorColor,
                    topLeft = Offset(
                        segmentWidth * indicatorPosition,
                        0f
                    ), //selectedIndex가 바뀌면 값이 1f → 2f
                    size = Size(
                        segmentWidth,
                        size.height
                    ),
                    cornerRadius = CornerRadius(8.dp.toPx()),
                )
            }
    ) {

        items.forEachIndexed { index, label ->

            val selected = index == selectedIdx
            val textColor by animateColorAsState(
                targetValue = if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                label = "textColor"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .selectable(
                        selected = selected,
                        role = Role.Tab,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onSelected(index) }
                    )
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = textColor
                )
            }
        }
    }

}

@Composable
private fun UnderlineSegmentedControl(
    items: List<String>,
    selectedIdx: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier
) {

    val indicatorPosition by animateFloatAsState(
        targetValue = selectedIdx.toFloat(),
        animationSpec = spring(
            stiffness = Spring.StiffnessMedium
        ),
        label = "indicatorPosition"
    )

    val indicatorColor = MaterialTheme.colorScheme.primary

    Row(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .padding(4.dp)
            .drawBehind {

                val segmentWidth = size.width / items.size
                val indicatorWidth = segmentWidth / 2
                val indicatorHeight = 2.dp.toPx()

                drawRoundRect(
                    color = indicatorColor,
                    topLeft = Offset(
                        segmentWidth * indicatorPosition + (segmentWidth - indicatorWidth) / 2,
                        size.height - indicatorHeight
                    ),
                    size = Size(
                        indicatorWidth,
                        indicatorHeight
                    ),
                    cornerRadius = CornerRadius(8.dp.toPx()),
                )
            }
    ) {

        items.forEachIndexed { index, label ->

            val selected = index == selectedIdx
            val textColor by animateColorAsState(
                targetValue = if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                label = "textColor"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .selectable(
                        selected = selected,
                        role = Role.Tab,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onSelected(index) }
                    )
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = textColor
                )
            }
        }
    }

}

@Preview(
    showBackground = true
)
@Composable
private fun SegmentedControlPreview() {

    PodiveTheme() {

        Column() {
            SegmentedControl(
                items = listOf(
                    "1번",
                    "2번"
                ),
                selectedIdx = 1,
                onSelected = {},
                type = SegmentedControlType.Underline
            )

            SegmentedControl(
                items = listOf(
                    "1번",
                    "2번"
                ),
                selectedIdx = 0,
                onSelected = {},
                type = SegmentedControlType.Filled
            )
        }
    }
}