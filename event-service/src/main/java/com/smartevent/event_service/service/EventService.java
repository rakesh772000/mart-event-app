package com.smartevent.event_service.service;
import com.smartevent.event_service.dto.EventRequestDto;
import com.smartevent.event_service.dto.EventResponseDto;

import java.util.List;

public interface EventService {

    EventResponseDto createEvent(EventRequestDto dto);

    List<EventResponseDto> getAllEvents();

    EventResponseDto getEventById(Long id);

    EventResponseDto updateEvent(Long id, EventRequestDto dto);

    void deleteEvent(Long id);
}

