package com.jacobpitkin.assistant.calendar;

import java.time.Clock;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.jacobpitkin.assistant.calendar.models.Event;
import com.jacobpitkin.assistant.discord.DiscordService;

@Service
public class CalendarService {
    private final DiscordService discord;
    private final Clock clock;
    private TreeMap<Long, Event> events;

    private final long ONE_MONTH_MILLIS = 1000 * 60 * 60 * 24 * 30L;

    public CalendarService(DiscordService discord, Clock clock) {
        this.discord = discord;
        this.clock = clock;
        events = new TreeMap<>();
    }

    public synchronized void addEvent(Event event) {
        long startTime = event.startTime();
        events.put(startTime, event);
    }

    public TreeMap<Long, Event> getEvents() {
        TreeMap<Long, Event> ret = new TreeMap<>(events);
        return ret;
    }

    public void clearOldEvents() {
        while (!events.isEmpty() && events.firstKey() < clock.millis() - ONE_MONTH_MILLIS) {
            events.pollFirstEntry();
        }
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
