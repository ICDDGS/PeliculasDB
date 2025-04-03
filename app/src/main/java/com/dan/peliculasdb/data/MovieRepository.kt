package com.dan.peliculasdb.data

import com.dan.peliculasdb.data.db.MovieDAO
import com.dan.peliculasdb.data.db.model.MovieEntity

class MovieRepository(
    private val movieDao: MovieDAO
) {
    suspend fun insertMovie(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }
    suspend fun getAllMovies(): MutableList<MovieEntity> {
        return movieDao.getAllMovies()
    }

    suspend fun updateMovie(movie: MovieEntity) {
        movieDao.updateMovie(movie)
    }
    suspend fun deleteMovie(movie: MovieEntity) {
        movieDao.deleteMovie(movie)
    }
}