package net.chrisrichardson.eventstore.examples.todolist.model;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Todo {

  private String id;
  private String title;
  private Boolean completed;
  private Integer order;

  public Todo() {
  }

  public Todo(String id, TodoInfo todoInfo) {
    this.id = id;
    this.title = todoInfo.getTitle();
    this.completed = todoInfo.isCompleted();
    this.order = todoInfo.getOrder();
  }

  public Todo(String title) {
    this.title = title;
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

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  private <T> T nonNull(T value, T defaultValue) {
    return value == null ? defaultValue : value;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
