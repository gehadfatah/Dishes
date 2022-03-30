package com.godaMeal.meals.menustags.data.local


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.liveData
import com.goda.movieapp.MainCoroutineRule
import com.godaMeal.meals.LiveDataTestUtil
import com.godaMeal.meals.menustags.db.ItemOfTagsDao
import com.godaMeal.meals.menustags.db.TagsDao
import com.godaMeal.meals.menustags.db.TagsLocalCache
import com.godaMeal.meals.menustags.db.TagsLocalCacheInterface
import com.godaMeal.meals.tagDessertItems
import com.godaMeal.meals.tagName
import com.godaMeal.meals.tagsList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class DisheLocalDataSourceTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var localDataSource: TagsLocalCacheInterface


    @Mock
    lateinit var tagsDao: TagsDao

    @Mock
    lateinit var itemOfTagsDao: ItemOfTagsDao

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)

        localDataSource = TagsLocalCache(tagsDao, itemOfTagsDao)
    }




    @Test
    fun `Given tagname when it Desserts then return list items `() = mainCoroutineRule.runBlockingTest{
        Mockito.`when`(itemOfTagsDao.tagsByName(tagName)).thenReturn(tagDessertItems )

        val result = localDataSource.getTagItemByTagName(tagName)

        assert(result == tagDessertItems)
    }

    @Test(expected = Exception::class)
    fun `Given tagname when it Desserts then return failed result`() = mainCoroutineRule.runBlockingTest {

        Mockito.doThrow(Exception("error")).`when`(tagsDao.getAll())

        localDataSource.getTagItemByTagName(tagName)


    }

    @Test
    fun testInvalidEntityArticles() = mainCoroutineRule.runBlockingTest{
        Mockito.`when`(tagsDao.getAll()).thenReturn(liveData { tagsList })

        val result = localDataSource.getAll()

        assert(LiveDataTestUtil.getValue(result) == tagsList)
    }

    @Test(expected = Exception::class)
    fun testTopUpRequestThrowException() = mainCoroutineRule.runBlockingTest {

        Mockito.doThrow(Exception("error")).`when`(tagsDao.getAll())

        localDataSource.getAll()


    }




}