package com.examen.lotterykotlin.data.model

open class Sorteo(
    val fecha: String = "",
    val tipoSorteo : TipoSorteo
) : Comparable<Sorteo> {

    override fun compareTo(other: Sorteo): Int {
        return fecha.compareTo(other.fecha)
    }
}
