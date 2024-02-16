package com.example.movies.moviesList.data.local.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface MoviesDao {

        @Upsert
    suspend fun upsertMovies(moviesList: List<MovieEntity>)

    @Query("SELECT * From MovieEntity Where id =:id")
  suspend fun getListMoviesById(id:Int): MovieEntity

    @Query("SELECT * From MovieEntity Where category =:category")
    suspend fun getListMoviesByCatigory(category:String):List<MovieEntity>


}