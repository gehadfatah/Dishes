package com.godaMeal.meals.menustags.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.godaMeal.meals.menustags.data.uiModels.TagDishe

@Dao
interface TagsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tags: List<TagDishe>)
    @Query("SELECT * FROM tags Order By  tagName Asc ")
    fun getAll(): LiveData<List<TagDishe>>
    @Query("SELECT * FROM tags")
    fun tagsByName(): DataSource.Factory<Int, TagDishe>
}
