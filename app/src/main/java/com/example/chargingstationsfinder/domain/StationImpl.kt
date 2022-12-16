package com.example.chargingstationsfinder.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class StationImpl(
    override val id: Int,
    override val operatorID: Int,
    override val addressInfo: @RawValue Address,
    override val chargingPointsCount: Int
) : Station, Parcelable