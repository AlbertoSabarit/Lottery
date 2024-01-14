package com.examen.lotterykotlin.data.networkstate

import java.lang.Exception

sealed class Resource() {

    data class ErrorData(val exception: Exception):  Resource()
    data class Success<T>(var data : T) : Resource()
}