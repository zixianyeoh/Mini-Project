package com.example.mini.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val username: String,
    val email: String? = null,
    val phone: String? = null
)
