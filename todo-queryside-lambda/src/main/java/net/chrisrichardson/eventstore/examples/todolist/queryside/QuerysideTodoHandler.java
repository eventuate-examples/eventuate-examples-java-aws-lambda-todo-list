package net.chrisrichardson.eventstore.examples.todolist.queryside;

import com.amazonaws.services.lambda.runtime.Context;
import io.eventuate.javaclient.commonimpl.JSonMapper;
import net.chrisrichardson.eventstore.examples.todolist.common.aws.AbstractAWSTodoHandler;
import net.chrisrichardson.eventstore.examples.todolist.common.aws.ApiGatewayResponse;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoCreatedEvent;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoDeletedEvent;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoUpdatedEvent;
import net.chrisrichardson.eventstore.examples.todolist.db.Todo;
import net.chrisrichardson.eventstore.examples.todolist.db.TodoDAO;
import net.chrisrichardson.eventstore.examples.todolist.eventhandler.common.EventsRequest;

import static net.chrisrichardson.eventstore.examples.todolist.common.aws.ApiGatewayResponse.applicationJsonHeaders;

public class QuerysideTodoHandler extends AbstractAWSTodoHandler<EventsRequest> {

  private TodoDAO todoDAO;

  public QuerysideTodoHandler(TodoDAO todoDAO) {
    this.todoDAO = todoDAO;
  }

  @Override
  protected ApiGatewayResponse handleAWSRequest(EventsRequest input, Context context) {
    input.getEvents().forEach(eventRecord -> {
      String entityId = eventRecord.getEntityId();
      if (eventRecord.isEventType(TodoCreatedEvent.class)) {
        TodoCreatedEvent event = JSonMapper.fromJson(eventRecord.getEventData(), TodoCreatedEvent.class);
        handleCreateEvent(event, entityId);
      } else if (eventRecord.isEventType(TodoUpdatedEvent.class)) {
        TodoUpdatedEvent event = JSonMapper.fromJson(eventRecord.getEventData(), TodoUpdatedEvent.class);
        handleUpdateEvent(event, entityId);
      } else if (eventRecord.isEventType(TodoDeletedEvent.class)) {
        handleDeleteEvent(entityId);
      }
    });
    return ApiGatewayResponse.builder()
            .setObjectBody("Event had been successfully processed")
            .setHeaders(applicationJsonHeaders())
            .build();
  }

  private void handleCreateEvent(TodoCreatedEvent event, String entityId) {
    Todo todo = new Todo(event.getTodo());
    todo.setId(entityId);
    todoDAO.save(todo);
  }

  private void handleUpdateEvent(TodoUpdatedEvent event, String entityId) {
    Todo todo = new Todo(event.getTodo());
    todo.setId(entityId);
    todoDAO.save(todo);
  }

  private void handleDeleteEvent(String entityId) {
    todoDAO.remove(entityId);
  }
}
