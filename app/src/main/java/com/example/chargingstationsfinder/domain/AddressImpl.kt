package com.example.chargingstationsfinder.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressImpl(
    override val latitude: Double,
    override val longitude: Double,
    override val title: String,
    override val town: String,
    override val addressLine: String
) : Address, Parcelable