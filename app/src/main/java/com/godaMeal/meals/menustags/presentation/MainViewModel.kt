package com.godaMeal.meals.menustags.presentation

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.godaMeal.meals.menustags.domain.repo.ITagRepository
import com.godaMeal.meals.menustags.data.uiModels.ItemOfTags
import com.godaMeal.meals.menustags.data.uiModels.TagDishe
import com.godaMeal.meals.menustags.domain.usecases.InserTagsUseCase
import com.godaMeal.meals.menustags.domain.usecases.TagsPaginationUseCase
import com.godaMeal.meals.utils.Resource
import com.godaMeal.meals.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val tagRepository: ITagRepository,
    private val paginationUseCase: TagsPaginationUseCase,
    private val inserTagsUseCase: InserTagsUseCase,
) : ViewModel() {


    // private var tagUserMutableStateFlow = MutableStateFlow<List<TagObject>(listOf())
    //val tagUserStateFlow: StateFlow<UiState<ResponseData<List<TagObject>>>> get() = tagUserMutableStateFlow

    private val _tags: MutableStateFlow<Flow<PagingData<TagDishe>>> = MutableStateFlow(flowOf())
    val tag get() = _tags


    val itemOfTagLiveData by lazy<LiveData<List<ItemOfTags>>> {
        itemOfTagMutableLiveData
    }
    private val itemOfTagMutableLiveData by lazy {
        MutableLiveData<List<ItemOfTags>>()
    }

    val errorLiveData by lazy<LiveData<String>> {
        errorMutableLiveData
    }
    private val errorMutableLiveData by lazy {
        MutableLiveData<String>()
    }

    val showLoadingLiveData by lazy<LiveData<Boolean>> {
        showLoadingMutableLiveData
    }
    private val showLoadingMutableLiveData by lazy {
        MutableLiveData<Boolean>()
    }

    val dataloadedLiveData
        get() = _isLoaded
    private val _isLoaded = MutableLiveData<Boolean>()


    fun fetchTags() =
        paginationUseCase {
            _isLoaded.value = (true)
            showLoadingMutableLiveData.postValue(false)
        }.cachedIn(viewModelScope)
            .onStart {
                showLoadingMutableLiveData.value = true
            }.onCompletion {
                showLoadingMutableLiveData.value = true
            }.catch {
                errorMutableLiveData.value = it.message
                showLoadingMutableLiveData.value = false
            }

    fun insertTagsToDatabase(list: List<TagDishe>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                inserTagsUseCase.invoke(list)
            }

        }
        /*  viewModelScope.launch {
              kotlin.runCatching {
                  tagRepository.insertTags(list)
              }.onSuccess {
                 // itemOfTagMutableLiveData.value = it.data
                  showLoadingMutableLiveData.value = false


              }.onFailure {
                  errorMutableLiveData.value = it.message
               //   showLoadingMutableLiveData.value = false
              }
          }*/
    }

    fun getMoviesFromDatabase(): LiveData<Resource<List<TagDishe>>> {
        showLoadingMutableLiveData.postValue(true)
        return Transformations.map(tagRepository.allMovies()) {
            showLoadingMutableLiveData.postValue(false)
            Resource(Status.SUCCESS, it, null)
        }
    }

    fun getItemByTagName(tagName: String) {
        showLoadingMutableLiveData.value = true
        viewModelScope.launch {
            kotlin.runCatching {
                tagRepository.getAvailableItems(tagName)
            }.onSuccess {
                itemOfTagMutableLiveData.value = it.data
                showLoadingMutableLiveData.value = false

            }.onFailure {
                errorMutableLiveData.value = it.message
                showLoadingMutableLiveData.value = false
            }
        }
    }


}