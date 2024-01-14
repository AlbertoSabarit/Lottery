package com.examen.lotterykotlin.ui

import android.R
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * Clase que añade un sorteo al repositorio
 */
class BaseFragmentDialog() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = requireArguments().getString(title)
        val message = requireArguments().getString(message)

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(
            R.string.ok
        ) { _, _ -> //Una de las claves para realizar la comunicación entre fragmentos (padre-hijo) es utilizar los métodos
            // supportFragmentManager de la actividad para realizar el intercambio de información.
            val bundle = Bundle()
            bundle.putBoolean(result, true)
            requireActivity().supportFragmentManager.setFragmentResult(request, bundle)
        }

        builder.setNegativeButton(
            R.string.cancel
        )
        { _, _ -> dismiss() }
        return builder.create()
    }

    companion object {
        const val title = "title"
        const val message = "message"
        const val request = "request"
        const val result = "result"

        // Método de fábrica para crear una instancia de BaseFragmentDialog con argumentos
        fun newInstance(title: String, message: String): BaseFragmentDialog {
            val fragment = BaseFragmentDialog()
            val args = Bundle()
            args.putString(BaseFragmentDialog.title, title)
            args.putString(BaseFragmentDialog.message, message)
            fragment.arguments = args
            return fragment
        }
    }
}