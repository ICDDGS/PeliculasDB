package com.dan.peliculasdb.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dan.peliculasdb.R
import com.dan.peliculasdb.application.PeliculasBDApp
import com.dan.peliculasdb.data.MovieRepository
import com.dan.peliculasdb.data.db.model.MovieEntity
import com.dan.peliculasdb.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var movies: MutableList<MovieEntity> = mutableListOf()
    private lateinit var repository: MovieRepository
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = (application as PeliculasBDApp).repository

        movieAdapter = MovieAdapter { selectedGame ->


            val dialog = MovieDialog(
                newMovie = false,
                movie = selectedGame,
                updateUI = {
                    updateUI()
                },
                message = { text ->
                    message(text)
                }
            )

            dialog.show(supportFragmentManager, "dialog2")


        }

        binding.apply {
            rvMovies.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMovies.adapter = movieAdapter
        }

        updateUI()
    }

    private fun updateUI() {
        lifecycleScope.launch {
            movies = repository.getAllMovies()


            binding.tvSinRegistros.visibility =
                if (movies.isNotEmpty()) View.INVISIBLE else View.VISIBLE

            movieAdapter.updateList(movies)
        }
    }

    fun click(view: View) {

        val dialog = MovieDialog(
            updateUI = {
                updateUI()
            },
            message = { text ->
                message(text)
            }
        )
        dialog.show(supportFragmentManager, "dialog1")
    }


    private fun message(text: String) {

        Snackbar.make(binding.main, text, Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.white))
            .setBackgroundTint(getColor(R.color.my_red)) //#9E1734
            .show()
    }
}