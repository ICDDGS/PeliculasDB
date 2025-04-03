package com.dan.peliculasdb.application

import android.app.Application
import com.dan.peliculasdb.data.db.MovieDatabase
import com.dan.peliculasdb.data.MovieRepository

class PeliculasBDApp: Application() {
    private val database by lazy {
        MovieDatabase.getDatabase(this@PeliculasBDApp)
    }

    val repository by lazy {
        MovieRepository(database.movieDao())
    }
}