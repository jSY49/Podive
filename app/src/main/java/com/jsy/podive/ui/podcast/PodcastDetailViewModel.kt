package com.jsy.podive.ui.podcast

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsy.podive.data.model.PodcastDetail
import com.jsy.podive.data.repository.PodcastRepository
import com.jsy.podive.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface PodcastDetailUiState {
    data object Loading : PodcastDetailUiState
    data class Success(val detail: PodcastDetail) : PodcastDetailUiState
    data class Error(val message: String?) : PodcastDetailUiState
}

@HiltViewModel
class PodcastDetailViewModel @Inject constructor(
    private val repository: PodcastRepository,
    savedStateHandle: SavedStateHandle,     // 네비게이션 인자(id, desc)가 들어있다
) : ViewModel() {

    private val podcastId: Long = checkNotNull(savedStateHandle[Routes.ARG_ID])
    private val desc: String = savedStateHandle[Routes.ARG_DESC] ?: ""

    private val _uiState = MutableStateFlow<PodcastDetailUiState>(PodcastDetailUiState.Loading)
    val uiState: StateFlow<PodcastDetailUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = PodcastDetailUiState.Loading
            _uiState.value = repository.getPodcastDetail(podcastId, desc).fold(
                onSuccess = { PodcastDetailUiState.Success(it) },
                onFailure = { PodcastDetailUiState.Error(it.message) }
            )
        }
    }
}
