package com.example.movies.moviesList.data.repository

import android.graphics.Movie
import android.net.http.HttpException
import com.example.movies.moviesList.data.local.movie.MovieDatabase
import com.example.movies.moviesList.data.mappers.toMovie
import com.example.movies.moviesList.data.mappers.toMovieEntity
import com.example.movies.moviesList.data.remote.Api
import com.example.movies.moviesList.domain.model.Movies
import com.example.movies.moviesList.domain.reposiitory.MovieListRepository
import com.example.movies.moviesList.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: Api,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {

    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movies>>> {
        return flow {

            emit(Resource.Loading(true))

            val localMovieList = movieDatabase.moviesDao.getListMoviesByCatigory(category)

            val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadLocalMovie) {
                emit(Resource.Success(
                    data = localMovieList.map { movieEntity ->
                        movieEntity.toMovie(category)
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val movieListFromApi = try {
                movieApi.getMoviesList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            val movieEntities = movieListFromApi.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }

            movieDatabase.moviesDao.upsertMovies(movieEntities)

            emit(Resource.Success(
                movieEntities.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))

        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movies>> {
        return flow {

            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.moviesDao.getListMoviesById(id)

            if (movieEntity != null) {
                emit(
                    Resource.Success(movieEntity.toMovie(movieEntity.category))
                )

                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("Error no such movie"))

            emit(Resource.Loading(false))


        }
    }
}