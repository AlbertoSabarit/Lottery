package com.examen.lotterykotlin.data.model

import com.examen.lotterykotlin.utils.NumeroAleatorio

class Bonoloto(
    fecha: String = "",
    val combinacion: String = "",
    val complementario: String = "",
    val reintegro: String = ""
) : Sorteo(fecha, TipoSorteo.BONOLOTO) {

    var codigoSorteoBonoloto: Int = 0

    init {
        codigoSorteoBonoloto++
    }

    companion object {

        private var codigoSorteoBonolotoTotal: Int = 0

        fun create(fecha: String): Bonoloto {

            val arrayNumeros = NumeroAleatorio.getListaNumerosAleatorio(1, 49, 6) ?: emptyList()
            val numeroLoteria = arrayNumeros.joinToString(", ")

            val complementario =
                NumeroAleatorio.getListaNumerosAleatorio(1, 49, 1)?.get(0).toString()
            val reintegro = NumeroAleatorio.getListaNumerosAleatorio(0, 9, 1)?.get(0).toString()

            val bonoloto = Bonoloto(fecha, numeroLoteria, complementario, reintegro)
            bonoloto.codigoSorteoBonoloto = ++codigoSorteoBonolotoTotal

            return bonoloto
        }
    }
}
