package net.chrisrichardson.eventstore.examples.todolist.commandside;

import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.db.DatabaseTodoConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.db.TodoDAO;
import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.TodoBackendConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.domain.TodoService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@Import({TodoBackendConfiguration.class,
        EventuateDriverConfiguration.class,
        DatabaseTodoConfiguration.class})
@EnableAutoConfiguration
public class CommandsideTodoHandlerConfiguration {

  @Bean
  public CommandsideTodoHandler commandsideTodoHandler(TodoService todoService, TodoDAO todoDAO) {
    return new CommandsideTodoHandler(todoService, todoDAO);
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    System.setProperty("vertx.disableFileCPResolving", "true");
    return new PropertySourcesPlaceholderConfigurer();
  }
}
