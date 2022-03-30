package com.godaMeal.meals.menustags.data.uiModels


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tags")
data class TagDishe(
    @field:SerializedName("photoURL")
    var photoURL: String,
    @PrimaryKey @field:SerializedName("tagName")
    var tagName: String
)


