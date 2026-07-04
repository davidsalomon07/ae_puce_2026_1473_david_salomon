package com.pucetec.events.dto

import jakarta.persistence.Id

data class AttendeeRequest(
    val name: String,
    val email: String
)

data class AttendeeResponse(
    val id: Long,
    val name: String,
    val email: String
)