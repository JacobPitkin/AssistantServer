package com.jacobpitkin.assistant.calendar;

import org.springframework.stereotype.Service;

import com.jacobpitkin.assistant.discord.DiscordService;

@Service
public class CalendarService {
    private final DiscordService discord;

    public CalendarService(DiscordService discord) {
        this.discord = discord;
    }

    public String getFromDiscord() {
        return discord.getMessage();
    }

    public String getFromService() {
        return "This message is from the calendar service.";
    }

    public String postDisordMessage() {
        return discord.postMessage();
    }
}
