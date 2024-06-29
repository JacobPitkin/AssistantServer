package com.jacobpitkin.assistant.calendar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("calendar")
public class CalendarController {
    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }
    
    @GetMapping("/")
    public String index() {
        return "This is the calendar endpoint.";
    }

    @GetMapping("/discord")
    public String getDiscordMessage() {
        return calendarService.getFromDiscord();
    }

    @GetMapping("/discord/post")
    public String postDiscordMessage() {
        return calendarService.postDisordMessage();
    }

    @GetMapping("/service")
    public String getService() {
        return calendarService.getFromService();
    }
}
