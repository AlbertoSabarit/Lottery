package com.examen.lotterykotlin.ui.usecase

import com.examen.lotterykotlin.data.model.Sorteo

sealed class SorteoListState() {

    data object NoDataList : SorteoListState()
    data class Loading(val value : Boolean) : SorteoListState()
    data class Success(var dataset : ArrayList<Sorteo>) : SorteoListState()
}