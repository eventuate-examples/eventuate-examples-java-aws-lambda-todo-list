package net.chrisrichardson.eventstore.examples.todolist.queryside;

import net.chrisrichardson.eventstore.examples.todolist.common.aws.AbstractHttpLambda;
import net.chrisrichardson.eventstore.examples.todolist.eventhandler.common.EventsRequest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class QuerysideTodoLambda extends AbstractHttpLambda<EventsRequest> {

  public QuerysideTodoLambda() {
    super(new AnnotationConfigApplicationContext(QuerysideTodoHandlerConfiguration.class));
  }
}
