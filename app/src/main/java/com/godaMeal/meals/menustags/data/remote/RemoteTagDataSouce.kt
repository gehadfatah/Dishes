package com.godaMeal.meals.menustags.data.remote

import com.godaMeal.meals.menustags.data.remote.api.TagsService
import com.godaMeal.meals.menustags.data.uiModels.ItemResponse


interface RemoteTagDataSouce{
   suspend fun  getAvailableItems(tagName: String): ItemResponse
}
class RemoteTagDataSouceImp(
    private val service: TagsService
) :RemoteTagDataSouce{
    override suspend fun getAvailableItems(tagName: String): ItemResponse =
        service.getAvailableItems(tagName)


}