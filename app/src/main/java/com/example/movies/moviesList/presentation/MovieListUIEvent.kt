package com.example.movies.moviesList.presentation

sealed interface MovieListUIEvent {



    data class Paginate( val category:String):MovieListUIEvent
        object Navigate: MovieListUIEvent {


        }

}