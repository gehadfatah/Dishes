package com.godaMeal.meals.menustags.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.godaMeal.meals.menustags.data.uiModels.ItemOfTags


@Dao
interface ItemOfTagsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemsOfTagsDao: List<ItemOfTags>)


    @Query("SELECT * FROM itemOfTags WHERE tagName = :queryString")
    suspend fun tagsByName(queryString: String): List<ItemOfTags>
}
