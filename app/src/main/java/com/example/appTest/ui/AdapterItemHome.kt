package com.example.appTest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appTest.databinding.ItemHomeBinding
import com.example.appTest.entity.Item
import java.util.*
import kotlin.collections.ArrayList


class AdapterItemHome(private val onItemClick: (item: Item) -> Unit) :
    ListAdapter<Item, RecyclerView.ViewHolder>(FoodDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FoodViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClick
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val food = getItem(position)
        (holder as FoodViewHolder).bind(food)
    }

    class FoodViewHolder(
        private val binding: ItemHomeBinding,
        private val onItemClick: (item: Item) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.item?.let { item ->
                    onItemClick(item)
                }
            }
        }



        fun bind(a: Item) {
            binding.apply {
                item = a
                executePendingBindings()
            }
        }
    }

    fun getFilter(onCount: (count: Int) -> Unit): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                val foodList = ArrayList<Item>()
                if (charSearch.isEmpty()) {
                } else {
                    for (item in currentList) {
                        if (item.title.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            foodList.add(item)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = foodList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(
                    results?.values as ArrayList<Item>
                )
                val exampleList: ArrayList<Item> = results.values as ArrayList<Item>
                onCount(exampleList.count())
                notifyDataSetChanged()
            }
        }
    }

}

private class FoodDiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}
