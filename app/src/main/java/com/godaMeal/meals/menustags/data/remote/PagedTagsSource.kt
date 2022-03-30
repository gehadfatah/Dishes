package com.godaMeal.meals.menustags.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.godaMeal.meals.menustags.data.remote.api.TagsService
import com.godaMeal.meals.menustags.data.uiModels.TagDishe

import javax.inject.Inject


class PagedTagsSource @Inject constructor(
    private val source: TagsService,
) : PagingSource<Int, TagDishe>() {

    override val jumpingSupported: Boolean
        get() = true

    override val keyReuseSupported: Boolean
        get() = true

    override fun getRefreshKey(state: PagingState<Int, TagDishe>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TagDishe> {
        return try {
            val position = params.key ?: 0
            val response = source.getTagsByPage(position)
            LoadResult.Page(
                data = response.tags,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (response.tags.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}