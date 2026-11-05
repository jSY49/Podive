package com.jsy.podive.ui.podcast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.jsy.podive.R
import com.jsy.podive.data.model.Episode
import com.jsy.podive.data.model.PodcastDetail
import com.jsy.podive.ui.common.ChipComponent
import com.jsy.podive.ui.common.ErrorRetry
import com.jsy.podive.ui.common.rememberArtworkColor
import com.jsy.podive.ui.common.skeleton.shimmer

@Composable
fun PodcastDetailScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PodcastDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        when (val state = uiState) {
            is PodcastDetailUiState.Success -> DetailContent(
                detail = state.detail,
                onBack = onBack
            )

            is PodcastDetailUiState.Loading -> DetailLoading(onBack = onBack)

            is PodcastDetailUiState.Error -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                BackBar(onBack = onBack, contentColor = MaterialTheme.colorScheme.onBackground)
                ErrorRetry(onRetry = viewModel::load)
            }
        }
    }
}

@Composable
private fun DetailContent(
    detail: PodcastDetail,
    onBack: () -> Unit,
) {
    val podcast = detail.podcast
    val headerColor = rememberArtworkColor(podcast.artworkUrl, MaterialTheme.colorScheme.primary)
    // 아트워크 평균색이 밝으면 글자를 어둡게
    val onHeader = if (headerColor.luminance() > 0.5f) MaterialTheme.colorScheme.onBackground
    else Color.White
    val context = LocalContext.current

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        // 상단 헤더 (아트워크 색 배경)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerColor)
                    .statusBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BackBar(onBack = onBack, contentColor = onHeader, showMore = true)
                Spacer(Modifier.height(8.dp))
                AsyncImage(
                    model = podcast.artworkUrl,
                    contentDescription = podcast.title,
                    modifier = Modifier
                        .size(168.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    text = podcast.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = onHeader,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Text(
                    text = podcast.author,
                    style = MaterialTheme.typography.bodyMedium,
                    color = onHeader.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(Modifier.height(20.dp))

                var subscribed by remember { mutableStateOf(false) }   // TODO 구독 저장은 라이브러리 구현할 때
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ChipComponent(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        text = stringResource(R.string.podcast_detail_play_latest),
                        icon = R.drawable.ic_play_arrow,
                        onClick = {
                            // TODO 뮤직 플레이어: detail.episodes.firstOrNull() 재생
                        }
                    )
                    ChipComponent(
                        containerColor = onHeader.copy(alpha = 0.2f),
                        contentColor = onHeader,
                        text = stringResource(
                            if (subscribed) R.string.podcast_detail_subscribed
                            else R.string.podcast_detail_subscribe
                        ),
                        icon = if (subscribed) R.drawable.ic_check else null,
                        onClick = { subscribed = !subscribed }
                    )
                }
                Spacer(Modifier.height(24.dp))

                // 흰 시트의 둥근 윗모서리
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }

        // 설명 + 메타
        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (podcast.desc.isNotBlank()) {
                    Text(
                        text = podcast.desc,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = stringResource(
                        R.string.podcast_detail_meta,
                        podcast.genre,
                        podcast.episodeCount
                    ),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            Text(
                text = stringResource(R.string.podcast_detail_episodes),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp)
            )
        }

        // TODO(paging) 지금은 최신 N개만 한 번에 받아온다. 스크롤 끝에서 더 불러오는 페이징으로 바꿔보기
        items(detail.episodes, key = { it.id }) { episode ->
            EpisodeItem(
                episode = episode,
                dateDuration = stringResource(
                    R.string.podcast_detail_date_duration,
                    formatReleaseDate(episode.releaseDate),
                    formatDuration(context, episode.durationMillis)
                ).trim(' ', '·'),
                onPlay = {
                    // TODO 뮤직 플레이어: episode.audioUrl 재생
                }
            )
        }

        item { Spacer(Modifier.height(24.dp).navigationBarsPadding()) }
    }
}

@Composable
private fun EpisodeItem(
    episode: Episode,
    dateDuration: String,
    onPlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = dateDuration,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = episode.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            val desc = remember(episode.description) { episode.description.stripHtml() }
            if (desc.isNotBlank()) {
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            ChipComponent(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                text = stringResource(R.string.podcast_detail_play),
                icon = R.drawable.ic_play_arrow,
                onClick = onPlay
            )
        }
    }
}

@Composable
private fun BackBar(
    onBack: () -> Unit,
    contentColor: Color,
    modifier: Modifier = Modifier,
    showMore: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_arrow_back),
            contentDescription = stringResource(R.string.common_back),
            tint = contentColor,
            modifier = Modifier
                .minimumInteractiveComponentSize()
                .clickable(onClick = onBack)
                .padding(8.dp)
        )
        Spacer(Modifier.weight(1f))
        if (showMore) {
            // TODO 더보기 메뉴 (공유 등)
            Icon(
                painter = painterResource(R.drawable.ic_more_vert),
                contentDescription = stringResource(R.string.common_more),
                tint = contentColor,
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .padding(8.dp)
            )
        }
    }
}

@Composable
private fun DetailLoading(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackBar(onBack = onBack, contentColor = MaterialTheme.colorScheme.onPrimary)
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .size(168.dp)
                .clip(RoundedCornerShape(24.dp))
                .shimmer()
        )
        Spacer(Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .size(width = 180.dp, height = 24.dp)
                .clip(RoundedCornerShape(6.dp))
                .shimmer()
        )
    }
}
