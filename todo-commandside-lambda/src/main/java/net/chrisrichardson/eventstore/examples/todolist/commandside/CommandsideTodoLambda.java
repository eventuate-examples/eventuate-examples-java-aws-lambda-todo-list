package net.chrisrichardson.eventstore.examples.todolist.commandside;

import net.chrisrichardson.eventstore.examples.todolist.common.aws.AbstractHttpLambda;
import net.chrisrichardson.eventstore.examples.todolist.common.aws.ApiGatewayRequest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandsideTodoLambda extends AbstractHttpLambda<ApiGatewayRequest> {

  public CommandsideTodoLambda() {
    super(new AnnotationConfigApplicationContext(CommandsideTodoHandlerConfiguration.class));
  }
}
