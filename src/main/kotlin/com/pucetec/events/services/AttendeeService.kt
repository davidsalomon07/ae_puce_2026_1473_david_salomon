package com.pucetec.events.services

import com.pucetec.events.dto.AttendeeRequest
import com.pucetec.events.dto.AttendeeResponse
import com.pucetec.events.exceptions.BlankFieldException
import com.pucetec.events.mappers.toEntity
import com.pucetec.events.mappers.toResponse
import com.pucetec.events.repositories.AttendeeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AttendeeService(
    private val attendeeRepository: AttendeeRepository
) {
    fun createAttendee(request: AttendeeRequest): AttendeeResponse {
        if (request.name.isBlank() || request.email.isBlank()) {
            throw BlankFieldException("Name or email cannot be blank")
        }
        val saved = attendeeRepository.save(request.toEntity())
        return saved.toResponse()
    }
}