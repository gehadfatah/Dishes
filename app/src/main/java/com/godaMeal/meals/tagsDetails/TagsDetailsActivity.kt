package com.godaMeal.meals.tagsDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.godaMeal.meals.databinding.ActivityTagsDetailsBinding
import com.godaMeal.meals.databinding.ContentTagsDetailsBinding
import com.godaMeal.meals.menustags.data.uiModels.ItemOfTags


class TagsDetailsActivity : AppCompatActivity() {
    companion object {
        const val DETAILS_ITEM = "details_item"
    }

    lateinit var binding: ActivityTagsDetailsBinding
    lateinit var contentTagsDetailsBinding: ContentTagsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTagsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar(binding.toolbar)
        initUi()
    }

    private fun initUi() {
        val itemOfTags = intent.getParcelableExtra<ItemOfTags>(DETAILS_ITEM)
        itemOfTags?.let {
            binding.imgItem.setImageURI(itemOfTags.photoUrl)
            supportActionBar?.title = itemOfTags.name
            contentTagsDetailsBinding = binding.contentDetails
            contentTagsDetailsBinding.tvDetails.text = itemOfTags.description
        }
    }

    private fun initToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
