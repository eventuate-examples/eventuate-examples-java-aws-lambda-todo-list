package net.chrisrichardson.eventstore.examples.todolist.findlambda;

import net.chrisrichardson.eventstore.examples.todolist.common.aws.AbstractHttpLambda;
import net.chrisrichardson.eventstore.examples.todolist.common.aws.ApiGatewayRequest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FindTodoLambda extends AbstractHttpLambda<ApiGatewayRequest> {
  public FindTodoLambda() {
    super(new AnnotationConfigApplicationContext(FindTodoHandlerConfiguration.class));
  }
}
