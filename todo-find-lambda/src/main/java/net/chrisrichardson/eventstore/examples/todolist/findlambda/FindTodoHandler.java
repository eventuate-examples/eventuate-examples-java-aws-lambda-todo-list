package net.chrisrichardson.eventstore.examples.todolist.findlambda;

import com.amazonaws.services.lambda.runtime.Context;
import net.chrisrichardson.eventstore.examples.todolist.common.aws.AbstractAWSTodoHandler;
import net.chrisrichardson.eventstore.examples.todolist.common.aws.ApiGatewayRequest;
import net.chrisrichardson.eventstore.examples.todolist.common.aws.ApiGatewayResponse;
import net.chrisrichardson.eventstore.examples.todolist.db.Todo;
import net.chrisrichardson.eventstore.examples.todolist.db.TodoDAO;

import java.util.Optional;

import static net.chrisrichardson.eventstore.examples.todolist.common.aws.ApiGatewayResponse.applicationJsonHeaders;
import static net.chrisrichardson.eventstore.examples.todolist.common.aws.ApiGatewayResponse.build404ErrorResponse;

public class FindTodoHandler extends AbstractAWSTodoHandler<ApiGatewayRequest> {

  private TodoDAO todoDAO;

  public FindTodoHandler(TodoDAO todoDAO) {
    this.todoDAO = todoDAO;
  }

  @Override
  protected ApiGatewayResponse handleAWSRequest(ApiGatewayRequest request, Context context) {
    String todoId = Optional.ofNullable(request.getPathParameters())
            .map(paramsMap -> paramsMap.get("todoId"))
            .orElse(null);

    ApiGatewayResponse.Builder responseBuilder = ApiGatewayResponse.builder()
            .setStatusCode(200)
            .setHeaders(applicationJsonHeaders());
    if (todoId != null) {
      Optional<Todo> todo = todoDAO.findOne(todoId);
      if (todo.isPresent()) {
        responseBuilder.setObjectBody(todo.get());
      } else {
        return build404ErrorResponse();
      }
    } else {
      responseBuilder.setObjectBody(todoDAO.getAll());
    }
    return responseBuilder.build();
  }
}
