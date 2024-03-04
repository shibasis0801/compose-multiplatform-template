package com.stark.network

import kotlinx.serialization.Serializable

@Serializable
data class SampleData(
    val message: String,
    val author: String
)