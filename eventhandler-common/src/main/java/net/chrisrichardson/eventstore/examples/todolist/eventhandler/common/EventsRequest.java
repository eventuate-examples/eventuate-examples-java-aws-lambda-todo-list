package net.chrisrichardson.eventstore.examples.todolist.eventhandler.common;

import java.util.List;

public class EventsRequest {
  private List<EventRecord> events;

  public EventsRequest() {
  }

  public EventsRequest(List<EventRecord> events) {
    this.events = events;
  }

  public List<EventRecord> getEvents() {
    return events;
  }

  public void setEvents(List<EventRecord> events) {
    this.events = events;
  }
}
