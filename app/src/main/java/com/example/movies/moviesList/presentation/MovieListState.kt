package com.example.movies.moviesList.presentation

import com.example.movies.moviesList.domain.model.Movies

data class MovieListState(
    val isLoading: Boolean = false,
    val popualrMovieListPage: Int = 1,
    val upcomingMovieListPage: Int = 1,

    val isCurrentPopularScreen:Boolean=true,
    val isCurrentUpcomingScreen:Boolean=true,


    val popualrMovieList:List<Movies> = emptyList(),
    val upcomingMovieList:List<Movies> = emptyList()
)