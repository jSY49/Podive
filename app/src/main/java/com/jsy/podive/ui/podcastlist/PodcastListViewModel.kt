package com.jsy.podive.ui.podcastlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsy.podive.data.model.PodcastGenre
import com.jsy.podive.data.repository.PodcastRepository
import com.jsy.podive.ui.explore.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PodcastListUiState(
    val selectedGenre: PodcastGenre? = null,    // null = 전체
    val list: ListState = ListState.Loading,
)

@HiltViewModel
class PodcastListViewModel @Inject constructor(
    private val repository: PodcastRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PodcastListUiState())
    val uiState: StateFlow<PodcastListUiState> = _uiState.asStateFlow()

    private var loadJob: Job? = null

    init {
        load()
    }

    fun selectGenre(genre: PodcastGenre?) {
        if (genre == _uiState.value.selectedGenre) return
        _uiState.update { it.copy(selectedGenre = genre) }
        load()
    }

    fun load() {
        // 장르를 빠르게 바꿀 때 이전 요청 결과가 나중에 덮어쓰지 않도록 취소한다
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.update { it.copy(list = ListState.Loading) }

            // TODO(paging) 지금은 limit 한 번에 전부 가져온다.
            //  iTunes 차트 RSS 는 offset 이 없어서 페이징을 하려면
            //  (1) limit 을 늘려 다시 받고 새로 생긴 부분만 이어붙이거나
            //  (2) 한 번에 받은 ID 목록을 들고 있다가 lookup 을 N개씩 나눠 호출하는 방식을 고민해보기
            val result = repository.getTopPodcasts(
                genreId = _uiState.value.selectedGenre?.id,
                limit = PAGE_LIMIT
            )
            _uiState.update {
                it.copy(
                    list = result.fold(
                        onSuccess = { list -> ListState.Success(list) },
                        onFailure = { e -> ListState.Error(e.message) }
                    )
                )
            }
        }
    }

    private companion object {
        const val PAGE_LIMIT = 30
    }
}
