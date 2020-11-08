package com.dev.numberslight.detail.viewmodel

import androidx.lifecycle.*
import com.dev.numberslight.model.Detail
import com.dev.numberslight.repository.DetailRepository
import com.dev.numberslight.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {

    private var _detail = MutableLiveData<Resource<Detail>>()
    val detail: LiveData<Resource<Detail>>
            get() = _detail

    fun getDetail(name: String) = viewModelScope.launch {
        _detail.value = Resource.Loading(null)
        val data = detailRepository.getDetail(name)
        _detail.value = data
    }
}
