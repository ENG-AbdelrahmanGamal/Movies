package com.example.movies.moviesList.util

sealed class Screen (val route:String){

    object Home:Screen ("main")
    object Details :Screen("details")
    object UpcomingMoveList :Screen ("UpcomingMovie")
    object PopularMovieList :Screen("popularMovie")

}