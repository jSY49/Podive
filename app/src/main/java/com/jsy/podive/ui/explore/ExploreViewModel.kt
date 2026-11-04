package com.jsy.podive.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsy.podive.data.PodcastRepository
import com.jsy.podive.data.PodcastRepositoryImpl
import com.jsy.podive.data.model.Podcast
import com.jsy.podive.data.model.PodcastGenre
import com.jsy.podive.data.remote.NetworkModule
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface ListState {
    data object Loading : ListState
    data class Success(val podcasts: List<Podcast>) : ListState
    data class Error(val message: String?) : ListState
}

sealed interface GenreListState {
    data object Loading : GenreListState
    data class Success(val items: List<Pair<PodcastGenre, Podcast>>) : GenreListState
    data class Error(val message: String?) : GenreListState
}

data class ExploreUiState(
    val nowTop: ListState = ListState.Loading,
    val genreTop: GenreListState = GenreListState.Loading,
)

class ExploreViewModel(
    private val repository: PodcastRepository = PodcastRepositoryImpl(NetworkModule.itunesApi)
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExploreUiState())
    val uiState: StateFlow<ExploreUiState> = _uiState.asStateFlow()

    init {
        loadNowTop()
        loadGenreTop()
    }

    fun loadNowTop() {
        //지금 인기
        viewModelScope.launch {
            _uiState.update { it.copy(nowTop = ListState.Loading) }
            val result = repository.getTopPodcasts(limit = 5)
            _uiState.update {
                it.copy(
                    nowTop = result.fold(
                    onSuccess = { list -> ListState.Success(list) },
                    onFailure = { e -> ListState.Error(e.message) }
                ))
            }
        }
    }

    fun loadGenreTop() {
        //분야별 인기
        viewModelScope.launch {
            _uiState.update { it.copy(genreTop = GenreListState.Loading) }

            val items = PodcastGenre.entries.map {genre ->
                async {
                    repository.getTopPodcasts(genreId = genre.id , limit = 1)
                        .getOrNull()
                        ?.firstOrNull()
                        ?.let{ podcast -> genre to podcast}
                }
            }.awaitAll().filterNotNull()

            _uiState.update {
                it.copy(
                    genreTop = if (items.isEmpty()) GenreListState.Error(null)
                    else GenreListState.Success(items)
                )
            }
        }
    }
}