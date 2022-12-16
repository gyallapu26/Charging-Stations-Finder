package com.example.chargingstationsfinder.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.chargingstationsfinder.TestCoroutineRule
import com.example.chargingstationsfinder.domain.AddressImpl
import com.example.chargingstationsfinder.domain.Station
import com.example.chargingstationsfinder.domain.StationImpl
import com.example.chargingstationsfinder.domain.StationsFinderUseCase
import com.example.chargingstationsfinder.network.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class StationsFinderViewModelTest {

    private lateinit var viewModel: StationsFinderViewModel
    private val mockStationsFinderUseCase: StationsFinderUseCase =
        Mockito.mock(StationsFinderUseCase::class.java)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()


    @BeforeEach
    fun setUp() {
        viewModel =
            StationsFinderViewModel(
                mockStationsFinderUseCase,
                mainCoroutineRule.testCoroutineDispatcher
            )
    }

    @Test
    fun `stationFinderUseCase should return error result`() = mainCoroutineRule.runBlockingTest {

        val errorMessage = "Something went wrong!"
        Mockito.`when`(mockStationsFinderUseCase.invoke())
            .thenReturn(flow { Result.Error(errorMessage) })
        val result = mutableListOf<StationContentUiState>()
        val job = launch {
            viewModel.uiState.toList(result)
        }
        viewModel.fetchStations()
        println(result)
        val currentUiState = result.last()
        assert(currentUiState.stations.isNotEmpty())
        job.cancel()
    }

    @Test
    fun `stationFinderUseCase should return stations result`() = mainCoroutineRule.runBlockingTest {

        val fakeStation = StationImpl(
            id = Int.MAX_VALUE,
            chargingPointsCount = 0,
            addressInfo = AddressImpl(
                latitude = 52.526,
                longitude = 13.415,
                title = "",
                town = "",
                addressLine = ""
            ),
            operatorID = Int.MIN_VALUE
        )
        val fakeStations: MutableList<Station> = mutableListOf<Station>().apply { add(fakeStation) }
        Mockito.`when`(mockStationsFinderUseCase.invoke())
            .thenReturn(flow { Result.Success(fakeStations) })
        val result = mutableListOf<StationContentUiState>()
        val job = launch {
            viewModel.uiState.toList(result)
        }
        viewModel.fetchStations()
        val currentUiState = result.last()
        assert(currentUiState.stations.isNotEmpty())
        assert(currentUiState.stations.first() == fakeStation)
        job.cancel()
    }

}