package com.godaMeal.meals.menustags.presentation.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.godaMeal.meals.R
import com.godaMeal.meals.menustags.data.uiModels.TagDishe
import com.facebook.drawee.view.SimpleDraweeView
import com.godaMeal.meals.databinding.ItemTagBinding
import com.godaMeal.meals.databinding.ItemTagsItemBinding
import kotlinx.android.synthetic.main.item_tag.view.*


class TagsViewHolder(val binding: ItemTagBinding) : RecyclerView.ViewHolder(binding.root) {
    private var tag: TagDishe? = null

    fun bind(tag: TagDishe?) {
        if (tag != null) {
            showRepoData(tag)
        }
    }

    private fun showRepoData(tag: TagDishe) {
        this.tag = tag
        with(binding) {

            tvName.text = tag.tagName
            tagImage.setImageURI(tag.photoURL)
        }
    }

    companion object {
        fun create(parent: ViewGroup): TagsViewHolder =

            TagsViewHolder(
                ItemTagBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

    }
}
