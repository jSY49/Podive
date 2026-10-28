package com.jsy.podive.ui.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LibraryScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.background(MaterialTheme.colorScheme.secondary).fillMaxSize()) { }

}

@Preview(
    name = "Phone",
    widthDp = 360,
    heightDp = 800,
    showBackground = true
)
@Preview(
    name = "Phone_flip",
    widthDp = 360,
    heightDp = 880,
    showBackground = true
)
@Preview(
    name = "Tablet",
    widthDp = 1280,
    heightDp = 800,
    showBackground = true
)
@Preview(
    name = "Foldable",
    widthDp = 673,
    heightDp = 841,
    showBackground = true
)
@Composable
private fun LibraryScreenPreview() {
    LibraryScreen()
}