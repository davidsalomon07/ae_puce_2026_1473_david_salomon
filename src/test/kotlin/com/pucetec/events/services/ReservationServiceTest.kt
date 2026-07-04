package com.pucetec.events.services

import com.pucetec.events.dto.ReservationRequest
import com.pucetec.events.entities.Attendee
import com.pucetec.events.entities.Event
import com.pucetec.events.entities.Reservation
import com.pucetec.events.exceptions.*
import com.pucetec.events.repositories.AttendeeRepository
import com.pucetec.events.repositories.EventRepository
import com.pucetec.events.repositories.ReservationRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class ReservationServiceTest {

    @Mock
    lateinit var reservationRepository: ReservationRepository
    @Mock
    lateinit var attendeeRepository: AttendeeRepository
    @Mock
    lateinit var eventRepository: EventRepository

    @InjectMocks
    lateinit var reservationService: ReservationService

    @Test
    fun `createReservation creates reservation when valid`() {
        val request = ReservationRequest(1L, 2L)
        val attendee = Attendee(1L, "David Salomon", "ldsalomon@puce.edu.ec")
        val event = Event(2L, "Concierto", "Estadio", 10, 10)
        val reservation = Reservation(1L, attendee, event, "ACTIVE", LocalDateTime.now())

        `when`(attendeeRepository.findById(1L)).thenReturn(Optional.of(attendee))
        `when`(eventRepository.findById(2L)).thenReturn(Optional.of(event))
        `when`(reservationRepository.countByAttendeeIdAndStatus(1L, "ACTIVE")).thenReturn(0)
        `when`(reservationRepository.save(any(Reservation::class.java))).thenReturn(reservation)

        val response = reservationService.createReservation(request)

        assertEquals(1L, response.id)
        assertEquals("ACTIVE", response.status)
        verify(eventRepository, times(1)).save(any(Event::class.java))
    }

    @Test
    fun `createReservation throws AttendeeNotFoundException if attendee absent`() {
        val request = ReservationRequest(1L, 2L)
        `when`(attendeeRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<AttendeeNotFoundException> { reservationService.createReservation(request) }
    }

    @Test
    fun `createReservation throws EventNotFoundException if event absent`() {
        val request = ReservationRequest(1L, 2L)
        val attendee = Attendee(1L, "David Salomon", "ldsalomon@puce.edu.ec")
        `when`(attendeeRepository.findById(1L)).thenReturn(Optional.of(attendee))
        `when`(eventRepository.findById(2L)).thenReturn(Optional.empty())

        assertThrows<EventNotFoundException> { reservationService.createReservation(request) }
    }

    @Test
    fun `createReservation throws SoldOutException if no tickets available`() {
        val request = ReservationRequest(1L, 2L)
        val attendee = Attendee(1L, "David Salomon", "ldsalomon@puce.edu.ec")
        val event = Event(2L, "Concierto", "Estadio", 10, 0)

        `when`(attendeeRepository.findById(1L)).thenReturn(Optional.of(attendee))
        `when`(eventRepository.findById(2L)).thenReturn(Optional.of(event))

        assertThrows<SoldOutException> { reservationService.createReservation(request) }
    }

    @Test
    fun `createReservation throws ReservationLimitExceededException if attendee has 4 active`() {
        val request = ReservationRequest(1L, 2L)
        val attendee = Attendee(1L, "David Salomon", "ldsalomon@puce.edu.ec")
        val event = Event(2L, "Concierto", "Estadio", 10, 5)

        `when`(attendeeRepository.findById(1L)).thenReturn(Optional.of(attendee))
        `when`(eventRepository.findById(2L)).thenReturn(Optional.of(event))
        `when`(reservationRepository.countByAttendeeIdAndStatus(1L, "ACTIVE")).thenReturn(4)

        assertThrows<ReservationLimitExceededException> { reservationService.createReservation(request) }
    }

    @Test
    fun `cancelReservation cancels reservation when active`() {
        val attendee = Attendee(1L, "Luis Salomon", "luissalomon@puce.edu.ec")
        val event = Event(2L, "Concierto", "Estadio", 10, 5)
        val reservation = Reservation(10L, attendee, event, "ACTIVE", LocalDateTime.now())
        val cancelledReservation = Reservation(10L, attendee, event, "CANCELLED", reservation.createdAt)

        `when`(reservationRepository.findById(10L)).thenReturn(Optional.of(reservation))
        `when`(reservationRepository.save(any(Reservation::class.java))).thenReturn(cancelledReservation)

        val response = reservationService.cancelReservation(10L)

        assertEquals("CANCELLED", response.status)
        verify(eventRepository, times(1)).save(any(Event::class.java))
    }

    @Test
    fun `cancelReservation throws ReservationNotFoundException if absent`() {
        `when`(reservationRepository.findById(10L)).thenReturn(Optional.empty())
        assertThrows<ReservationNotFoundException> { reservationService.cancelReservation(10L) }
    }

    @Test
    fun `cancelReservation throws ReservationAlreadyCancelledException if already cancelled`() {
        val attendee = Attendee(1L, "Luis Salomon", "luissalomon@puce.edu.ec")
        val event = Event(2L, "Concierto", "Estadio", 10, 5)
        val reservation = Reservation(10L, attendee, event, "CANCELLED", LocalDateTime.now())

        `when`(reservationRepository.findById(10L)).thenReturn(Optional.of(reservation))

        assertThrows<ReservationAlreadyCancelledException> { reservationService.cancelReservation(10L) }
    }

    @Test
    fun `getAllReservations returns list`() {
        val attendee = Attendee(1L, "Carlos Andrade", "candrade@puce.edu.ec")
        val event = Event(2L, "Concierto", "Estadio", 10, 5)
        val reservation = Reservation(10L, attendee, event, "ACTIVE", LocalDateTime.now())

        `when`(reservationRepository.findAll()).thenReturn(listOf(reservation))

        val list = reservationService.getAllReservations()
        assertEquals(1, list.size)
    }
}
