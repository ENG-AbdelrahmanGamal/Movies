package com.example.movies.moviesList.data.local.movie

import android.os.Build.VERSION_CODES
import androidx.room.Database
import androidx.room.RoomDatabase
import com.google.android.engage.video.datamodel.MovieEntity


@Database(entities = [MovieEntity::class], version = 1)
abstract  class MovieDataBase :RoomDatabase(){

   abstract val moviesDao :MoviesDao



}