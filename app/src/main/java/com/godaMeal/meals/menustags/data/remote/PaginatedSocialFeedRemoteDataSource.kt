package com.godaMeal.meals.menustags.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.godaMeal.meals.menustags.data.remote.api.TagsService
import com.godaMeal.meals.menustags.data.uiModels.TagDishe
import com.godaMeal.meals.menustags.db.TagsDatabase
import com.godaMeal.meals.menustags.db.TagsLocalCache
import com.godaMeal.meals.menustags.db.TagsLocalCacheInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.anko.doAsync


class PaginatedSocialFeedRemoteDataSource(
    private val service: TagsService,

    private val dataLoaded: () -> Unit,
    val cache: TagsLocalCacheInterface,
    val tagsDatabase: TagsDatabase
) :
    PagingSource<Int, TagDishe>() {
    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TagDishe> {

        return try {
             val page = params.key ?:0
           // val top = pageCountItem
            //val skip = params.key ?: 0
            val response =
                service.getTagsByPage(
                    page
                )

            dataLoaded.invoke()
            val list=response.tags
           // val db=list

        /*    doAsync {
                cache.insertTags(db.subList(0,list.size)) {
                   *//* lastRequestedPage++
                    isRequestInProgress = false*//* }
            }*/
         //   withContext(Dispatchers.IO){

         //   }
         //   tagsDatabase.withTransaction {

                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
             //   cache.insertTags(db.subList(0,list.size))
           // }

            LoadResult.Page(
                data =  /*if (list.isEmpty())databaseList.value.orEmpty() else*/ list ?: listOf(),
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (list.isEmpty() ) null else page.plus(
                    1
                )
            )
        } catch (exception: Exception) {
            _networkErrors.postValue(exception.message)
            isRequestInProgress = false

            dataLoaded.invoke()
            LoadResult.Error(exception)

        }


    }

    override fun getRefreshKey(state: PagingState<Int, TagDishe>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
  /* override fun getRefreshKey(state: PagingState<Int, TagDishe>): Int? {
       return null
   }*/

}
