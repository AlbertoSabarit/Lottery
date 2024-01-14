package com.examen.lotterykotlin.ui.usecase


sealed class SorteoCreationState(){

    data object EmptyFecha : SorteoCreationState()
    data object FechaFormatError : SorteoCreationState()
    data object sorteoExiste : SorteoCreationState()
    data class Loading(val value : Boolean): SorteoCreationState()
    data object Success : SorteoCreationState()
}
