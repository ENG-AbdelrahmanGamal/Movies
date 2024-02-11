package com.example.movies.moviesList.presentation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.moviesList.domain.reposiitory.MovieListRepository
import com.example.movies.moviesList.util.Category
import com.example.movies.moviesList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel() {

    private val _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()

    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)

    }


    fun onEvent(event: MovieListUIEvent) {
        when (event) {
            MovieListUIEvent.Navigate -> {
                _movieListState.update {
                    it.copy(
                        isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen
                    )
                }
            }

            is MovieListUIEvent.Paginate -> {
                if (event.category == Category.POPULAR) {
                    getPopularMovieList(true)
                } else if (event.category == Category.UPCOMING) {
                    getUpcomingMovieList(true)

                }
            }
        }
    }

    private fun getUpcomingMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update { it.copy(isLoading = true) }
            movieListRepository.getMovieList(
                forceFetchFromRemote, Category.UPCOMING,
                movieListState.value.upcomingMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update { it.copy(isLoading = false) }
                    }

                    is Resource.Loading -> {
                        _movieListState.update { it.copy(isLoading = result.isLoading) }
                    }

                    is Resource.Success -> {
                        result.data?.let {upCommingList->
                            _movieListState.update {
                                it.copy(upcomingMovieList = movieListState.value.upcomingMovieList
                                            + upCommingList.shuffled(),
                                    upcomingMovieListPage = movieListState.value.upcomingMovieListPage+1
                                )
                            }
                        }
                    }
                }
            }

        }
    }

    private fun getPopularMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update { it.copy(isLoading = true) }
            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.POPULAR,
                movieListState.value.popualrMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update { it.copy(isLoading = false) }
                    }

                    is Resource.Loading -> {
                        _movieListState.update { it.copy(isLoading = result.isLoading) }
                    }

                    is Resource.Success -> {
                        result.data?.let { popularList ->
                            _movieListState.update {
                                it.copy(
                                    popualrMovieList = movieListState.value.popualrMovieList
                                            + popularList.shuffled(),
                                    popualrMovieListPage = movieListState.value.popualrMovieListPage+1
                                )
                            }
                        }
                    }
                }
            }

        }

    }
}