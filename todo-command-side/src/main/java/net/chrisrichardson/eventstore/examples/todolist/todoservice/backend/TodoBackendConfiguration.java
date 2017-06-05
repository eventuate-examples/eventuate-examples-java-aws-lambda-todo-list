package net.chrisrichardson.eventstore.examples.todolist.todoservice.backend;

import io.eventuate.sync.AggregateRepository;
import io.eventuate.sync.EventuateAggregateStore;
import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.command.TodoCommand;
import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.domain.TodoAggregate;
import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.domain.TodoBulkDeleteAggregate;
import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.domain.TodoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TodoBackendConfiguration {

  @Bean
  public AggregateRepository<TodoAggregate, TodoCommand> aggregateRepository(EventuateAggregateStore eventStore) {
    return new AggregateRepository<>(TodoAggregate.class, eventStore);
  }

  @Bean
  public AggregateRepository<TodoBulkDeleteAggregate, TodoCommand> bulkDeleteAggregateRepository(EventuateAggregateStore eventStore) {
    return new AggregateRepository<>(TodoBulkDeleteAggregate.class, eventStore);
  }

  @Bean
  public TodoService updateService(AggregateRepository<TodoAggregate, TodoCommand> aggregateRepository, AggregateRepository<TodoBulkDeleteAggregate, TodoCommand> bulkDeleteAggregateRepository) {
    return new TodoService(aggregateRepository, bulkDeleteAggregateRepository);
  }
}


