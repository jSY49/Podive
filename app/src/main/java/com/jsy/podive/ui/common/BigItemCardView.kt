package com.jsy.podive.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun BigItemCardView(
    title: String,
    artworkUrl: String,
    desc: String,
    genre: String,
    modifier: Modifier = Modifier,
    onClick : () -> Unit = {}
) {

    //TODO 이미지 로딩 안될 때 대응 이미지 필요함

    val bgColor = rememberArtworkColor(
        artworkUrl,
        MaterialTheme.colorScheme.primary
    )
    Card(
        modifier = modifier.aspectRatio(276f / 208f),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = bgColor,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
    ) {
        Box(
            modifier = Modifier.padding(
                horizontal = 24.dp,
                vertical = 22.dp
            )
        ) {
            //genre
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = genre,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            //info
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = artworkUrl,
                    contentDescription = title,
                    modifier = Modifier
                        .size(96.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }
    }
}

@Preview(
    showBackground = false
)
@Composable
private fun BigItemCardViewPreview() {
    BigItemCardView(
        title = "Cut to Black",
        artworkUrl = "https://images.unsplash.com/photo-1775840966242-d4dadb667541?q=80&w=1015&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=NHwxMjA3fDA%3D",
        desc = "엔딩 크레딧 이후의 이야기",
        genre = "영화",
    )
}