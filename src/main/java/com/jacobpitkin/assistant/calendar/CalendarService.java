package com.jacobpitkin.assistant.calendar;

import java.time.Clock;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.jacobpitkin.assistant.calendar.models.Event;

@Service
public class CalendarService {
    private final Clock clock;
    private TreeMap<Long, Event> events;

    private final long ONE_MONTH_MILLIS = 1000 * 60 * 60 * 24 * 30L;

    public CalendarService(Clock clock) {
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

    public Event removeEvent(long id) {
        return events.remove(id);
    }

    public Event removeEvent(Event event) {
        return events.remove(event.startTime());
    }

    public void clearOldEvents() {
        while (!events.isEmpty() && events.firstKey() < clock.millis() - ONE_MONTH_MILLIS) {
            events.pollFirstEntry();
        }
    }

    public String getMessage() {
        return "Message from CalendarService";
    }
}
