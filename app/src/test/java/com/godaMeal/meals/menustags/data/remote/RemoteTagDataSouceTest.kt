package com.godaMeal.meals.menustags.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.goda.movieapp.MainCoroutineRule
import com.godaMeal.meals.getTagsRequest
import com.godaMeal.meals.itemResponse
import com.godaMeal.meals.menustags.data.remote.api.TagsService
import com.godaMeal.meals.menustags.db.TagsLocalCacheInterface
import com.godaMeal.meals.tagName
import com.godaMeal.meals.tagResponse
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RemoteTagDataSouceTest{
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var remoteDataSource:  RemoteTagDataSouce

    lateinit var service: TagsService

    @Before
    fun setup() {
        service = mock()
        remoteDataSource=RemoteTagDataSouceImp(service)
    }
    @Test
    fun `remote source get getAvailableItems return success`() = runBlocking {
        service = mock {
            onBlocking {

                getAvailableItems(tagName )
            } doReturn
                    itemResponse

        }

         val result = remoteDataSource.getAvailableItems(tagName)
        assert(result == itemResponse)
    }

    @Test(expected = Exception::class)
    fun `remote source getAvailableItems throw requestexception`(): Unit = runBlocking {

        service = mock {
            onBlocking {

                getAvailableItems(tagName )
            } doThrow
                    Exception("error")

        }

     remoteDataSource.getAvailableItems(tagName)
    }
}