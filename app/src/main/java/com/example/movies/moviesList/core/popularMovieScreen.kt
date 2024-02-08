package com.example.movies.moviesList.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.movies.moviesList.presentation.MovieListState
import com.example.movies.moviesList.presentation.MovieListUIEvent
import java.util.Locale.Category

@Composable
fun popularMovieScreen(
    movieListState: MovieListState,
    navHostController: NavHostController,
    onEvent:(MovieListUIEvent)-> Unit

){
if(movieListState.popualrMovieList.isEmpty())
{
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
CircularProgressIndicator()
    }
}
    else{
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)
        ){
            items(movieListState.popualrMovieList.size) {index ->

                MovieItem(
                    movie = movieListState.popualrMovieList[index],
                    navHostController = navHostController
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                if(index>=movieListState.popualrMovieList.size -1&& ! movieListState.isLoading)
                {
                    onEvent(MovieListUIEvent.Paginate(com.example.movies.moviesList.util.Category.POPULAR))

                }
            }


        }
    }
}