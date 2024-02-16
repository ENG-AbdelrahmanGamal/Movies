package com.example.movies.moviesList.data.local.movie

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter


@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract  class MovieDatabase :RoomDatabase(){
   abstract val moviesDao :MoviesDao



}