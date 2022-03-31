package com.godaMeal.meals.menustags.domain.usecases


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.godaMeal.meals.menustags.data.uiModels.TagDishe
import com.godaMeal.meals.menustags.domain.repo.ITagRepository
import kotlinx.coroutines.flow.Flow

const val pageCountItem = 20

class TagsPaginationUseCase(
    private val repo: ITagRepository
) {

    operator fun invoke(
        dataLoaded: () -> Unit
    ): Flow<PagingData<TagDishe>> {
        return Pager(
            PagingConfig(
                pageSize = pageCountItem,
                enablePlaceholders = true,
                prefetchDistance = 10
            ),
        ) {
            repo.listTags(dataLoaded)
        }.flow/*.asLiveData()*/

    }

}
