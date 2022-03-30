package com.godaMeal.meals.menustags.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.goda.movieapp.MainCoroutineRule
import com.godaMeal.meals.getTagsRequest
import com.godaMeal.meals.menustags.data.remote.api.TagsService
import com.godaMeal.meals.menustags.data.uiModels.TagResponse
import com.godaMeal.meals.menustags.db.TagsLocalCache
import com.godaMeal.meals.menustags.db.TagsLocalCacheInterface
import com.godaMeal.meals.tagResponse
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi

class PaginatedSocialFeedRemoteDataSourceTest{
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var remoteDataSource:  PaginatedSocialFeedRemoteDataSource

    lateinit var service: TagsService
    lateinit var cache: TagsLocalCacheInterface

    @Before
    fun setup() {
        cache = mock()
    }
    @Test
    fun `remote source get Early Closure return success`() = runBlocking {
        service = mock {
            onBlocking {

                getTagsByPage(getTagsRequest)
            } doReturn
                tagResponse

        }
        remoteDataSource =
            PaginatedSocialFeedRemoteDataSource(service, {  } ,cache = cache )
       // val result = remoteDataSource.geet(depositCommonRequest)
       // assert(result == depositDetailToCloseResponse)
    }

    @Test(expected = Exception::class)
    fun `remote source topUpDeposit throw requestexception`(): Unit = runBlocking {

/*        service = mock {
            onBlocking { getDetailCloseDeposit(depositCommonRequest) } doReturn
                    Response.error(402, null)
        }
        remoteDataSource =
            DepositEarlyClosureRemoteDataSourceImpl(service, mainCoroutineRule.coroutineContext)
        remoteDataSource.getDetailCloseDeposit(depositCommonRequest)*/
    }
}