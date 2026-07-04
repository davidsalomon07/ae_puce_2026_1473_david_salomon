package com.pucetec.events.services

import com.pucetec.events.dto.EventRequest
import com.pucetec.events.entities.Event
import com.pucetec.events.exceptions.BlankFieldException
import com.pucetec.events.exceptions.EventNotFoundException
import com.pucetec.events.exceptions.InvalidCapacityException
import com.pucetec.events.repositories.EventRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class EventServiceTest {

    @Mock
    lateinit var eventRepository: EventRepository

    @InjectMocks
    lateinit var eventService: EventService

    @Test
    fun `createEvent returns response when valid`() {
        val request = EventRequest("Concierto Rock", "Estadio", 100)
        val entity = Event(1L, "Concierto Rock", "Estadio", 100, 100)

        `when`(eventRepository.save(any(Event::class.java))).thenReturn(entity)

        val response = eventService.createEvent(request)

        assertEquals(1L, response.id)
        assertEquals("Concierto Rock", response.name)
        assertEquals("Estadio", response.venue)
        assertEquals(100, response.totalTickets)
        assertEquals(100, response.availableTickets)
    }

    @Test
    fun `createEvent throws BlankFieldException when name is blank`() {
        val request = EventRequest("  ", "Estadio", 100)
        assertThrows<BlankFieldException> { eventService.createEvent(request) }
    }

    @Test
    fun `createEvent throws InvalidCapacityException when capacity is 0`() {
        val request = EventRequest("Rock", "Estadio", 0)
        assertThrows<InvalidCapacityException> { eventService.createEvent(request) }
    }

    @Test
    fun `getAllEvents returns list`() {
        val entity = Event(1L, "Concierto Rock", "Estadio", 100, 100)
        `when`(eventRepository.findAll()).thenReturn(listOf(entity))

        val list = eventService.getAllEvents()
        assertEquals(1, list.size)
    }

    @Test
    fun `getEventById returns response when found`() {
        val entity = Event(1L, "Concierto Rock", "Estadio", 100, 100)
        `when`(eventRepository.findById(1L)).thenReturn(Optional.of(entity))

        val response = eventService.getEventById(1L)
        assertEquals(1L, response.id)
    }

    @Test
    fun `getEventById throws EventNotFoundException when not found`() {
        `when`(eventRepository.findById(1L)).thenReturn(Optional.empty())
        assertThrows<EventNotFoundException> { eventService.getEventById(1L) }
    }
}
