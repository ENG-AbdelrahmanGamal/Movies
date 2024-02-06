package com.example.movies.moviesList.data.mappers

import com.example.movies.moviesList.data.local.movie.MovieEntity
import com.example.movies.moviesList.data.remote.responses.responseMovieList.MovieListTransfer
import com.example.movies.moviesList.domain.model.Movies


fun MovieListTransfer.toMovieEntity(category: String):MovieEntity{
    return MovieEntity (
        adult=adult?:false,
        backdrop_path=backdrop_path?:"",
        // genre_ids: List<Int>,
        original_language=original_language?:"",
        original_title=original_title?:"",
        overview=overview?:"",
        popularity=popularity?:0.0,
        poster_path= poster_path?:"",
        release_date=release_date?:"",
        title=title?:"",
        video=video?:false,
        vote_average=vote_average?:0.0,
        vote_count= vote_count?:0,
        id=id?:-1,
        category=category,
        genre_ids=try {
            genre_ids?.joinToString(",")?:"-1,-2"
        }catch (e:Exception)
        {
            "-1,-2"
        }
    )

}



fun MovieEntity.toMovie(category: String):Movies{
    return Movies(
        backdrop_path=backdrop_path,
     original_language=original_language,
     original_title=original_title,
     overview=overview,
     popularity=popularity,
     poster_path= poster_path,
     release_date=release_date,
     title=title,
     video=video,
     vote_average=vote_average,
     vote_count= vote_count,
      adult = adult,
    id=id,
     category=category,
        genre_ids= try {
            genre_ids.split(",").map{it.toInt() }
        }catch (e:Exception)
        {
            listOf(-1,-2)
        }
        )
}