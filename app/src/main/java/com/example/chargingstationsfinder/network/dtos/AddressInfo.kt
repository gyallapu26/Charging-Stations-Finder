package com.example.chargingstationsfinder.network.dtos

data class AddressInfo(
    val AccessComments: String,
    val AddressLine1: String,
    val ContactTelephone1: String,
    val CountryID: Int,
    val DistanceUnit: Int,
    val ID: Int,
    val Latitude: Double,
    val Longitude: Double,
    val Postcode: String,
    val RelatedURL: String,
    val StateOrProvince: String,
    val Title: String,
    val Town: String
)