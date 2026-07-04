package com.pucetec.events.repositories

import com.pucetec.events.entities.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationsRepository : JpaRepository<Reservation, Long> {
    fun countByAttendeeIdAndStatus(attendeeId: String, status: String): Int
}

typealias ReservationRepository = ReservationsRepository