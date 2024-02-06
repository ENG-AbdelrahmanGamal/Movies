package com.example.movies.moviesList.data.remote.responses.responseMovieList

data class MovieListDataTransfer(
    val page: Int,
    val results: List<MovieListTransfer>,
    val total_pages: Int,
    val total_results: Int
)