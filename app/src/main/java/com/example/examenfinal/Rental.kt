package com.example.examenfinal.Rental

data class Rental(
    val id: String = "",
    val userId: String = "",
    val movieId: String = "",
    val startDate: Long = 0L,   // <-- Long
    val endDate: Long = 0L,     // <-- Long
    val status: String = "active"
)
