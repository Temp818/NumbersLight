package com.dev.numberslight.numbers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dev.numberslight.model.NumberLight
import com.dev.numberslight.repository.NumbersRepository
import com.dev.numberslight.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class NumbersViewModel @Inject constructor(
    private val numbersRepository: NumbersRepository
) : ViewModel() {

    var coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main
    private var _numbersResource = MutableLiveData<Resource<List<NumberLight>>>()
    val numbersResource: LiveData<Resource<List<NumberLight>>>
        get() = _numbersResource

    fun getNumbers() = liveData {
        emit(Resource.Loading(null))
        val data = numbersRepository.getNumbersAsync()
        emit(data)
    }
}
