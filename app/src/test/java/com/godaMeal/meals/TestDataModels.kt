package com.godaMeal.meals

import com.godaMeal.meals.menustags.data.uiModels.ItemOfTags
import com.godaMeal.meals.menustags.data.uiModels.ItemResponse
import com.godaMeal.meals.menustags.data.uiModels.TagDishe
import com.godaMeal.meals.menustags.data.uiModels.TagResponse

val getTagsRequest = 1
val tagResponse=TagResponse(tags = listOf(TagDishe("https://","Desserts")
,TagDishe("https://","Fishes")))

val tagsList=listOf(TagDishe("sk","Dessert"))

val tagName="Dessert"

val tagDessertItems= listOf(ItemOfTags("description","Dessert",id=0,name="dessert -0 ",""),
        ItemOfTags("description","Dessert",id=2,name="dessert -0 ","")
)
val itemResponse= ItemResponse(listOf(ItemOfTags("description","Dessert",id=2,name="dessert -0 ",""),
        ItemOfTags("description","Dessert",id=0,name="dessert -0 ","")))
