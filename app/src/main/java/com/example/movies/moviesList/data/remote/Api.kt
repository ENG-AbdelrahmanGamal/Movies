package com.example.movies.moviesList.data.remote

import com.example.movies.moviesList.data.remote.responses.responseMovieList.MovieListDataTransfer
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {

    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page:Int,
        @Query("api_key") api_key:String=API_KEY

    ):MovieListDataTransfer

    companion object{

        const val BASE_URL="https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL="https://image.tmdb.org/t/p/w500"
        const val API_KEY="abaca52e53f445dd59c758dae5fdfbea"
    }


}
