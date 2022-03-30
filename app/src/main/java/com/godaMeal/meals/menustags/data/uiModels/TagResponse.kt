package com.godaMeal.meals.menustags.data.uiModels

import com.google.gson.annotations.SerializedName

class TagResponse(@SerializedName("tags") val tags: List<TagDishe>)