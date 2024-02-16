package com.example.movies.moviesList.domain.reposiitory

import com.example.movies.moviesList.domain.model.Movies
import com.example.movies.moviesList.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {


   suspend fun getMovieList(
      forceFetchFromRemote:Boolean
   ,category:String,
      page:Int): Flow<Resource<List<Movies>>>
   suspend fun getMovie(id:Int):Flow<Resource<Movies>>

}