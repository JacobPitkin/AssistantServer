package com.jacobpitkin.assistant.calendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jacobpitkin.assistant.calendar.models.Event;

public class CalendarServiceTests {
    
    @Test
    public void testAddEvent() {
        Clock clock = mock(Clock.class);
        when(clock.millis()).thenReturn(0L);
        CalendarService calendarService = new CalendarService(clock);

        Event event1 = new Event("event1", 6L);
        Event event2 = new Event("event2", 1L);
        Event event3 = new Event("event3", 2L);
        Event event4 = new Event("event4", 4L);
        Event event5 = new Event("event5", 3L);
        Event event6 = new Event("event6", 5L);

        calendarService.addEvent(event1);
        calendarService.addEvent(event2);
        calendarService.addEvent(event3);
        calendarService.addEvent(event4);
        calendarService.addEvent(event5);
        calendarService.addEvent(event6);

        TreeMap<Long, Event> events = calendarService.getUpcomingEvents();
        TreeMap<Long, Event> pastEvents = calendarService.getPastEvents();
        Assertions.assertEquals(0, pastEvents.size());
        Assertions.assertEquals("event2", events.firstEntry().getValue().title());

        long counter = 1L;
        for (Entry<Long, Event> entry : events.sequencedEntrySet()) {
            Assertions.assertEquals(counter++, entry.getKey());
        }
    }

    // @Test
    // public void testClearOldEvents() {
    //     Clock clock = mock(Clock.class);
    //     CalendarService calendarService = new CalendarService(clock);

    //     long ONE_MONTH_MILLIS = 1000 * 60 * 60 * 24 * 30L;

    //     Event event1 = new Event("event1", ONE_MONTH_MILLIS * 2L);
    //     Event event2 = new Event("event2", ONE_MONTH_MILLIS);
    //     Event event3 = new Event("event3", ONE_MONTH_MILLIS * 3L);

    //     when(clock.millis()).thenReturn(ONE_MONTH_MILLIS * 2L + 5L);
        
    //     calendarService.addEvent(event1);
    //     calendarService.addEvent(event2);
    //     calendarService.addEvent(event3);

    //     calendarService.clearOldEvents();

    //     TreeMap<Long, Event> events = calendarService.getUpcomingEvents();

    //     Assertions.assertEquals(2, events.size());
    // }
}
