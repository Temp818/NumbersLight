package com.dev.numberslight.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dev.numberslight.model.NumberLight
import com.dev.numberslight.numbers.repository.NumbersRepository
import com.dev.numberslight.numbers.viewmodel.NumbersViewModel
import com.dev.numberslight.util.Resource
import com.dev.numberslight.util.TestCoroutineRule
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NumbersViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun testGetNumbersWithSuccess() {
        runBlocking {
            //region Given
            val numbersResourceObserver = mock<Observer<Resource<out List<NumberLight>>>>()
            val numbersResourceCaptor: KArgumentCaptor<(Resource<List<NumberLight>>)> =
                argumentCaptor()
            val numbersRepository = mock<NumbersRepository>()
            val data: Resource<List<NumberLight>> =
                Resource.Done(listOf(NumberLight("Foo", "Bar"), NumberLight("Name", "Image")))
            val numbersViewModel = NumbersViewModel(numbersRepository)
            whenever(numbersRepository.getNumbers()).thenReturn(data)
            numbersViewModel.numbers.observeForever(numbersResourceObserver)
            //endregion

            //region When
            numbersViewModel.getNumbers()

            delay(10)
            //endregion

            //region Then
            verify(numbersResourceObserver, times(2)).onChanged(numbersResourceCaptor.capture())
            assertThat(numbersResourceCaptor.firstValue).isEqualTo(Resource.Loading(null))
            assertThat(numbersResourceCaptor.secondValue).isEqualTo((data))
            //endregion
        }
    }

    @Test
    fun testGetNumbersWithFailure() {
        runBlocking {
            //region Given
            val numbersResourceObserver = mock<Observer<Resource<out List<NumberLight>>>>()
            val numbersResourceCaptor: KArgumentCaptor<(Resource<List<NumberLight>>)> =
                argumentCaptor()
            val numbersRepository = mock<NumbersRepository>()
            val numbersViewModel = NumbersViewModel(numbersRepository)
            val error: Resource<List<NumberLight>> = Resource.Error(Throwable(), null)
            whenever(numbersRepository.getNumbers()).thenReturn(error)
            numbersViewModel.numbers.observeForever(numbersResourceObserver)
            //endregion

            //region When
            numbersViewModel.getNumbers()
            delay(10)
            //endregion

            //region Then
            verify(numbersResourceObserver, times(2)).onChanged(numbersResourceCaptor.capture())
            assertThat(numbersResourceCaptor.firstValue).isEqualTo(Resource.Loading(null))
            assertThat(numbersResourceCaptor.secondValue).isEqualTo((error))
            //endregion
        }
    }
}


