package com.godaMeal.meals.menustags.domain.usecases

import com.godaMeal.meals.menustags.data.uiModels.TagDishe
import com.godaMeal.meals.menustags.domain.repo.ITagRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InserTagsUseCase(
    private val repo: ITagRepository

) {
     suspend  fun invoke(list:List<TagDishe>) {
         withContext(Dispatchers.IO){

             repo.insertTags(list)
         }
    }

}