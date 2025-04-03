package com.dan.peliculasdb.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.dan.peliculasdb.R
import com.dan.peliculasdb.application.PeliculasBDApp
import com.dan.peliculasdb.data.MovieRepository
import com.dan.peliculasdb.data.db.model.MovieEntity
import com.dan.peliculasdb.databinding.MovieDialogBinding
import android.widget.EditText
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException



class MovieDialog(
    private val newMovie: Boolean = true,
    private var movie: MovieEntity = MovieEntity(
        title = "",
        genre = "",
        company = "",
        director = "",
        year = "",
    ),
    private val updateUI: () -> Unit,
    private val message: (String) -> Unit,

): DialogFragment() {
    private var _binding: MovieDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialog: Dialog

    private var saveButton: Button? = null

    private lateinit var repository: MovieRepository

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        _binding = MovieDialogBinding.inflate(layoutInflater)

        binding.apply {
            tietTitle.setText(movie.title)
            tietGenre.setText(movie.genre)
            tietDirector.setText(movie.director)
            tietYear.setText(movie.year)
            tietCompaniar.setText(movie.company)


        }

        dialog = if (newMovie)
            buildDialog("Guardar", "Cancelar", {
                //Guardar
                binding.apply {
                    movie.title = tietTitle.text.toString()
                    movie.genre = tietGenre.text.toString()
                    movie.director = tietDirector.text.toString()
                    movie.year = tietYear.text.toString()
                    movie.company = tietCompaniar.text.toString()
                }


                try {

                    lifecycleScope.launch {

                        val result = async {
                            repository.insertMovie(movie)
                        }

                        result.await()

                        message("Pelicula guardada exitosamente")

                        updateUI()

                    }

                } catch (e: IOException) {
                    //Manejamos la excepción
                    e.printStackTrace()

                    message("Error al guardar la pelicula")

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }, {
                //Cancelar

            })
        else
            buildDialog(getString(R.string.update_button), getString(R.string.delete_button), {
                //Actualizar
                binding.apply {
                    movie.title = tietTitle.text.toString()
                    movie.genre = tietGenre.text.toString()
                    movie.director = tietDirector.text.toString()
                    movie.year = tietYear.text.toString()
                    movie.company = tietCompaniar.text.toString()
                }


                try {

                    lifecycleScope.launch {

                        val result = async {
                            repository.updateMovie(movie)
                        }

                        result.await()

                        message("Pelicula actualizada exitosamente")


                        updateUI()

                    }

                } catch (e: IOException) {
                    //Manejamos la excepción
                    e.printStackTrace()
                    message("Error al actualizar la pelicula")

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }, {
                //Eliminar

                //Guardamos el contexto antes de la corrutina para que no se pierda
                val context = requireContext()

                //Diálogo para confirmar
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.confirmation))
                    .setMessage(getString(R.string.delete_confirmation, movie.title))
                    .setPositiveButton(getString(R.string.ok)) { _, _ ->
                        try {
                            lifecycleScope.launch {

                                val result = async {
                                    repository.deleteMovie(movie)
                                }

                                result.await()

                                message(context.getString(R.string.game_deleted))

                                updateUI()

                            }

                        } catch (e: IOException) {
                            //Manejamos la excepción
                            e.printStackTrace()

                            message("Error al eliminar la pelicula")

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    .setNegativeButton("Cancelar") { dialogInterface, _ ->
                        dialogInterface.dismiss()
                    }
                    .create()
                    .show()


            })


        return dialog

    }
    override fun onStart() {
        super.onStart()

        val generos = listOf("Romance", "Terror", "Acción", "Ciencia Ficción","Animada", "Comedia","Otro")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, generos)
        binding.tietGenre.setAdapter(adapter)
        binding.tietGenre.setOnClickListener {
            binding.tietGenre.showDropDown()
        }



        //Instanciamos el repositorio
        repository = (requireContext().applicationContext as PeliculasBDApp).repository

        //Referenciamos el botón "Guardar" del diálgo
        saveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        saveButton?.isEnabled = false

        binding.apply {
            setupTextWatcher(
                tietTitle,
                tietGenre,
                tietCompaniar,
                tietDirector,
                tietYear
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun validateFields(): Boolean =
        binding.tietTitle.text.toString().isNotEmpty()
                && binding.tietGenre.text.toString().isNotEmpty()
                && binding.tietCompaniar.text.toString().isNotEmpty()
                && binding.tietDirector.text.toString().isNotEmpty()
                && binding.tietYear.text.toString().isNotEmpty()

    private fun setupTextWatcher(vararg textFields: EditText)
    {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                saveButton?.isEnabled = validateFields()
            }
        }

        textFields.forEach { it.addTextChangedListener(textWatcher) }
    }
    private fun buildDialog(
        btn1Text: String,
        btn2Text: String,
        positiveButton: () -> Unit,
        negativeButton: () -> Unit,
    ): Dialog =
        AlertDialog.Builder(requireContext()).setView(binding.root)
            .setTitle("Movie")
            .setPositiveButton(btn1Text) { _, _ ->
                //Click para el botón positivo
                positiveButton()
            }
            .setNegativeButton(btn2Text) { _, _ ->
                //Click para el botón negativo
                negativeButton()
            }
            .create()

}