package com.examen.lotterykotlin.data.model

import com.examen.lotterykotlin.utils.NumeroAleatorio

class Euromillon(
    fecha: String = "",
    val combinacion: String = "",
    val estrella1: String = "",
    val estrella2: String = ""
) : Sorteo(fecha, TipoSorteo.EUROMILLON) {

    var codigoSorteoEuromillon: Int = 0

    init {
        codigoSorteoEuromillon++
    }

    companion object {

        private var codigoSorteoEuromillonTotal: Int = 0

        fun create(fecha: String): Euromillon {

            val arrayNumeros = NumeroAleatorio.getListaNumerosAleatorio(1, 49, 6) ?: emptyList()
            val numeroLoteria = arrayNumeros.joinToString(", ")

            val estrella1 =
                NumeroAleatorio.getListaNumerosAleatorio(0, 9, 1)?.get(0).toString()
            val estrella2 = NumeroAleatorio.getListaNumerosAleatorio(0, 9, 1)?.get(0).toString()

            val euromillon = Euromillon(fecha, numeroLoteria, estrella1, estrella2)
            euromillon.codigoSorteoEuromillon = ++codigoSorteoEuromillonTotal

            return euromillon
        }
    }

}
