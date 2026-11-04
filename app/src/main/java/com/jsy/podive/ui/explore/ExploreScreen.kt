package com.jsy.podive.ui.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jsy.podive.R
import com.jsy.podive.data.model.Podcast
import com.jsy.podive.data.model.PodcastGenre
import com.jsy.podive.ui.common.BigItemCardView
import com.jsy.podive.ui.common.ChipComponent
import com.jsy.podive.ui.common.ScreenHeader
import com.jsy.podive.ui.common.SegmentedControl
import com.jsy.podive.ui.common.SmallItemCardView
import com.jsy.podive.ui.common.skeleton.BigItemCardSkeleton
import com.jsy.podive.ui.common.skeleton.SmallItemCardSkeleton
import com.jsy.podive.ui.theme.PodiveTheme

@Composable
fun ExploreScreen(modifier: Modifier = Modifier,   viewModel: ExploreViewModel = viewModel()) {

    var selectedTab by remember { mutableStateOf(ExploreTab.PodcastSearch) }
    var rssUrl by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()  //collectAsStateWithLifecycle :Flow 를 Compose 의 State 로 바꿔서 값이 바뀌면 화면을 다시 그리기 , 앱이 백그라운드 가면 수집 멈춤


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        ScreenHeader(
            title = {
                Text(text = stringResource(R.string.explore_header_title))
            },
        )

        SegmentedControl(
            items = ExploreTab.entries.map { stringResource(it.label) },
            selectedIdx = selectedTab.ordinal,
            onSelected = { selectedTab = ExploreTab.entries[it] }
        )

        when (selectedTab) {
            ExploreTab.PodcastSearch -> PodcastSearchTab(
                nowTop = uiState.nowTop,
                genreTop = uiState.genreTop,
                onRetryNowTop = viewModel::loadNowTop,
                onRetryGenreTop = viewModel::loadGenreTop,
                onSearchClick = {
                    // TODO 검색 화면으로 이동 (검색어 입력 후 PodcastRepository.searchPodcast(term) 호출)
                },
                onPodcastClick = {
                    // TODO 팟캐스트 상세 화면으로 이동
                },
                onViewTotalClick = {
                    // TODO 지금 인기 전체 보기 화면으로 이동
                },
                modifier = Modifier.weight(1f)
            )

            ExploreTab.RssUrl -> RssUrlTab(
                url = rssUrl,
                onUrlChange = { rssUrl = it },
                onSubmit = {
                    // TODO 네트워크: RSS(feedUrl) 파싱해서 팟캐스트/에피소드 가져오기
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun PodcastSearchTab(
    nowTop: ListState,
    genreTop: GenreListState,
    onRetryNowTop: () -> Unit,
    onRetryGenreTop: () -> Unit,
    onSearchClick: () -> Unit,
    onPodcastClick: (Podcast) -> Unit,
    onViewTotalClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // 검색창
        item {
            SearchBar(
                onClick = onSearchClick,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // 지금 인기
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.explore_chart_now_top),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }

                when (nowTop) {
                    is ListState.Loading -> LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        userScrollEnabled = false
                    ) {
                        items(2) { BigItemCardSkeleton(Modifier.width(276.dp)) }
                    }

                    is ListState.Success -> LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(nowTop.podcasts, key = { it.id }) { podcast ->
                            BigItemCardView(
                                title = podcast.title,
                                artworkUrl = podcast.artworkUrl,
                                desc = podcast.desc,
                                genre = podcast.genre,
                                onClick = { onPodcastClick(podcast) },
                                modifier = Modifier.width(276.dp)
                            )
                        }
                    }

                    is ListState.Error -> ErrorRetry(onRetry = onRetryNowTop)
                }
            }
        }

        // 분야별 인기
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.explore_chart_genre_top),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(R.string.common_view_total),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.clickable(onClick = onViewTotalClick)
                )
            }
        }

        // 2열 그리드 (LazyColumn 안이라 2개씩 묶어서 Row 로 그림)
        when (genreTop) {
            is GenreListState.Loading -> items(3) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SmallItemCardSkeleton(Modifier.weight(1f))
                    SmallItemCardSkeleton(Modifier.weight(1f))
                }
            }

            is GenreListState.Success -> items(genreTop.items.chunked(2)) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowItems.forEach { (genre, podcast) ->
                        SmallItemCardView(
                            title = podcast.title,
                            artworkUrl = podcast.artworkUrl,
                            sub = stringResource(genre.label),
                            onClick = { onPodcastClick(podcast) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (rowItems.size == 1) Spacer(Modifier.weight(1f))
                }
            }

            is GenreListState.Error -> item { ErrorRetry(onRetry = onRetryGenreTop) }
        }
    }
}

@Composable
private fun ErrorRetry(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "불러오지 못했어요", // TODO strings.xml 로 이동
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(8.dp))
        ChipComponent(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            text = "다시 시도", // TODO strings.xml 로 이동
            icon = R.drawable.ic_refresh,
            onClick = onRetry
        )
    }
}

@Composable
private fun SearchBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = stringResource(R.string.explore_chart_search_desc),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun RssUrlTab(
    url: String,
    onUrlChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO RSS 탭 상세 UI (안내 문구, 확인 버튼, 로딩/에러 상태) - 디자인 나오면 추가
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = url,
            onValueChange = onUrlChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_link),
                    contentDescription = null
                )
            },
            placeholder = { Text(text = "https://") },
            shape = RoundedCornerShape(16.dp)
        )
        Spacer(Modifier.height(12.dp))
        ChipComponent(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            text = stringResource(R.string.explore_tab_rss),
            icon = R.drawable.ic_rss_feed,
            onClick = onSubmit
        )
    }
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
private fun ExploreScreenPreview() {
    PodiveTheme {
        ExploreScreen()
    }
}
