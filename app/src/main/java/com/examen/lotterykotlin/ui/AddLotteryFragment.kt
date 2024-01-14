package com.examen.lotterykotlin.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.examen.lotterykotlin.R
import com.examen.lotterykotlin.data.model.Bonoloto
import com.examen.lotterykotlin.data.model.Euromillon
import com.examen.lotterykotlin.data.model.Primitiva
import com.examen.lotterykotlin.databinding.FragmentAddLotteryBinding
import com.examen.lotterykotlin.ui.usecase.SorteoCreationState
import com.examen.lotterykotlin.ui.usecase.SorteoCreationViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

/**
 * Clase que añade un sorteo al repositorio
 */
class AddLotteryFragment : Fragment() {

    private var _binding: FragmentAddLotteryBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: SorteoCreationViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddLotteryBinding.inflate(inflater, container, false)

        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tieFecha.addTextChangedListener(ErrorsWatcher(binding.tilFecha))

        binding.fabGuardar.setOnClickListener {

            when (binding.spinnerSorteo.selectedItem.toString()) {
                "Bonoloto" -> {
                    val bonoloto = Bonoloto.create(binding.tieFecha.text.toString())
                    viewModel.validateSorteo(bonoloto)
                }

                "Euromillon" -> {
                    val euromillon = Euromillon.create(binding.tieFecha.text.toString())
                    viewModel.validateSorteo(euromillon)
                }

                "Primitiva" -> {
                    val primitiva = Primitiva.create(binding.tieFecha.text.toString())
                    viewModel.validateSorteo(primitiva)
                }
            }
        }

        viewModel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                SorteoCreationState.EmptyFecha -> setEmptyFecha()
                SorteoCreationState.FechaFormatError -> setFechaFormatError()
                SorteoCreationState.sorteoExiste -> setSorteoExiste()
                is SorteoCreationState.Loading -> onLoading(it.value)
                SorteoCreationState.Success -> onSuccess()
            }
        })
    }

    private fun setFechaFormatError() {
        binding.tilFecha.error = "Formato de fecha no válido"
        Snackbar.make(requireView(),"Formato de fecha no válido",Snackbar.LENGTH_LONG).setAction("Action", null).show()
        binding.tilFecha.requestFocus()
    }

    private fun onSuccess() {
        Toast.makeText(context, "Sorteo creado", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun onLoading(value: Boolean) {
        if (value) {
            findNavController().navigate(R.id.action_addLotteryFragment_to_fragmentProgressDialog)
        } else {
            findNavController().popBackStack()
        }
    }

    private fun setSorteoExiste() {
        binding.tilFecha.error = "Ya existe un sorteo con la misma fecha"
        Snackbar.make(requireView(), "Ya existe un sorteo con la misma fecha", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
        binding.tilFecha.requestFocus()
    }

    private fun setEmptyFecha() {
        binding.tilFecha.error = "Fecha vacía"
        Snackbar.make(requireView(),"Fecha vacía. Debes introducir una fecha",Snackbar.LENGTH_LONG).setAction("Action", null).show()
        binding.tilFecha.requestFocus()
    }

    inner class ErrorsWatcher(val til: TextInputLayout) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            til.error = null
        }

    }

}