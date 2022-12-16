package com.example.chargingstationsfinder.ui

import com.example.chargingstationsfinder.domain.AddressImpl
import com.example.chargingstationsfinder.domain.Station
import com.example.chargingstationsfinder.domain.StationImpl

data class StationContentUiState
    (
    val stations: List<Station> = mutableListOf<Station>().apply { add(defaultStation) },
    val isLoading: Boolean = true,
    val errorMessage: String = ""
)


/** Default station used in case of loading state or error state */
val defaultStation = StationImpl(
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