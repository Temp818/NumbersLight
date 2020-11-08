package com.dev.numberslight.numbers.viewmodel

import androidx.lifecycle.*
import com.dev.numberslight.model.NumberLight
import com.dev.numberslight.repository.NumbersRepository
import com.dev.numberslight.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class NumbersViewModel @Inject constructor(
    private val numbersRepository: NumbersRepository
) : ViewModel() {

    private var _numbers = MutableLiveData<Resource<List<NumberLight>>>()
    val numbers: LiveData<Resource<List<NumberLight>>>
        get() = _numbers

    fun getNumbers() = viewModelScope.launch {
        _numbers.value = Resource.Loading(null)
        val data = numbersRepository.getNumbers()
        _numbers.value = data
    }
}
