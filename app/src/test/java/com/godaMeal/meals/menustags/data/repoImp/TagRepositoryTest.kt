package com.godaMeal.meals.menustags.data.repoImp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.goda.movieapp.MainCoroutineRule
import com.godaMeal.meals.itemResponse
import com.godaMeal.meals.menustags.data.remote.RemoteTagDataSouce
import com.godaMeal.meals.menustags.data.remote.api.TagsService
import com.godaMeal.meals.menustags.db.TagsDatabase
import com.godaMeal.meals.menustags.db.TagsLocalCacheInterface
import com.godaMeal.meals.menustags.domain.repo.ITagRepository
import com.godaMeal.meals.tagDessertItems
import com.godaMeal.meals.tagName
import com.godaMeal.meals.utils.Status
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
@OptIn(ExperimentalCoroutinesApi::class)
class TagRepositoryTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    lateinit var repository: ITagRepository


    lateinit var localDataSource: TagsLocalCacheInterface
    lateinit var remoteTagDataSouce: RemoteTagDataSouce
    lateinit var service: TagsService

    lateinit var tagsDatabase: TagsDatabase

    @Before
    fun before() {
        service = mock()
        localDataSource = mock()
        remoteTagDataSouce= mock()
        tagsDatabase= mock()

        repository = TagRepository(service,remoteTagDataSouce, localDataSource, tagsDatabase )
    }

    @Test
    fun `Given tag name when gettagbYtagname success in remote return success list `() =
        mainCoroutineRule.runBlockingTest {
            //arrange

            Mockito.`when`(remoteTagDataSouce.getAvailableItems(tagName = tagName)).thenReturn(itemResponse)

            //act
            val result = repository.getAvailableItems(tagName)

            //assert
            assert(result.data == itemResponse.itemResponse)


        }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun `Given tag name when gettagbYtagname success in local and exception in remote return success list`() =
        mainCoroutineRule.runBlockingTest {

            Mockito.`when`(localDataSource.getTagItemByTagName(tagName))
                .thenReturn(itemResponse.itemResponse)
            Mockito.doThrow(Exception("error")).`when`(remoteTagDataSouce.getAvailableItems(tagName))

            val result = repository.getAvailableItems(tagName)

            //assert
            assert(result.data == itemResponse.itemResponse)


        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = Exception::class)
    fun `Given tag name  when gettagbYtagname and exception in local , remote return exception failed  `() =
        mainCoroutineRule.runBlockingTest {

            Mockito.doThrow(Exception("error")).`when`(localDataSource.getTagItemByTagName(tagName))
            Mockito.doThrow(Exception("error")).`when`(remoteTagDataSouce.getAvailableItems(tagName))

            repository.getAvailableItems(tagName)


        }
}