package com.pucetec.events.services

import com.pucetec.events.dto.EventRequest
import com.pucetec.events.dto.EventResponse
import com.pucetec.events.exceptions.BlankFieldException
import com.pucetec.events.exceptions.EventNotFoundException
import com.pucetec.events.exceptions.InvalidCapacityException
import com.pucetec.events.mappers.toEntity
import com.pucetec.events.mappers.toResponse
import com.pucetec.events.repositories.EventRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class EventService(
    private val eventRepository: EventRepository
) {
    fun createEvent(request: EventRequest): EventResponse {
        if (request.name.isBlank() || request.venue.isBlank()) {
            throw BlankFieldException("Name or venue cannot be blank")
        }
        if (request.totalTickets < 1) {
            throw InvalidCapacityException("Total tickets capacity must be at least 1")
        }
        val event = request.toEntity()
        val saved = eventRepository.save(event)
        return saved.toResponse()
    }

    @Transactional(readOnly = true)
    fun getAllEvents(): List<EventResponse> {
        return eventRepository.findAll().map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun getEventById(id: Long): EventResponse {
        val event = eventRepository.findById(id)
            .orElseThrow { EventNotFoundException("Event not found with ID: $id") }
        return event.toResponse()
    }
}