package com.examen.lotterykotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.examen.lotterykotlin.R
import com.examen.lotterykotlin.adapter.SorteoAdapter
import com.examen.lotterykotlin.data.model.Sorteo
import com.examen.lotterykotlin.databinding.FragmentListLotteryBinding
import com.examen.lotterykotlin.ui.usecase.ListLotteryViewModel
import com.examen.lotterykotlin.ui.usecase.SorteoListState
import com.google.android.material.snackbar.Snackbar

class ListLotteryFragment : Fragment(), MenuProvider, SorteoAdapter.OnClickSorteo {

    private var _binding: FragmentListLotteryBinding? = null
    private val binding get() = _binding!!


    private val viewmodel: ListLotteryViewModel by viewModels()

    private lateinit var sorteoAdapter: SorteoAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListLotteryBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuhost: MenuHost = requireActivity()
        menuhost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_listLotteryFragment_to_addLotteryFragment)
        }

        setUpRecyclerView()

        viewmodel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                SorteoListState.NoDataList -> setNoDataList()
                is SorteoListState.Loading -> onLoading(it.value)
                is SorteoListState.Success -> onSuccess(it.dataset)
            }
        })
    }


    private fun setUpToolbar() {

        (requireActivity() as? MainActivity)?.toolbar?.apply {
            visibility = View.VISIBLE
        }

        val menuhost: MenuHost = requireActivity()
        menuhost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun onSuccess(dataset: ArrayList<Sorteo>) {
        binding.rvLottery.visibility = View.VISIBLE
        binding.animationView.visibility = View.GONE

        sorteoAdapter.update(dataset)
    }

    private fun onLoading(value: Boolean) {
        if (value) {
            findNavController().navigate(R.id.action_listLotteryFragment_to_fragmentProgressDialog)
        } else (
                findNavController().popBackStack()
                )
    }

    private fun setNoDataList() {
        binding.rvLottery.visibility = View.GONE
        binding.animationView.visibility = View.VISIBLE
    }


    fun setUpRecyclerView() {
        sorteoAdapter = SorteoAdapter(this)

        with(binding.rvLottery) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)

            adapter = sorteoAdapter
        }
    }


    /**
     * Método que añade las opciones del menú definidas en R.menu.menu_list_user al menú
     * principal
     */
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_list_lottery, menu)
    }


    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_refresh -> {
                Snackbar.make(requireView(),"Lista ordenada por fecha", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                viewmodel.getSorteoList()
                true
            }

            R.id.action_sort -> {
                Snackbar.make(requireView(),"Lista ordenada por sorteo",Snackbar.LENGTH_LONG).setAction("Action", null).show()
                sorteoAdapter.ordenar()
                true
            }

            else -> false
        }
    }


    companion object {
        private const val TAG = "ListLotteryFragment"
    }


    override fun onClickDelete(sorteo: Sorteo): Boolean {

        val dialog = BaseFragmentDialog.newInstance("Cuidado", "¿Borrar sorteo?")

        dialog.show((context as AppCompatActivity).supportFragmentManager, TAG)
        dialog.parentFragmentManager.setFragmentResultListener(
            BaseFragmentDialog.request,
            viewLifecycleOwner
        ) { _, bundle ->
            val resultado = bundle.getBoolean(BaseFragmentDialog.result)
            if (resultado) {
                viewmodel.borrarSorteo(sorteo)
                viewmodel.getSorteoList()

                Toast.makeText(requireActivity(), "Sorteo borrado", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        viewmodel.getSorteoList()
    }

    override fun onResume() {
        super.onResume()
        setUpToolbar()
    }
}