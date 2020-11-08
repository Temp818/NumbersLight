package com.dev.numberslight.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dev.numberslight.detail.repository.DetailRepository
import com.dev.numberslight.detail.viewmodel.DetailViewModel
import com.dev.numberslight.model.Detail
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
class DetailViewModelTest {

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
    fun testGetDetailWithSuccess() {
        runBlocking {
            //region Given
            val detailResourceObserver = mock<Observer<Resource<out Detail>>>()
            val detailResourceCaptor: KArgumentCaptor<(Resource<Detail>)> =
                argumentCaptor()
            val detailRepository = mock<DetailRepository>()
            val data: Resource<Detail> =
                Resource.Done(Detail("Foo", "Bar", "Image"))
            val detailViewModel = DetailViewModel(detailRepository)
            whenever(detailRepository.getDetail(any())).thenReturn(data)
            detailViewModel.detail.observeForever(detailResourceObserver)
            //endregion

            //region When
            detailViewModel.getDetail("Foo")

            delay(10)
            //endregion

            //region Then
            verify(detailResourceObserver, times(2)).onChanged(detailResourceCaptor.capture())
            assertThat(detailResourceCaptor.firstValue).isEqualTo(Resource.Loading(null))
            assertThat(detailResourceCaptor.secondValue).isEqualTo((data))
            //endregion
        }
    }

    @Test
    fun testGetDetailWithFailure() {
        runBlocking {
            //region Given
            val detailResourceObserver = mock<Observer<Resource<out Detail>>>()
            val detailResourceCaptor: KArgumentCaptor<(Resource<Detail>)> =
                argumentCaptor()
            val detailRepository = mock<DetailRepository>()
            val detailViewModel = DetailViewModel(detailRepository)
            val error: Resource<Detail> = Resource.Error(Throwable(), null)
            whenever(detailRepository.getDetail(any())).thenReturn(error)
            detailViewModel.detail.observeForever(detailResourceObserver)
            //endregion

            //region When
            detailViewModel.getDetail("Foo")
            delay(10)
            //endregion

            //region Then
            verify(detailResourceObserver, times(2)).onChanged(detailResourceCaptor.capture())
            assertThat(detailResourceCaptor.firstValue).isEqualTo(Resource.Loading(null))
            assertThat(detailResourceCaptor.secondValue).isEqualTo((error))
            //endregion
        }
    }
}


