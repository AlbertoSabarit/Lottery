package com.examen.lotterykotlin.data.model

import com.examen.lotterykotlin.utils.NumeroAleatorio

class Primitiva(
    fecha: String = "",
    val combinacion: String = "",
    val complementario: String = "",
    val reintegro: String = ""
) : Sorteo(fecha, TipoSorteo.PRIMITIVA) {

    var codigoSorteoPrimitiva: Int = 0

    init {
        codigoSorteoPrimitiva++
    }

    companion object {
        private var codigoSorteoPrimitivaTotal: Int = 0

        fun create(fecha: String): Primitiva {
            val arrayNumeros = NumeroAleatorio.getListaNumerosAleatorio(1, 49, 6) ?: emptyList()
            val numeroLoteria = arrayNumeros.joinToString(", ")

            val complementario =
                NumeroAleatorio.getListaNumerosAleatorio(1, 49, 1)?.get(0).toString()
            val reintegro = NumeroAleatorio.getListaNumerosAleatorio(0, 9, 1)?.get(0).toString()

            val primitiva = Primitiva(fecha, numeroLoteria, complementario, reintegro)
            primitiva.codigoSorteoPrimitiva = ++codigoSorteoPrimitivaTotal

            return primitiva
        }
    }
}
