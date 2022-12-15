package com.kgk.task1.ui

import com.google.gson.annotations.SerializedName

data class ListData(
    @SerializedName("EMP_ID") val empId: String,
    @SerializedName("FULL_NAME") val empName: String,
    @SerializedName("PHOTO_PATH") val empPhoto: String,
    @SerializedName("SUBAREA_ID") val subAreaId: String,
    @SerializedName("CSC_ID") val cscId: String,
    @SerializedName("CSC_NAME") val cscName: String,
    @SerializedName("CURRENCY") val currency: String,
)

