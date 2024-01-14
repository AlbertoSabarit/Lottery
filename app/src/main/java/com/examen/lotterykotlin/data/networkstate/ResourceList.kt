package com.examen.lotterykotlin.data.networkstate

import java.lang.Exception

sealed class ResourceList() {

    data class NoData(val exception: Exception):  ResourceList()
    data class Success<T>(var data : ArrayList<T>) : ResourceList()
}