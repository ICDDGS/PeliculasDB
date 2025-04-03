package com.dan.peliculasdb.ui

import androidx.recyclerview.widget.RecyclerView
import com.dan.peliculasdb.data.db.model.MovieEntity
import com.dan.peliculasdb.databinding.MovieElementBinding

class MovieViewHolder (
    private val binding: MovieElementBinding,
):RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: MovieEntity) {
        binding.tvTitle.text = movie.title
        binding.tvGenre.text = movie.genre
        binding.tvCompania.text = movie.company
        binding.tvDirector.text = movie.director
        binding.tvYear.text = movie.year.toString()
        //binding.ivIcon.setImageResource(R.drawable.terror)


    }

}