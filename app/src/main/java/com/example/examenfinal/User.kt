package com.example.examenfinal.User

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val age: Int = 0,
    val creditCard: String = "",
    val role: String = "user",  // "user" o "admin"
    val photoUrl: String? = null
)

