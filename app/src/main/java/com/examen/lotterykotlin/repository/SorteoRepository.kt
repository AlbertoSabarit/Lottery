package com.examen.lotterykotlin.repository


import com.examen.lotterykotlin.data.model.Sorteo
import com.examen.lotterykotlin.data.networkstate.Resource
import com.examen.lotterykotlin.data.networkstate.ResourceList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception

class SorteoRepository private constructor() {

    companion object {

        var dataset = mutableListOf<Sorteo>()

        init {
            initSorteoRepository()
        }

        fun initSorteoRepository() {
            /* dataset.add(Bonoloto("Bonoloto", "28/12/2023", "4, 23, 25, 30, 36, 47"))
               dataset.add(Euromillon("Euromillon", "25/12/2023", "5, 12,34, 25, 48", 1, "8", "10"))
               dataset.add(Primitiva("Primitiva", "14/01/2018", "4, 23, 25, 30, 36, 41", 1, "8", "3"))*/

        }

        suspend fun getSorteoList(): ResourceList {
            return withContext(Dispatchers.IO) {
                delay(1000)
                when {
                    dataset.isEmpty() -> ResourceList.NoData(Exception("no hay datos"))
                    else -> ResourceList.Success(dataset as ArrayList<Sorteo>)
                }
            }
        }

        suspend fun crearSorteo(sorteo: Sorteo): Resource {
            return withContext(Dispatchers.IO) {
                delay(1000)
                if (dataset.any { it.fecha == sorteo.fecha && it.tipoSorteo == sorteo.tipoSorteo}) {
                    Resource.ErrorData(Exception("Error"))
                } else {
                    dataset.add(sorteo)
                    Resource.Success(sorteo)
                }
            }
        }

        fun borrarSorteo(sorteo: Sorteo) {
            dataset.remove(sorteo)
        }


    }
}