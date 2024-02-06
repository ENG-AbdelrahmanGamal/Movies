package com.example.movies.moviesList.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.movies.moviesList.data.local.movie.MovieDataBase
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
    private val movieDataBase: MovieDataBase
) : MovieListRepository {

    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movies>>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovieList = movieDataBase.moviesDao.getListMoviesByCatigory(category)
            val shouldLoadingLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadingLocalMovie) {
                emit(Resource.Success(data = localMovieList.map { movieEntity ->
                    movieEntity.toMovie(category)
                }))
                emit(Resource.Loading(false))
                return@flow

            }
            val movieListFromApi = try {
                movieApi.getMoviesList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "ErrorIO Loading Movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "ErrorHttp Loading Movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading Movies"))
                return@flow

            }
            val MovieEntities = movieListFromApi.results.let {
                it.map { movieTransfer ->
                    movieTransfer.toMovieEntity(category)
                }
            }
            movieDataBase.moviesDao.upsertMovies(MovieEntities)
            emit(Resource.Success(MovieEntities.map { movieEntity -> movieEntity.toMovie(category) }))
            emit(Resource.Loading(false))

        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movies>> {
        return flow {
            emit(Resource.Loading(true))
            val movieEntity = movieDataBase.moviesDao.getListMoviesById(id)
            if (movieEntity != null) {
                emit(Resource.Success(movieEntity.toMovie(movieEntity.category)))
                emit(Resource.Loading(false))
                return@flow
            }
            emit(Resource.Error("Error such Movie"))

        }
    }
}