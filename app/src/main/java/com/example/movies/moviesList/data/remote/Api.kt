package com.example.movies.moviesList.data.remote

import androidx.core.os.BuildCompat
import com.example.movies.BuildConfig
import com.example.movies.moviesList.data.remote.responses.responseMovieList.MovieListDataTransfer
import com.google.gson.internal.GsonBuildConfig
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
        //protect API KEY and push to github
         val API_KEY=BuildConfig.API_KEY
    }


}
