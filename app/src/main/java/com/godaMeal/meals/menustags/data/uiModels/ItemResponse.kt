package com.godaMeal.meals.menustags.data.uiModels

import com.google.gson.annotations.SerializedName

class ItemResponse(@SerializedName("items") var itemResponse: List<ItemOfTags>)