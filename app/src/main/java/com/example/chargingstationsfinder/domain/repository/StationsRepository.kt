package com.example.chargingstationsfinder.domain.repository

import android.content.Context
import com.example.chargingstationsfinder.R
import com.example.chargingstationsfinder.di.Constants
import com.example.chargingstationsfinder.domain.AddressImpl
import com.example.chargingstationsfinder.domain.Station
import com.example.chargingstationsfinder.domain.StationImpl
import com.example.chargingstationsfinder.helper.isNetworkAvailable
import com.example.chargingstationsfinder.network.ApiService
import com.example.chargingstationsfinder.network.dtos.StationsResponseDtoItem
import com.example.chargingstationsfinder.network.util.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named

class StationsRepository @Inject constructor(
    private val apiService: ApiService,
    @Named(Constants.KEY_CONSTANT) private val key: String,
    private val ioDispatcher: CoroutineDispatcher,
    @Named(Constants.COUNTRY_CODE) private val countryCode: String,
    @ApplicationContext private val context: Context

) {

    suspend fun fetchStations(): Flow<Result<List<Station>>> = flow {
        while (true) {
            if (context.isNetworkAvailable()) {
                val response = apiService.fetchStations(key, countryCode)
                if (response.isSuccessful) {
                    try {
                        val results = response.body().orEmpty()
                        results.map { convertToStationData(it) }
                        emit(Result.Success(results.map { convertToStationData(it) }))
                        kotlinx.coroutines.delay(30000)

                    } catch (e: Exception) {
                        emit(Result.Error((e.localizedMessage.orEmpty())))
                    }
                } else {
                    emit(Result.Error((response.errorBody().toString())))
                }
            } else emit(Result.Error(context.getString(R.string.error_msg_no_internet)))
        }
    }.flowOn(ioDispatcher)

    private fun convertToStationData(item: StationsResponseDtoItem): StationImpl =
        StationImpl(
            id = item.ID,
            operatorID = item.OperatorID,
            addressInfo = AddressImpl(
                latitude = item.AddressInfo.Latitude,
                longitude = item.AddressInfo.Longitude,
                town = item.AddressInfo.Town,
                title = item.AddressInfo.Title,
                addressLine = item.AddressInfo.AddressLine1
            ),
            chargingPointsCount = item.NumberOfPoints
        )

}


