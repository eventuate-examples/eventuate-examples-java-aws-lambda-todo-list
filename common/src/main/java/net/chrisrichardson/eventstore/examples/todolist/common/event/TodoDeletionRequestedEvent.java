package net.chrisrichardson.eventstore.examples.todolist.common.event;


import io.eventuate.Event;

public class TodoDeletionRequestedEvent implements Event {

  private String todoId;

  public TodoDeletionRequestedEvent(String todoId) {
    this.todoId = todoId;
  }

  public TodoDeletionRequestedEvent() {

  }

  public String getTodoId() {
    return todoId;
  }
}
