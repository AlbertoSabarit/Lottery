package com.examen.lotterykotlin.ui.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examen.lotterykotlin.data.model.Sorteo
import com.examen.lotterykotlin.data.networkstate.Resource
import com.examen.lotterykotlin.repository.SorteoRepository
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class SorteoCreationViewModel : ViewModel() {

    var fecha = MutableLiveData<String>()

    private var state = MutableLiveData<SorteoCreationState>()

    fun getState(): LiveData<SorteoCreationState> {
        return state
    }

    fun validateSorteo(sorteo: Sorteo) {

        when {
            TextUtils.isEmpty(fecha.value) -> state.value = SorteoCreationState.EmptyFecha
            !validarFecha(fecha.value!!) -> state.value = SorteoCreationState.FechaFormatError

            else ->{

                viewModelScope.launch {

                    state.value = SorteoCreationState.Loading(true)
                    val result = SorteoRepository.crearSorteo(sorteo)
                    state.value = SorteoCreationState.Loading(false)

                    when (result) {

                        is Resource.ErrorData -> state.value = SorteoCreationState.sorteoExiste
                        is Resource.Success<*> -> state.value = SorteoCreationState.Success

                    }
                }
            }
        }
    }
    fun validarFecha(fechaString: String): Boolean {
        try {
            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            formato.isLenient = false
            formato.parse(fechaString)
            return true
        } catch (e: ParseException) {
            return false
        }
    }

}