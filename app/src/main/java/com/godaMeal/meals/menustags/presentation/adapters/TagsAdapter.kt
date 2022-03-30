package  com.godaMeal.meals.menustags.presentation.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.godaMeal.meals.menustags.data.uiModels.TagDishe


class TagsAdapter() :
    PagingDataAdapter<TagDishe, RecyclerView.ViewHolder>(
        FeedWallDiffCallBack()
    ) {
    var index = -1
    lateinit var onClick: OnClick


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TagsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tagItem = getItem(position)
        if (tagItem != null) {
            (holder as TagsViewHolder).bind(tagItem)
            if (index == -1) {
                onClick(tagItem.tagName)
                index = position
            }
            holder.itemView.setOnClickListener {
                onClick(tagItem.tagName)

                index = position

                notifyDataSetChanged()
            }
            holder.itemView.isSelected = index == position
        }
    }


}

class FeedWallDiffCallBack : DiffUtil.ItemCallback<TagDishe>() {
    override fun areItemsTheSame(
        oldItem: TagDishe,
        newItem: TagDishe
    ): Boolean {
        return oldItem.tagName == newItem.tagName

    }

    override fun areContentsTheSame(
        oldItem: TagDishe,
        newItem: TagDishe
    ): Boolean {
        return oldItem == newItem

    }
}
typealias OnClick = (String) -> Unit
