package com.examen.lotterykotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.examen.lotterykotlin.R
import com.examen.lotterykotlin.data.model.Bonoloto
import com.examen.lotterykotlin.data.model.Euromillon
import com.examen.lotterykotlin.data.model.Primitiva
import com.examen.lotterykotlin.data.model.Sorteo
import com.examen.lotterykotlin.databinding.ItemSorteoBinding

class SorteoAdapter(val listener: OnClickSorteo) :
    RecyclerView.Adapter<SorteoAdapter.SorteoViewHolder>() {

    var dataset = arrayListOf<Sorteo>()

    interface OnClickSorteo {
        fun onClickDelete(sorteo: Sorteo): Boolean
    }

    inner class SorteoViewHolder(val binding: ItemSorteoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sorteo: Sorteo) {

            when (sorteo) {
                is Bonoloto -> {
                    with(binding) {
                        tvCodigoSorteo.text = sorteo.codigoSorteoBonoloto.toString()
                        tvFechaSorteo.text = sorteo.fecha
                        tvNumerosSorteo.text = sorteo.combinacion
                        tvComplementario.text = sorteo.complementario
                        tvReintegro.text = sorteo.reintegro
                        imgSorteo.setImageResource(R.drawable.ic_bonoloto)

                    }
                }

                is Euromillon -> {
                    with(binding) {
                        tvCodigoSorteo.text = sorteo.codigoSorteoEuromillon.toString()
                        tvFechaSorteo.text = sorteo.fecha
                        tvNumerosSorteo.text = sorteo.combinacion
                        tvComplementario.text = sorteo.estrella1
                        tvReintegro.text = sorteo.estrella2
                        imgSorteo.setImageResource(R.drawable.ic_euromillon)
                    }
                }

                is Primitiva -> {
                    with(binding) {
                        tvCodigoSorteo.text = sorteo.codigoSorteoPrimitiva.toString()
                        tvFechaSorteo.text = sorteo.fecha
                        tvNumerosSorteo.text = sorteo.combinacion
                        tvComplementario.text = sorteo.complementario
                        tvReintegro.text = sorteo.reintegro
                        imgSorteo.setImageResource(R.drawable.ic_primitiva)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SorteoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SorteoViewHolder(ItemSorteoBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: SorteoViewHolder, position: Int) {
        var sorteo = dataset[position]
        holder.bind(sorteo)

        holder.binding.root.setOnLongClickListener {
            listener.onClickDelete(sorteo)
            true
        }
    }

    fun update(newDataSet: ArrayList<Sorteo>) {
        dataset = newDataSet
        notifyDataSetChanged()
    }

    fun ordenar() {
        dataset.sortBy { it.tipoSorteo }
        notifyDataSetChanged()
    }
}