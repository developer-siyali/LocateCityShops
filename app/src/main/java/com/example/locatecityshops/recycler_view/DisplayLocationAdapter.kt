package com.example.locatecityshops.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.locatecityshops.R
import com.example.locatecityshops.databinding.RowItemBinding
import kotlinx.android.synthetic.main.row_item.view.*


class DisplayLocationAdapter(private val callback: ItemClickListener): ListAdapter<RecyclerRow, DisplayLocationAdapter.LocationViewHolder>(ItemDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val rowBinding = RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(rowBinding.root)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        getItem(position).let { rowItem ->
            holder.itemView.apply {
                name.text = rowItem.name
                search_results.text = String.format(resources.getString(R.string.number_results_found), "${rowItem.numberOfResults ?: ""} ${rowItem.entityType ?: ""}")
                setOnClickListener {
                    callback.onItemClicked(position)
                }
            }
        }
    }

    class LocationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    class ItemDiffCallback: DiffUtil.ItemCallback<RecyclerRow>() {

        override fun areContentsTheSame(oldItem: RecyclerRow, newItem: RecyclerRow): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areItemsTheSame(oldItem: RecyclerRow, newItem: RecyclerRow): Boolean {
            return oldItem.numberOfResults == newItem.numberOfResults
        }
    }
}