package com.godaMeal.meals.menustags.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.godaMeal.meals.menustags.data.uiModels.ItemOfTags

class ItemsOfTagsAdapter(
    private val onItemSelected: (ItemOfTags) -> Unit
) : ListAdapter<ItemOfTags, RecyclerView.ViewHolder>(
    GdiffCallBack()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= run { ItemsTagsViewHolder.create(parent) }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tagItem = currentList[holder.absoluteAdapterPosition]
        (holder as ItemsTagsViewHolder).bind(tagItem)
        holder.itemView.setOnClickListener {
            onItemSelected(tagItem)
        }
    }


}
class GdiffCallBack : DiffUtil.ItemCallback<ItemOfTags>() {
    override fun areItemsTheSame(
        oldItem: ItemOfTags,
        newItem: ItemOfTags
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ItemOfTags,
        newItem: ItemOfTags
    ): Boolean {
        return oldItem.name == newItem.name&&oldItem.description == newItem.description
    }
}