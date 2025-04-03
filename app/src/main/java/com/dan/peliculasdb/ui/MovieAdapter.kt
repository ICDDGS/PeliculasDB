package com.dan.peliculasdb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dan.peliculasdb.data.db.model.MovieEntity
import com.dan.peliculasdb.databinding.MovieElementBinding

class MovieAdapter (
    private val onMovieClick: (MovieEntity) -> Unit,
): RecyclerView.Adapter<MovieViewHolder>() {
    private var movies : List<MovieEntity> = emptyList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding = MovieElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            onMovieClick(movie)
        }
    }

    override fun getItemCount(): Int =movies.size

    fun updateList(list: List<MovieEntity>) {
        movies = list
        notifyDataSetChanged()

    }


}