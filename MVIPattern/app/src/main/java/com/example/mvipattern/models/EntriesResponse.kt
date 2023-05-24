package com.example.mvipattern.models

import com.google.gson.annotations.SerializedName

data class EntriesResponse(
    @SerializedName("count")
    val count: Long,
    @SerializedName("entries")
    val values: List<EntryModel>)

data class EntryModel(
    @SerializedName("API")
    val api: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("Category")
    val category: String,
)