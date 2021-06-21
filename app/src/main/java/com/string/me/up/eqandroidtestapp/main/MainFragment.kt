package com.string.me.up.eqandroidtestapp.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isNotEmpty
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.string.me.up.eqandroidtestapp.R
import com.string.me.up.eqandroidtestapp.adapters.MainAdapter
import com.string.me.up.eqandroidtestapp.adapters.OnItemClickListener
import com.string.me.up.eqandroidtestapp.databinding.FragmentMainBinding
import com.string.me.up.eqandroidtestapp.model.ItemDetails
import com.string.me.up.eqandroidtestapp.util.Constants

class MainFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main,
            container, false
        )
        mainViewModel = ViewModelProvider(this@MainFragment)[MainViewModel::class.java]
        setLifecycle(binding, mainViewModel)
        lifecycle.addObserver(mainViewModel)
        mainViewModel.onLifeCyclePause()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getItems()
        mainAdapter = MainAdapter(this@MainFragment)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        initRecyclerView(mainAdapter, layoutManager)
        initObservers()

        binding.mainRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val manager = binding.mainRecyclerView.layoutManager as GridLayoutManager
                    val visibleItemCount = manager.findLastCompletelyVisibleItemPosition() + 1
                    if (visibleItemCount == manager.itemCount) {
                        mainViewModel.getItems()
                    }
                }
            }
        })
    }

    override fun onItemClicked(itemDetails: ItemDetails) {
        val itemBundle = bundleOf(Constants.single_item to itemDetails)
        findNavController().navigate(
            R.id.action_mainFragment_to_detailsFragment,
            itemBundle
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (searchView.isNotEmpty())
                    mainAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun setLifecycle(
        binding: FragmentMainBinding,
        mainViewModel: MainViewModel
    ) {
        binding.run {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = mainViewModel
        }
    }

    private fun initRecyclerView(
        mAdapter: MainAdapter,
        gridLayoutManager: GridLayoutManager
    ) {
        binding.mainRecyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = mAdapter
        }
    }

    private fun initObservers() {
        mainViewModel.appData.observe(viewLifecycleOwner, { appData ->
            appData?.let {
                mainAdapter.itemList = it.items
                mainAdapter.initFilteredList(it.items)
            }
        })

        mainViewModel.errorResponse.observe(viewLifecycleOwner, {
            it?.let {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.unexpected_err_title))
                    .setMessage(getString(R.string.unexpected_err_message))
                    .setCancelable(true)
                    .setIcon(R.drawable.bomb)
                    .show().create()
            }
        })

        mainViewModel.endOfPaginationReached.observe(viewLifecycleOwner, { hasReachedEnd ->
            hasReachedEnd?.let {
                if (it) {
                    Toast.makeText(
                        requireContext(),
                        R.string.end_of_pagination_reached,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}