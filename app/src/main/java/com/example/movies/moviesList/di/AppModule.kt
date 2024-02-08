package com.example.movies.moviesList.di

import android.app.Application
import androidx.room.Room
import com.example.movies.moviesList.data.local.movie.MovieDatabase
import com.example.movies.moviesList.data.remote.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level=HttpLoggingInterceptor.Level.BODY
    }

    private val client:OkHttpClient=OkHttpClient.Builder().addInterceptor(interceptor).build()
    @Provides
    @Singleton
    fun provideMovieApi():Api
    {
        return Retrofit.Builder().baseUrl(Api.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Api::class.java)
    }
    @Provides
    @Singleton
    fun provideMovieDatabase(application: Application):MovieDatabase{
        return Room.databaseBuilder(application,MovieDatabase::class.java,"movie_database").build()
    }



//    @Provides
//    @Singleton
//    fun provideOkHttp():OkHttpClient{
//        return OkHttpClient.Builder().connectTimeout(20,TimeUnit.SECONDS)
//            .readTimeout(20,TimeUnit.SECONDS)
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
//        return Retrofit.Builder().baseUrl(Api.BASE_URL).client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideMovieDatabase2(application: Application):MovieDataBase{
//        return Room.databaseBuilder(application,MovieDataBase::class.java,"movie.db").build()
//    }



//    fun provideRetrofit2(okHttpClient: OkHttpClient): Retrofit{
//        return Retrofit.Builder()
//            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    fun provideOkHttp2(): OkHttpClient {
//        return OkHttpClient.Builder()
//            .connectTimeout(20, TimeUnit.SECONDS)
//            .readTimeout(20, TimeUnit.SECONDS)
//            .build()
//    }






}