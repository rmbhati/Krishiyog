package com.kgk.task1.ui

import com.google.gson.annotations.SerializedName

data class ListData(
    @SerializedName("author") val author: String,
    @SerializedName("name") val name: String,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("url") val url: String,
    @SerializedName("description") val description: String,
    @SerializedName("language") val language: String,
    @SerializedName("languageColor") val languageColor: String,
    @SerializedName("stars") val stars: String,
    @SerializedName("forks") val forks: String,
    @SerializedName("currentPeriodStars") val currentPeriodStars: String,
    @SerializedName("builtBy") val builtBy: List<BuildByData>,
)

data class BuildByData(
    @SerializedName("href") val href: String,
    @SerializedName("username") val username: String,
    @SerializedName("avatar") val avatar: String,

    )

