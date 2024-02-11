package com.example.movies.moviesList.details.presentation

import com.example.movies.moviesList.domain.model.Movies

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movies? = null
)
