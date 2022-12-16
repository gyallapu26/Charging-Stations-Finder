package com.example.chargingstationsfinder.network.dtos

data class StationsResponseDtoItem(
    val AddressInfo: AddressInfo,
    val Connections: List<Connection>,
    val DataProviderID: Int,
    val DataQualityLevel: Int,
    val DateCreated: String,
    val DateLastStatusUpdate: String,
    val DateLastVerified: String,
    val GeneralComments: String,
    val ID: Int,
    val IsRecentlyVerified: Boolean,
    val NumberOfPoints: Int,
    val OperatorID: Int,
    val StatusTypeID: Int,
    val SubmissionStatusTypeID: Int,
    val UUID: String,
    val UsageCost: String,
    val UsageTypeID: Int
)