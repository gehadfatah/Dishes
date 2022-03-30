package com.godaMeal.meals.menustags.di

import androidx.room.Room
import com.godaMeal.meals.menustags.domain.repo.ITagRepository
import com.godaMeal.meals.menustags.data.repoImp.TagRepository
import com.godaMeal.meals.menustags.data.remote.api.TagsService
import com.godaMeal.meals.menustags.db.TagsDatabase
import com.godaMeal.meals.menustags.db.TagsLocalCache
import com.godaMeal.meals.menustags.domain.usecases.TagsPaginationUseCase
import com.godaMeal.meals.menustags.presentation.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val mainModule = module {
/*    single {
        Room.databaseBuilder(
            androidContext(),
            RunningDatabase::class.java,
            RUNNING_DATABASE_NAME
        ).build()
    }*/
    factory { TagsDatabase.getInstance(androidContext()).reposDao() }
    factory { TagsDatabase.getInstance(androidContext()).itemOfTagsDao() }
    factory { TagsLocalCache(get(), get()) }
    factory { get<Retrofit>().create(TagsService::class.java) }
    factory<ITagRepository> { TagRepository(get(), get()) }
    viewModel { MainViewModel(get(),get()) }
    single { TagsPaginationUseCase(get()) }

}