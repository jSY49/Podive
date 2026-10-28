package com.jsy.podive.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsy.podive.R
import com.jsy.podive.ui.theme.PodiveTheme

@Composable
fun ChipComponent(
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    @DrawableRes icon: Int? = null,
    iconDescription: String? = null,
) {

//    require(text != null || icon != null)   //둘다 없으면 crash
    if (text == null && icon == null) return

    Row(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .clip(shape = RoundedCornerShape(18.dp))
            .clickable(
                role = Role.Button,
                onClick = onClick
            )
            .background(color = containerColor)
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        icon?.let {
            Icon(
                painter = painterResource(icon),
                tint = contentColor,
                contentDescription = if (text == null) iconDescription else null
            )
        }

        text?.let {
            Text(
                text = text,
                style = textStyle,
                color = contentColor
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0XFF808080
)
@Composable
private fun ChipComponentPreview() {

    PodiveTheme {
        Column() {
            ChipComponent(
                onClick = {},
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                text = "dddd",
                icon = R.drawable.ic_close,
                iconDescription = "ddd",
            )
            ChipComponent(
                onClick = {},
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                icon = R.drawable.ic_close,
                iconDescription = "ddd",
            )
        }
    }
}