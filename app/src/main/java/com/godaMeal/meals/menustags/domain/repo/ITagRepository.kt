package com.godaMeal.meals.menustags.domain.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.godaMeal.meals.menustags.data.uiModels.ItemOfTagsResult
import com.godaMeal.meals.menustags.data.uiModels.TagDishe

interface ITagRepository {

    suspend fun getAvailableItems(tagName: String): ItemOfTagsResult
    fun allMovies(): LiveData<List<TagDishe>>

    fun listTags(dataLoaded: () -> Unit): PagingSource<Int, TagDishe>
}