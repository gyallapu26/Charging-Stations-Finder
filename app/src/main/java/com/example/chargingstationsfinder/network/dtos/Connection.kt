package com.example.chargingstationsfinder.network.dtos

data class Connection(
    val Amps: Int,
    val Comments: String,
    val ConnectionTypeID: Int,
    val CurrentTypeID: Int,
    val ID: Int,
    val LevelID: Int,
    val PowerKW: Double,
    val Quantity: Int,
    val StatusTypeID: Int,
    val Voltage: Int
)