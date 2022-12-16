package com.example.chargingstationsfinder.domain

interface Station {

    /** id of the charging station */
    val id: Int

    /** operator id of the charging station */

    val operatorID: Int


    /** Address information of the charging station */

    val addressInfo: Address

    /**number of charging points of station*/
    val chargingPointsCount: Int
}