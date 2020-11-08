package com.dev.numberslight.repository

import com.dev.numberslight.api.NumbersLightService
import com.dev.numberslight.api.response.DetailResponse
import com.dev.numberslight.api.response.NumberLightResponse
import com.dev.numberslight.mapper.NumberMapper
import com.dev.numberslight.model.Detail
import com.dev.numberslight.model.NumberLight
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
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NumberRepositoryTest {

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
    fun testFetchData() {
        runBlocking {
            //region Given
            val mapper = mock<NumberMapper>()
            val service = mock<NumbersLightService>()
            val repository = NumberRepositoryImpl(mapper, service)
            val getNumberLightCall = listOf(NumberLightResponse("Foo", "Bar"))
            val expectedData = Resource.Done(listOf(NumberLight("Foo","Bar")))
            whenever(service.getNumbersLight()).thenReturn(getNumberLightCall)
            whenever(mapper.convertNumbersResponseToNumber(any())).thenReturn(listOf(NumberLight("Foo","Bar")))
            //endregion

            //region When
            val data = repository.getNumbers()
            //endregion

            //region Then
            verify(service).getNumbersLight()
            verify(mapper).convertNumbersResponseToNumber(getNumberLightCall)
            assertThat(data).isEqualTo(expectedData)
            //endregion
        }
    }

    @Test
    fun testFetchDataOnce() {
        runBlocking {
            //region Given
            val mapper = mock<NumberMapper>()
            val service = mock<NumbersLightService>()
            val repository = NumberRepositoryImpl(mapper, service)
            val getNumberLightCall = listOf(NumberLightResponse("Foo", "Bar"))
            val expectedData = Resource.Done(listOf(NumberLight("Foo","Bar")))
            whenever(service.getNumbersLight()).thenReturn(getNumberLightCall)
            whenever(mapper.convertNumbersResponseToNumber(any())).thenReturn(listOf(NumberLight("Foo","Bar")))
            //endregion

            //region When
            repository.getNumbers()
            repository.getNumbers()
            //endregion

            //region Then
            verify(service, times(1)).getNumbersLight()
            verify(mapper, times(1)).convertNumbersResponseToNumber(getNumberLightCall)
            //endregion
        }
    }

    @Test
    fun testFetchDetailData() {
        runBlocking {
            //region Given
            val mapper = mock<NumberMapper>()
            val service = mock<NumbersLightService>()
            val repository = NumberRepositoryImpl(mapper, service)
            val getDetailCall = DetailResponse("Foo", "Bar", "Image")
            val expectedData = Resource.Done(Detail("Foo", "Bar", "Image"))
            whenever(service.getDetail(any())).thenReturn(getDetailCall)
            whenever(mapper.convertDetailResponseToDetail(any())).thenReturn(Detail("Foo", "Bar", "Image"))
            //endregion

            //region When
            val data = repository.getDetail("Foo")
            //endregion

            //region Then
            verify(service).getDetail("Foo")
            verify(mapper).convertDetailResponseToDetail(getDetailCall)
            assertThat(data).isEqualTo(expectedData)
            //endregion
        }
    }
}


