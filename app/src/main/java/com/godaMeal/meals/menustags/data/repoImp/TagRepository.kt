package com.godaMeal.meals.menustags.data.repoImp

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.godaMeal.meals.menustags.data.remote.PaginatedSocialFeedRemoteDataSource
import com.godaMeal.meals.menustags.data.remote.RemoteTagDataSouce
import com.godaMeal.meals.menustags.data.remote.api.TagsService
import com.godaMeal.meals.menustags.data.uiModels.ItemOfTags
import com.godaMeal.meals.menustags.data.uiModels.ItemOfTagsResult
import com.godaMeal.meals.menustags.data.uiModels.TagDishe
import com.godaMeal.meals.menustags.db.TagsDatabase
import com.godaMeal.meals.menustags.db.TagsLocalCacheInterface
import com.godaMeal.meals.menustags.domain.repo.ITagRepository

class TagRepository(
    private val service: TagsService,
    private val remoteTagDataSouce: RemoteTagDataSouce,
    private val cache: TagsLocalCacheInterface,
    val tagsDatabase: TagsDatabase
) :
    ITagRepository {
    override fun listTags(dataLoaded: () -> Unit): PagingSource<Int, TagDishe> {

        return PaginatedSocialFeedRemoteDataSource(service, dataLoaded, cache,tagsDatabase)
    }

    override fun allMovies(): LiveData<List<TagDishe>> {
        return cache.getAll()
    }
    override suspend fun insertTags(list: List<TagDishe>){
        return cache.insertTags(list)
    }

    override suspend fun getAvailableItems(tagName: String): ItemOfTagsResult {

        val itemOfTagsResult = ItemOfTagsResult()
        runCatching {
            remoteTagDataSouce.getAvailableItems(tagName)
        }.onSuccess { itemResponse ->
            val itemOfTags = itemResponse.itemResponse.map {
                ItemOfTags(
                    it.description,
                    tagName,
                    it.id,
                    it.name,
                    it.photoUrl
                )
            }
            cache.insertItemOfTags(itemOfTags)
            itemResponse.itemResponse = itemOfTags
            itemOfTagsResult.data = itemResponse.itemResponse


        }.onFailure {
            itemOfTagsResult.data = cache.getTagItemByTagName(tagName)
            itemOfTagsResult.networkErrors = it.message
        }
        return itemOfTagsResult
    }


}