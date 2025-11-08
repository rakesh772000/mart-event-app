package com.smartevent.event_service.service;
import com.smartevent.event_service.dto.EventRequestDto;
import com.smartevent.event_service.dto.EventResponseDto;
import com.smartevent.event_service.entity.Event;
import com.smartevent.event_service.exception.ResourceNotFoundException;
import com.smartevent.event_service.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventResponseDto createEvent(EventRequestDto dto) {
        logger.info("Creating new event: {}", dto.getTitle());

        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setEventDate(dto.getEventDate());
        event.setOrganizerName(dto.getOrganizerName());

        Event savedEvent = eventRepository.save(event);
        logger.debug("Event saved with ID: {}", savedEvent.getId());

        return mapToDto(savedEvent);
    }

    @Override
    public List<EventResponseDto> getAllEvents() {
        logger.info("Fetching all events");
        return eventRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponseDto getEventById(Long id) {
        logger.info("Fetching event by ID: {}", id);
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
        return mapToDto(event);
    }

    @Override
    public EventResponseDto updateEvent(Long id, EventRequestDto dto) {
        logger.info("Updating event ID: {}", id);
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setEventDate(dto.getEventDate());
        event.setOrganizerName(dto.getOrganizerName());

        Event updatedEvent = eventRepository.save(event);
        logger.debug("Event updated with ID: {}", updatedEvent.getId());

        return mapToDto(updatedEvent);
    }

    @Override
    public void deleteEvent(Long id) {
        logger.info("Deleting event ID: {}", id);
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
        eventRepository.delete(event);
        logger.debug("Event deleted with ID: {}", id);
    }

    // Mapping Helper
    private EventResponseDto mapToDto(Event event) {
        EventResponseDto dto = new EventResponseDto();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setLocation(event.getLocation());
        dto.setEventDate(event.getEventDate());
        dto.setOrganizerName(event.getOrganizerName());
        return dto;
    }
}

