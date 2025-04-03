package com.dan.peliculasdb.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dan.peliculasdb.data.db.model.MovieEntity
import com.dan.peliculasdb.util.Constants

@Dao
interface MovieDAO {
    @Insert
    suspend fun insertMovie(movie: MovieEntity)
    @Insert
    suspend fun insertMovie(movies: MutableList<MovieEntity>)
    @Query("SELECT * FROM ${Constants.DATABASE_MOVIE_TABLE}")
    suspend fun getAllMovies(): MutableList<MovieEntity>
    @Query("SELECT * FROM ${Constants.DATABASE_MOVIE_TABLE} WHERE movie_id = :movieId")
    suspend fun getMovieById(movieId: Long): MovieEntity?
    @Update
    suspend fun updateMovie(movie: MovieEntity)
    @Update
    suspend fun updateMovie(movies: MutableList<MovieEntity>)
    @Delete
    suspend fun deleteMovie(movie: MovieEntity)
    @Delete
    suspend fun deleteMovie(movies: MutableList<MovieEntity>)

}




