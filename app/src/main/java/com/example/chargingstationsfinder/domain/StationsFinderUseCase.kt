package com.example.chargingstationsfinder.domain

import com.example.chargingstationsfinder.domain.repository.StationsRepository
import javax.inject.Inject

/**
 * UseCase encapsulate complex business logic and can be reused by mutiple viewmodel
 * Although @see GetChargingStations might not add much benefit in simple case but
 * when app grows complex it's useful!
 * */

class StationsFinderUseCase @Inject constructor(private val stationsRepository: StationsRepository) {

    suspend operator fun invoke() = stationsRepository.fetchStations()
}