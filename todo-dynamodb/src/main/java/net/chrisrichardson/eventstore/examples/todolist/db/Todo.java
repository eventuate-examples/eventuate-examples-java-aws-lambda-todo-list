package net.chrisrichardson.eventstore.examples.todolist.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import net.chrisrichardson.eventstore.examples.todolist.model.TodoInfo;
import org.apache.commons.lang.builder.EqualsBuilder;

@DynamoDBTable(tableName = "todo_records")
public class Todo {
  @DynamoDBHashKey
  private String id;
  @DynamoDBAttribute
  private String title;
  @DynamoDBAttribute
  private Boolean completed;
  @DynamoDBAttribute
  private Integer order;

  public Todo() {
  }

  public Todo(String id) {
    this.id = id;
  }


  public Todo(TodoInfo todoInfo) {
    this.title = todoInfo.getTitle();
    this.completed = todoInfo.isCompleted();
    this.order = todoInfo.getOrder();
  }

  public Todo(String id, String title, Boolean completed, Integer order) {
    this.id = id;
    this.title = title;
    this.completed = completed;
    this.order = order;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean isCompleted() {
    return nonNull(completed, false);
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  public Integer getOrder() {
    return nonNull(order, 0);
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  public Todo merge(Todo newTodo) {
    return new Todo(id,
            nonNull(newTodo.title, title),
            nonNull(newTodo.completed, completed),
            nonNull(newTodo.order, order));
  }

  private <T> T nonNull(T value, T defaultValue) {
    return value == null ? defaultValue : value;
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public String toString() {
    return "Todo{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", completed=" + completed +
            ", order=" + order +
            '}';
  }


}
