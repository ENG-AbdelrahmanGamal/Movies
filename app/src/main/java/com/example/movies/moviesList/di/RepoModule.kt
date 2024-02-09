package com.example.movies.moviesList.di

import com.example.movies.moviesList.data.repository.MovieListRepositoryImpl
import com.example.movies.moviesList.domain.reposiitory.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    @Singleton
    abstract fun provideMovieListRepository(movieListRepositoryImpl: MovieListRepositoryImpl):
            MovieListRepository

}