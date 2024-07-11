package com.jacobpitkin.assistant.calendar.models;

import java.util.Collections;
import java.util.List;

public record Event(String title, long startTime, long endTime, String description, List<String> tags) implements Comparable<Event> {
    public Event(String title, long startTime, long endTime, String description) {
        this(title, startTime, endTime, description, Collections.emptyList());
    }

    public Event(String title, long startTime, long endTime, List<String> tags) {
        this(title, startTime, endTime, "", tags);
    }

    public Event(String title, long startTime, String description, List<String> tags) {
        this(title, startTime, 0L, description, tags);
    }

    public Event(String title, long startTime, long endTime) {
        this(title, startTime, endTime, "", Collections.emptyList());
    }

    public Event(String title, long startTime, String description) {
        this(title, startTime, 0L, description, Collections.emptyList());
    }

    public Event(String title, long startTime, List<String> tags) {
        this(title, startTime, 0L, "", tags);
    }

    public Event(String title, long startTime) {
        this(title, startTime, 0L, "", Collections.emptyList());
    }

    @Override
    public int compareTo(Event otherEvent) {
        return Long.compare(startTime(), otherEvent.startTime());
    }
}