package com.jacobpitkin.assistant.calendar;

import java.util.TreeMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacobpitkin.assistant.calendar.models.Event;

@RestController
@RequestMapping("/calendar")
public class CalendarController {
    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }
    
    @GetMapping
    public String index() {
        return "This is the calendar endpoint.";
    }

    @GetMapping("/events")
    public TreeMap<Long, Event> getEvents() {
        return calendarService.getEvents();
    }
}
