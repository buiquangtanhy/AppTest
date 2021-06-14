package com.example.appTest.repository.remote

import com.fasterxml.jackson.annotation.JsonProperty

data class DataResponse<T> (
    @JsonProperty("result")
    val result: Boolean,
    @JsonProperty("data")
    val data: T?,
    @JsonProperty("error")
    val error: String? = null
)

