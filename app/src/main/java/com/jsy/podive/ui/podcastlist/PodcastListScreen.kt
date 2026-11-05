package com.jsy.podive.ui.podcastlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.jsy.podive.R
import com.jsy.podive.data.model.Podcast
import com.jsy.podive.data.model.PodcastGenre
import com.jsy.podive.ui.common.ChipComponent
import com.jsy.podive.ui.common.ErrorRetry
import com.jsy.podive.ui.common.ScreenHeader
import com.jsy.podive.ui.common.skeleton.shimmer
import com.jsy.podive.ui.explore.ListState

@Composable
fun PodcastListScreen(
    onBack: () -> Unit,
    onPodcastClick: (Podcast) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PodcastListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        ScreenHeader(
            title = { Text(text = stringResource(R.string.podcast_list_title)) },
            isBackPossible = true,
            backDescription = R.string.common_back,
            backClickEvent = onBack
        )

        // 장르 필터 (null = 전체)
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                GenreChip(
                    text = stringResource(R.string.podcast_list_genre_all),
                    selected = uiState.selectedGenre == null,
                    onClick = { viewModel.selectGenre(null) }
                )
            }
            items(PodcastGenre.entries) { genre ->
                GenreChip(
                    text = stringResource(genre.label),
                    selected = uiState.selectedGenre == genre,
                    onClick = { viewModel.selectGenre(genre) }
                )
            }
        }

        when (val list = uiState.list) {
            is ListState.Loading -> LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                userScrollEnabled = false
            ) {
                items(8) { PodcastRowSkeleton() }
            }

            is ListState.Error -> ErrorRetry(onRetry = viewModel::load)

            // TODO(paging) 끝까지 스크롤하면 다음 데이터를 이어서 불러오기 (LazyListState 로 마지막 아이템 감지)
            is ListState.Success -> LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(list.podcasts, key = { _, it -> it.id }) { index, podcast ->
                    PodcastRow(
                        rank = index + 1,
                        podcast = podcast,
                        onClick = { onPodcastClick(podcast) }
                    )
                }
            }
        }
    }
}

@Composable
private fun GenreChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    ChipComponent(
        containerColor = if (selected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surfaceVariant,
        contentColor = if (selected) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurfaceVariant,
        text = text,
        onClick = onClick
    )
}

@Composable
private fun PodcastRow(
    rank: Int,
    podcast: Podcast,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = rank.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        AsyncImage(
            model = podcast.artworkUrl,
            contentDescription = podcast.title,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = podcast.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = podcast.author,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = podcast.genre,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun PodcastRowSkeleton(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .shimmer()
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmer()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmer()
            )
        }
    }
}
