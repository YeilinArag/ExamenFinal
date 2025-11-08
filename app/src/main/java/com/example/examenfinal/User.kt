package com.example.rentavirtual.data.models

data class User(
    val uid: String = "",
    val fullName: String = "",
    val age: Int = 0,
    val cardNumberLast4: String = "",
    val photoUrl: String = "",
    val role: String = "user" // "user" o "admin"
)
