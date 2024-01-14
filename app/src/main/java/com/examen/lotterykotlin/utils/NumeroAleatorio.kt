package com.examen.lotterykotlin.utils

class NumeroAleatorio {

    companion  object {
        private lateinit var lista: ArrayList<Int>

        /**
         * Método que genera la lista de Números Aleatorios
         */
        fun getListaNumerosAleatorio(
            valorInicial: Int,
            valorFinal: Int,
            cantidad: Int
        ): ArrayList<Int>? {
            lista = ArrayList()
            for (i in 0 until cantidad) {
                generaNumeroAleatorio(valorInicial, valorFinal)
            }
            return lista
        }

        /**
         * Método que crea un número aleatorio en el rango establecido
         */
        fun numeroAleatorio(valorInicial: Int, valorFinal: Int): Int {
            return (Math.random() * (valorFinal - valorInicial + 1) + valorInicial).toInt()
        }

        private fun generaNumeroAleatorio(valorInicial: Int, valorFinal: Int): Int {
            if (lista!!.size < valorFinal - valorInicial + 1) {
                //Aun no se han generado todos los numeros
                val numero = numeroAleatorio(valorInicial, valorFinal) //genero un numero
                return if (lista!!.isEmpty()) { //si la lista esta vacia
                    lista!!.add(numero)
                    numero
                } else { //si no esta vacia
                    if (lista!!.contains(numero)) { //Si el numero que generé esta contenido en la lista
                        generaNumeroAleatorio(
                            valorInicial,
                            valorFinal
                        ) //recursivamente lo mando a generar otra vez
                    } else { //Si no esta contenido en la lista
                        lista!!.add(numero)
                        numero
                    }
                }
            }
            return -1 //Ya se ha generado todos los números
        }
    }
}