package com.dan.peliculasdb.ui

import androidx.recyclerview.widget.RecyclerView
import com.dan.peliculasdb.R
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
        when (movie.genre.lowercase()) {
            "terror" -> binding.ivIcon.setImageResource(R.drawable.terror)
            "romance" -> binding.ivIcon.setImageResource(R.drawable.amor)
            "acción" -> binding.ivIcon.setImageResource(R.drawable.accion)
            "ciencia ficcion" -> binding.ivIcon.setImageResource(R.drawable.ciencia_ficcion)
            "animada" -> binding.ivIcon.setImageResource(R.drawable.animada)
            "comedia" -> binding.ivIcon.setImageResource(R.drawable.comedia)
            else -> binding.ivIcon.setImageResource(R.drawable.ic_movie) // ícono genérico opcional
        }



    }

}