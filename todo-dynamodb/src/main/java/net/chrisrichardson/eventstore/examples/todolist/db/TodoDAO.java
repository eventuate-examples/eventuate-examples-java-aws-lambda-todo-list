package net.chrisrichardson.eventstore.examples.todolist.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TodoDAO {

  private DynamoDBMapper mapper;

  public TodoDAO(DynamoDBMapper mapper) {
    this.mapper = mapper;
  }

  public Optional<Todo> findOne(String todoId) {
    return Optional.ofNullable(mapper.load(Todo.class, todoId));
  }

  public List<Todo> getAll() {
    return mapper.scan(Todo.class, new DynamoDBScanExpression());
  }

  public void save(Todo todo) {
    mapper.save(todo);
  }

  public void remove(String todoId) {
    mapper.delete(new Todo(todoId));
  }

  public void removeAll(List<String> todoIds) {
    mapper.batchDelete(todoIds.stream().map(Todo::new).collect(Collectors.toList()));
  }
}
