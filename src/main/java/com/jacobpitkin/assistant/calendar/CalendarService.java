package com.jacobpitkin.assistant.calendar;

import java.time.Clock;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jacobpitkin.assistant.calendar.models.Event;

@Service
public class CalendarService {
    private final static Logger LOG = LogManager.getLogger();
    private final Clock clock;
    private TreeMap<Long, Event> upcomingEvents;
    private TreeMap<Long, Event> pastEvents;

    public CalendarService(Clock clock) {
        this.clock = clock;
        upcomingEvents = new TreeMap<>();
        pastEvents = new TreeMap<>(Collections.reverseOrder());
    }

    public synchronized void addEvent(Event event) {
        long startTime = event.startTime();
        long now = clock.millis();

        if (startTime > now) {
            upcomingEvents.put(startTime, event);
        } else {
            pastEvents.put(startTime, event);
        }
    }

    public synchronized TreeMap<Long, Event> getUpcomingEvents() {
        TreeMap<Long, Event> ret = new TreeMap<>(upcomingEvents);
        return ret;
    }

    public synchronized Event removeEvent(long id) {
        long now = clock.millis();

        if (id < now) {
            return pastEvents.remove(id);
        }

        return upcomingEvents.remove(id);
    }

    public synchronized Event removeEvent(Event event) {
        long startTime = event.startTime();
        return removeEvent(startTime);
    }

    public void updateEvent(long id, Event event) {
        long now = clock.millis();
        long updatedStartTime = event.startTime();
        
        if (id == updatedStartTime) {
            if (id < now) {
                pastEvents.put(id, event);
            } else {
                upcomingEvents.put(id, event);
            }
        } else {
            pastEvents.remove(id);
            upcomingEvents.remove(id);

            if (updatedStartTime < now) {
                pastEvents.put(updatedStartTime, event);
            } else {
                upcomingEvents.put(updatedStartTime, event);
            }
        }
    }

    private synchronized void clearOldEvents() {
        long now = clock.millis();

        while (upcomingEvents.firstKey() < now) {
            Entry<Long, Event> entry = upcomingEvents.firstEntry();
            upcomingEvents.remove(entry.getKey());
            pastEvents.put(entry.getKey(), entry.getValue());
        }
    }

    public String getMessage() {
        return "Message from CalendarService";
    }

    @Scheduled(cron = "* * * * *")
    public void pulse() {
        LOG.info("[Calendar] pulse");
    }
}
