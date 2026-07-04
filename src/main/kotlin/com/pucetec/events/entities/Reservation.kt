package com.pucetec.events.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reservations")
class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendee_id", nullable = false)
    val attendee: Attendee = Attendee(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    val event: Event = Event(),

    val status: String = "", // "ACTIVE" o "CANCELLED"

    val createdAt: LocalDateTime = LocalDateTime.now()
)
