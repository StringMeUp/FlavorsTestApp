package com.string.me.up.eqandroidtestapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.string.me.up.eqandroidtestapp.R
import com.string.me.up.eqandroidtestapp.databinding.ItemCardBinding
import com.string.me.up.eqandroidtestapp.model.ItemDetails
import java.util.*
import kotlin.collections.ArrayList

class MainAdapter(private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>(), Filterable {

    var itemList = ArrayList<ItemDetails>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    var defaultItemList = ArrayList<ItemDetails>()

    fun initFilteredList(itemsListCopy: ArrayList<ItemDetails>) {
        defaultItemList.clear()
        defaultItemList.addAll(itemsListCopy)
        notifyDataSetChanged()
    }

    class MainViewHolder(
        private val binding: ItemCardBinding,
        private val itemClickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemDetails) {
            binding.run {
                setItemImage(item)
                itemTitle.text = item.name
                itemImage.setOnClickListener {
                    itemClickListener.onItemClicked(item)
                }
            }
        }

        private fun setItemImage(item: ItemDetails) {
            Glide.with(binding.itemImage)
                .load(item.image)
                .apply(RequestOptions().override(500, 500))
                .into(binding.itemImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = DataBindingUtil.inflate<ItemCardBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_card, parent, false
        )
        return MainViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<ItemDetails>()
                if (constraint.isNullOrEmpty()) {
                    filteredList.addAll(defaultItemList)
                } else {
                    val filterPattern =
                        constraint.toString().toLowerCase(Locale.getDefault()).trim()
                    defaultItemList.forEach {
                        if (it.type.toLowerCase(Locale.getDefault()) == filterPattern ||
                            it.name.toLowerCase(Locale.getDefault()).contains(filterPattern)
                        )
                            filteredList.add(it)
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemList.clear()
                itemList.addAll(results?.values as ArrayList<out ItemDetails>)
                notifyDataSetChanged()
            }
        }
    }
}

interface OnItemClickListener {
    fun onItemClicked(itemDetails: ItemDetails)
}