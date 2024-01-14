package com.examen.lotterykotlin.ui.usecase


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examen.lotterykotlin.data.model.Sorteo
import com.examen.lotterykotlin.data.networkstate.ResourceList
import com.examen.lotterykotlin.repository.SorteoRepository
import kotlinx.coroutines.launch
import java.util.ArrayList

/**
 * Clase  ViewModel
 */
class ListLotteryViewModel : ViewModel() {

    private var state = MutableLiveData<SorteoListState>()

    fun getState(): LiveData<SorteoListState> {
        return state
    }

    fun getSorteoList() {

        viewModelScope.launch {

            state.value = SorteoListState.Loading(true)
            val result = SorteoRepository.getSorteoList()
            state.value = SorteoListState.Loading(false)

            when(result){

                is ResourceList.NoData -> state.value = SorteoListState.NoDataList
                is ResourceList.Success<*> -> {
                    (result.data as ArrayList<Sorteo>).sort()
                    state.value = SorteoListState.Success(result.data as ArrayList<Sorteo>)
                }

            }
        }
    }

    fun borrarSorteo(sorteo: Sorteo) {
        SorteoRepository.borrarSorteo(sorteo)
    }
}