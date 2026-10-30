package com.jsy.podive.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsy.podive.R
import com.jsy.podive.ui.theme.PodiveTheme

@Composable
fun ScreenHeader(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    @DrawableRes icon: Int? = null,
    @StringRes iconDescription: Int? = null,
    @StringRes backDescription: Int? = null,
    isBackPossible: Boolean = false,
    backClickEvent: () -> Unit = {},
    iconClickEvent: () -> Unit = {},
) {

    Row(
        modifier = modifier.padding(
            start = if (isBackPossible) 4.dp else 16.dp,
            end = 16.dp,
            bottom = 8.dp,
            top = 16.dp
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        //back
        if (isBackPossible) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = backDescription?.let { stringResource(it) },
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .clickable { backClickEvent() }
                    .padding(8.dp)
            )
        }

        //title area
        ProvideTextStyle(MaterialTheme.typography.headlineMedium) {
            title()
        }

        //ic
        icon?.let {

            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(icon),
                contentDescription = iconDescription?.let { stringResource(it) },
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .clickable { iconClickEvent() }
                    .padding(8.dp)
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun ScreenHeaderPreview() {

    PodiveTheme() {

        ScreenHeader(
            title = {
                Text(text = stringResource(R.string.explore_header_title))
            },
            icon = R.drawable.ic_library_music,
            isBackPossible = true
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun ScreenHeaderPreview2() {

    PodiveTheme() {

        ScreenHeader(
            title = {
                Icon(
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = stringResource(R.string.app_name),
                    tint = Color.Unspecified,   // 원본 색 유지 (안 주면 단색으로 덮어요)
                )

            },
            isBackPossible = false
        )
    }

}