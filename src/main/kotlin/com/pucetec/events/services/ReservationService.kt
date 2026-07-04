package com.pucetec.events.services

import com.pucetec.events.dto.ReservationRequest
import com.pucetec.events.dto.ReservationResponse
import com.pucetec.events.entities.Event
import com.pucetec.events.entities.Reservation
import com.pucetec.events.exceptions.*
import com.pucetec.events.mappers.toResponse
import com.pucetec.events.repositories.AttendeeRepository
import com.pucetec.events.repositories.EventRepository
import com.pucetec.events.repositories.ReservationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val attendeeRepository: AttendeeRepository,
    private val eventRepository: EventRepository
) {
    fun createReservation(request: ReservationRequest): ReservationResponse {
        val attendee = attendeeRepository.findById(request.attendeeId)
            .orElseThrow { AttendeeNotFoundException("Attendee not found with ID: ${request.attendeeId}") }

        val event = eventRepository.findById(request.eventId)
            .orElseThrow { EventNotFoundException("Event not found with ID: ${request.eventId}") }

        if (event.availableTickets <= 0) {
            throw SoldOutException("Event is sold out")
        }

        val activeReservations = reservationRepository.countByAttendeeIdAndStatus(attendee.id, "ACTIVE")
        if (activeReservations >= 4) {
            throw ReservationLimitExceededException("Attendee already has 4 active reservations")
        }

        val updatedEvent = Event(
            id = event.id,
            name = event.name,
            venue = event.venue,
            totalTickets = event.totalTickets,
            availableTickets = event.availableTickets - 1
        )
        eventRepository.save(updatedEvent)

        val reservation = Reservation(
            attendee = attendee,
            event = updatedEvent,
            status = "ACTIVE",
            createdAt = LocalDateTime.now()
        )
        val saved = reservationRepository.save(reservation)
        return saved.toResponse()
    }

    fun cancelReservation(id: Long): ReservationResponse {
        val reservation = reservationRepository.findById(id)
            .orElseThrow { ReservationNotFoundException("Reservation not found with ID: $id") }

        if (reservation.status == "CANCELLED") {
            throw ReservationAlreadyCancelledException("Reservation is already cancelled")
        }

        val event = reservation.event
        val updatedEvent = Event(
            id = event.id,
            name = event.name,
            venue = event.venue,
            totalTickets = event.totalTickets,
            availableTickets = event.availableTickets + 1
        )
        eventRepository.save(updatedEvent)

        val updatedReservation = Reservation(
            id = reservation.id,
            attendee = reservation.attendee,
            event = updatedEvent,
            status = "CANCELLED",
            createdAt = reservation.createdAt
        )
        val saved = reservationRepository.save(updatedReservation)
        return saved.toResponse()
    }

    @Transactional(readOnly = true)
    fun getAllReservations(): List<ReservationResponse> {
        return reservationRepository.findAll().map { it.toResponse() }
    }
}