package net.chrisrichardson.eventstore.examples.todolist.commandside;

import com.amazonaws.services.lambda.runtime.Context;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.javaclient.commonimpl.JSonMapper;
import net.chrisrichardson.eventstore.examples.todolist.common.aws.AbstractAWSTodoHandler;
import net.chrisrichardson.eventstore.examples.todolist.common.aws.ApiGatewayRequest;
import net.chrisrichardson.eventstore.examples.todolist.common.aws.ApiGatewayResponse;
import net.chrisrichardson.eventstore.examples.todolist.db.TodoDAO;
import net.chrisrichardson.eventstore.examples.todolist.model.Todo;
import net.chrisrichardson.eventstore.examples.todolist.model.TodoInfo;
import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.domain.TodoAggregate;
import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.domain.TodoService;

import java.util.stream.Collectors;

import static net.chrisrichardson.eventstore.examples.todolist.common.aws.ApiGatewayResponse.applicationJsonHeaders;
import static net.chrisrichardson.eventstore.examples.todolist.common.aws.ApiGatewayResponse.build405ErrorResponse;

public class CommandsideTodoHandler extends AbstractAWSTodoHandler<ApiGatewayRequest> {

  private TodoService todoService;
  private TodoDAO todoDAO;

  public CommandsideTodoHandler(TodoService todoService, TodoDAO todoDAO) {
    this.todoService = todoService;
    this.todoDAO = todoDAO;
  }

  @Override
  protected ApiGatewayResponse handleAWSRequest(ApiGatewayRequest request, Context context) {
    Object response = null;

    switch (request.getHttpMethod()) {
      case "POST": {
        response = handleTodoCreate(request);
        break;
      }
      case "PUT": {
        response = handlerTodoUpdate(request);
        break;
      }
      case "DELETE": {
        response = handlerTodoDelete(request);
        break;
      }
      default: {
        build405ErrorResponse();
      }
    }

    return ApiGatewayResponse.builder()
            .setStatusCode(200)
            .setObjectBody(response)
            .setHeaders(applicationJsonHeaders())
            .build();
  }

  private Object handleTodoCreate(ApiGatewayRequest input) {
    TodoInfo todoInfo = JSonMapper.fromJson(input.getBody(), TodoInfo.class);
    EntityWithIdAndVersion<TodoAggregate> entityWithIdAndVersion = todoService.save(todoInfo);
    return new Todo(entityWithIdAndVersion.getEntityId(),
            entityWithIdAndVersion.getAggregate().getTodo());
  }

  private Object handlerTodoUpdate(ApiGatewayRequest input) {
    String todoId = input.getPathParameters().get("todoId");
    TodoInfo todoInfo = JSonMapper.fromJson(input.getBody(), TodoInfo.class);

    EntityWithIdAndVersion<TodoAggregate> entityWithIdAndVersion = todoService.update(todoId, todoInfo);
    return new Todo(todoId,
            entityWithIdAndVersion.getAggregate().getTodo());
  }

  private Object handlerTodoDelete(ApiGatewayRequest input) {
    String todoId = input.getPathParameters().get("todoId");

    if (todoId != null) {
      EntityWithIdAndVersion<TodoAggregate> entityWithIdAndVersion = todoService.remove(todoId);
      return new Todo(todoId,
              entityWithIdAndVersion.getAggregate().getTodo());
    } else {
      todoService.deleteAll(todoDAO.getAll()
              .stream().map(net.chrisrichardson.eventstore.examples.todolist.db.Todo::getId)
              .collect(Collectors.toList())
      );
      return "All todo records were successfully deleted";
    }
  }
}
