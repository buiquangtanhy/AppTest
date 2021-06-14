package com.example.appTest.entity

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class Item(
    @JsonProperty("albumId")
    val albumId: Int,

    @JsonProperty("id")
    var id: Int,

    @JsonProperty("title")
    var title: String,

    @JsonProperty("url")
    var url: String,

    @JsonProperty("thumbnailUrl")
    var thumbnailUrl: String
) : Serializable