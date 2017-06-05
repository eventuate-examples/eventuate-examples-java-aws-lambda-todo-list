package net.chrisrichardson.eventstore.examples.todolist.queryside;

import net.chrisrichardson.eventstore.examples.todolist.db.DatabaseTodoConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.db.TodoDAO;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DatabaseTodoConfiguration.class})
@EnableAutoConfiguration
public class QuerysideTodoHandlerConfiguration {

  @Bean
  public QuerysideTodoHandler querysideTodoHandler(TodoDAO todoDAO) {
    return new QuerysideTodoHandler(todoDAO);
  }
}
