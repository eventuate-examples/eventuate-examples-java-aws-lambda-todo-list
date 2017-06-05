package net.chrisrichardson.eventstore.examples.todolist.eventhandler.common;

import org.apache.commons.lang.builder.ToStringBuilder;

public class EventRecord {
  private String entityId;
  private String eventId;
  private String eventType;
  private String eventData;
  private String entityType;
  private String space;
  private String userId;

  public EventRecord() {
  }

  public EventRecord(String entityId, String eventId, String eventType, String eventData, String entityType, String space, String userId) {
    this.entityId = entityId;
    this.eventId = eventId;
    this.eventType = eventType;
    this.eventData = eventData;
    this.entityType = entityType;
    this.space = space;
    this.userId = userId;
  }

  public String getEntityId() {
    return entityId;
  }

  public String getEventId() {
    return eventId;
  }

  public String getEventType() {
    return eventType;
  }

  public String getEventData() {
    return eventData;
  }

  public String getEntityType() {
    return entityType;
  }

  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

  public String getSpace() {
    return space;
  }

  public void setSpace(String space) {
    this.space = space;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public void setEventData(String eventData) {
    this.eventData = eventData;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public boolean isEventType(Class clasz) {
    return clasz.getName().equals(eventType);
  }
}
