package com.example.rentavirtual.data.models

import com.google.firebase.Timestamp

data class Rental(
    val id: String = "",
    val movieId: String = "",
    val movieTitle: String = "",
    val userId: String = "",
    val userName: String = "",
    val startDate: Timestamp? = null,
    val endDate: Timestamp? = null
)
