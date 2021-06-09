package com.example.locatecityshops.recycler_view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locatecityshops.R
import com.example.locatecityshops.databinding.GenericRecyclerViewBinding
import com.example.locatecityshops.repository.CitiesRepository
import com.example.locatecityshops.viewmodel.CitiesViewModel
import com.example.locatecityshops.viewmodel.CitiesViewModelFactory

abstract class DisplayLocationListFragment: Fragment(), ItemClickListener {
    private lateinit var recyclerRecyclerRowArray: Array<RecyclerRow>
    private lateinit var binding: GenericRecyclerViewBinding
    private lateinit var adapterDisplay: DisplayLocationAdapter
    private lateinit var viewModel: CitiesViewModel
    private lateinit var repository: CitiesRepository
    private lateinit var viewModelFactory: CitiesViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = GenericRecyclerViewBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        repository = CitiesRepository()
        viewModelFactory = CitiesViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(CitiesViewModel::class.java)

        binding.textInputLayout.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.toString()?.let { filterList(it) }
            }
        })

        binding.allShopsInCity.setOnClickListener {
            viewModel.setButtonClickFlag(true)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
    }

    fun submitList(list: Array<RecyclerRow>) {
        this.recyclerRecyclerRowArray = list
        adapterDisplay.submitList(list.toMutableList())
    }

    fun setViewAllShopsButton(flag: Boolean) {
        binding.allShopsInCity.visibility = if (flag) View.VISIBLE
        else View.GONE
    }

    fun setSearchInstructionEntity(entity: String, cityName: String? = null) {
        binding.textInputLayout.hint = String.format(resources.getString(R.string.search_instruction), entity)
        if (!cityName.isNullOrBlank()) binding.allShopsInCity.text = String.format(resources.getString(R.string.view_all_shops_in_city), cityName)
    }

    private fun setUpAdapter() {
        adapterDisplay = DisplayLocationAdapter(this)
        binding.recyclerView.adapter = adapterDisplay
    }

    private fun filterList(text: String?) {
        if (text.isNullOrBlank()) {
            submitFilteredList(recyclerRecyclerRowArray.toList())
            return
        }
        val filteredList = recyclerRecyclerRowArray.filter {
            it.name.contains(text, true)
        }
        submitFilteredList(filteredList)
    }

    private fun submitFilteredList(filteredList: List<RecyclerRow>) {
        adapterDisplay.submitList(filteredList.toMutableList())
    }
}