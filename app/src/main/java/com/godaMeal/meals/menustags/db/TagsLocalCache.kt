package com.godaMeal.meals.menustags.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.godaMeal.meals.menustags.data.uiModels.ItemOfTags
import com.godaMeal.meals.menustags.data.uiModels.TagDishe
import java.util.concurrent.Executors

interface TagsLocalCacheInterface{
    fun insertTags(tags: List<TagDishe>, insertFinished: () -> Unit)
    suspend fun insertItemOfTags(itemOfTags: List<ItemOfTags>)
    suspend fun getTagItemByTagName(name: String): List<ItemOfTags>
    fun getAllTagData(): DataSource.Factory<Int, TagDishe>
    fun getAll(): LiveData<List<TagDishe>>
}

class TagsLocalCache(
    private val tagsDao: TagsDao, private val itemOfTagsDao: ItemOfTagsDao
):TagsLocalCacheInterface {
    override fun insertTags(tags: List<TagDishe>, insertFinished: () -> Unit) {
        Executors.newSingleThreadExecutor().execute {
            tagsDao.insert(tags)
            insertFinished()
        }
    }

    override suspend fun insertItemOfTags(itemOfTags: List<ItemOfTags>) {
        itemOfTagsDao.insert(itemOfTags)
    }

    override suspend fun getTagItemByTagName(name: String): List<ItemOfTags> {
        return itemOfTagsDao.tagsByName(name)
    }

    override fun getAllTagData(): DataSource.Factory<Int, TagDishe> {
        return tagsDao.tagsByName()
    }
    override fun getAll(): LiveData<List<TagDishe>> {
        return tagsDao.getAll()
    }
}
