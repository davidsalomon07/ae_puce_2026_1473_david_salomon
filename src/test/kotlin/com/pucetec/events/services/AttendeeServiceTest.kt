package com.pucetec.events.services

import com.pucetec.events.dto.AttendeeRequest
import com.pucetec.events.entities.Attendee
import com.pucetec.events.exceptions.BlankFieldException
import com.pucetec.events.repositories.AttendeeRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class AttendeeServiceTest {

    @Mock
    lateinit var attendeeRepository: AttendeeRepository

    @InjectMocks
    lateinit var attendeeService: AttendeeService

    @Test
    fun `createAttendee returns response when valid`() {
        val request = AttendeeRequest("David Salomon", "ldsalomon@puce.edu.ec")
        val entity = Attendee(1L, "David Salomon", "ldsalomon@puce.edu.ec")

        `when`(attendeeRepository.save(any(Attendee::class.java))).thenReturn(entity)

        val response = attendeeService.createAttendee(request)

        assertEquals(1L, response.id)
        assertEquals("David Salomon", response.name)
        assertEquals("ldsalomon@puce.edu.ec", response.email)
    }

    @Test
    fun `createAttendee throws BlankFieldException when name is blank`() {
        val request = AttendeeRequest("", "ldsalomon@puce.edu.ec")
        assertThrows<BlankFieldException> { attendeeService.createAttendee(request) }
    }
}

// HOLA //