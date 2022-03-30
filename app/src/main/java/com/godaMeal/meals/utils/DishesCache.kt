package com.godaMeal.meals.utils

import com.godaMeal.meals.menustags.data.uiModels.TagDishe

import io.reactivex.Observable

interface DishesCache {

    fun clear()
    fun save(movieEntity: TagDishe)
    fun remove(movieEntity: TagDishe)
    fun saveAll(movieEntities: List<TagDishe>)
    fun getAll(): Observable<List<TagDishe>>
    fun get(movieId: Int): Observable<Optional<TagDishe>>
    fun isEmpty(): Boolean
}