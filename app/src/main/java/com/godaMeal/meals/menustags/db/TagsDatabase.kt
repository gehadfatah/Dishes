package com.godaMeal.meals.menustags.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.godaMeal.meals.menustags.data.uiModels.ItemOfTags
import com.godaMeal.meals.menustags.data.uiModels.TagDishe

@Database(
    entities = [TagDishe::class, ItemOfTags::class],
    version = 1,
    exportSchema = false
)
abstract class TagsDatabase : RoomDatabase() {

    abstract fun reposDao(): TagsDao
    abstract fun itemOfTagsDao(): ItemOfTagsDao

    companion object {

        @Volatile
        private var INSTANCE: TagsDatabase? = null

        fun getInstance(context: Context): TagsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TagsDatabase::class.java, "menus.db"
            )
                .build()
    }
}
