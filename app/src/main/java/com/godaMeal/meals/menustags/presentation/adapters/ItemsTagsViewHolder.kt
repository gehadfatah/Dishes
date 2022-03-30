package com.godaMeal.meals.menustags.presentation.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.godaMeal.meals.R
import com.godaMeal.meals.menustags.data.uiModels.ItemOfTags
import com.godaMeal.meals.databinding.ItemTagsItemBinding

class ItemsTagsViewHolder(val binding: ItemTagsItemBinding) : RecyclerView.ViewHolder(binding?.root) {

    private var tag: ItemOfTags? = null


    fun bind(tag: ItemOfTags?) {
        if (tag != null) {
            showRepoData(tag)
        }
    }

    private fun showRepoData(tag: ItemOfTags) {
        this.tag = tag
        with(binding) {
            tvName.text = tag.name
            tagImage.setImageURI(tag.photoUrl)
            mealImage.setImageURI(tag.photoUrl).apply { 

            }


        }

    }

    companion object {
        fun create(parent: ViewGroup): ItemsTagsViewHolder =

                ItemsTagsViewHolder(
                    ItemTagsItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )



    }
}
