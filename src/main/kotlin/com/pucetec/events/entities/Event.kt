package com.pucetec.events.entities

import jakarta.persistence.*

@Entity
@Table(name = "events")
class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val name: String = "",

    val venue: String = "",

    val totalTickets: Int = 0,

    val availableTickets: Int = 0
)
