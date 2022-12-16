package com.example.chargingstationsfinder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chargingstationsfinder.domain.StationsFinderUseCase
import com.example.chargingstationsfinder.network.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StationsFinderViewModel @Inject constructor(
    private val stationsFinderUseCase: StationsFinderUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _uiState: MutableStateFlow<StationContentUiState> =
        MutableStateFlow(StationContentUiState())
    val uiState = _uiState.asStateFlow()


     fun fetchStations() {
        viewModelScope.launch(ioDispatcher) {
            stationsFinderUseCase().collectLatest { result ->
                _uiState.update { currentUiState ->
                    when (result) {
                        is Result.Success -> {
                            currentUiState.copy(
                                isLoading = false,
                                stations = result.data,
                                errorMessage = ""
                            )
                        }
                        is Result.Error -> {
                            currentUiState.copy(
                                isLoading = false,
                                errorMessage = result.errorMessage
                            )
                        }
                        else -> currentUiState
                    }
                }

            }
        }
    }

}