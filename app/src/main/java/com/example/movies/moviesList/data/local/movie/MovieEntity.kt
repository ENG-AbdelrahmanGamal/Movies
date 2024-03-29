package com.example.movies.moviesList.data.local.movie

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters


//@TypeConverters(MovieEntity::class)
@Entity(tableName = "MovieEntity")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val category: String
)