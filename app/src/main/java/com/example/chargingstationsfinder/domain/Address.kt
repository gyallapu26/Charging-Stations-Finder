package com.example.chargingstationsfinder.domain

interface Address {

    /** latitude of the charging station */
    val latitude: Double

    /** longitude of the charging station */
    val longitude: Double

    /** Name of the charging station */
    val title: String

    /** Name of the town charging station located */
    val town: String

    /** Address line of the charging station */
    val addressLine: String

}