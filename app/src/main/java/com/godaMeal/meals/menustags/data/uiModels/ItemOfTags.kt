package com.godaMeal.meals.menustags.data.uiModels


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "itemOfTags")
@Parcelize
class ItemOfTags constructor(
    @field:SerializedName("description")
    var description: String?,
    @field:SerializedName("tagName")
    var tagName: String,
    @field:SerializedName("id")
    var id: Int = 0,
    @PrimaryKey @field:SerializedName("name")
    var name: String,
    @field:SerializedName("photoUrl")
    var photoUrl: String?
) : Parcelable

