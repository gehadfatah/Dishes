package com.godaMeal.meals.menustags.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.godaMeal.meals.menustags.data.uiModels.ItemOfTags
import com.godaMeal.meals.menustags.data.uiModels.TagDishe
import java.util.concurrent.Executors

class TagsLocalCache(
    private val tagsDao: TagsDao, private val itemOfTagsDao: ItemOfTagsDao
) {
    fun insertTags(tags: List<TagDishe>, insertFinished: () -> Unit) {
        Executors.newSingleThreadExecutor().execute {
            tagsDao.insert(tags)
            insertFinished()
        }
    }

    suspend fun insertItemOfTags(itemOfTags: List<ItemOfTags>) {
        itemOfTagsDao.insert(itemOfTags)
    }

    suspend fun getTagItemByTagName(name: String): List<ItemOfTags> {
        return itemOfTagsDao.tagsByName(name)
    }

    fun getAllTagData(): DataSource.Factory<Int, TagDishe> {
        return tagsDao.tagsByName()
    }
    fun getAll(): LiveData<List<TagDishe>> {
        return tagsDao.getAll()
    }
}
