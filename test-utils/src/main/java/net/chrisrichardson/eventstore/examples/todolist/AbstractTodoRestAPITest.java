package net.chrisrichardson.eventstore.examples.todolist;


import net.chrisrichardson.eventstore.examples.todolist.model.Todo;
import net.chrisrichardson.eventstore.examples.todolist.model.TodoInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static net.chrisrichardson.eventstore.examples.todolist.testutil.TestUtil.awaitNotFoundResponse;
import static net.chrisrichardson.eventstore.examples.todolist.testutil.TestUtil.awaitSuccessfulRequest;


@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractTodoRestAPITest {

  private String baseUrl(String path) {
    return getHost() + "/" + path;
  }

  @Autowired
  private RestTemplate restTemplate;


  private Todo awaitCreationInView(String todoId) {
    return awaitSuccessfulRequest(() -> getTodo(todoId));
  }

  private ResponseEntity<Todo> createTodo(TodoInfo todoToSave) {
    ResponseEntity<Todo> postResponse = restTemplate.postForEntity(baseUrl("todos"), todoToSave, Todo.class);
    Assert.assertEquals(HttpStatus.OK, postResponse.getStatusCode());
    return postResponse;
  }


  private ResponseEntity<Todo> getTodo(String todoId) {
    return restTemplate.getForEntity(baseUrl("todos/" + todoId), Todo.class);
  }

  private ResponseEntity<Todo[]> getTodos() {
    return restTemplate.getForEntity(baseUrl("todos"), Todo[].class);
  }

  private void assertTodoEquals(Todo expectedTodo, Todo todo) {
    Assert.assertEquals(expectedTodo.getTitle(), todo.getTitle());
    Assert.assertEquals(expectedTodo.getOrder(), todo.getOrder());
    Assert.assertEquals(expectedTodo.isCompleted(), todo.isCompleted());
  }

  private void assertTodoContains(Todo expectedTodo, List<Todo> todoList) {
    Assert.assertTrue(todoList.contains(expectedTodo));
  }

  private Todo makeExpectedTodo(String todoId, TodoInfo todo) {
    Todo todoWithUrl = new Todo();
    todoWithUrl.setCompleted(todo.isCompleted());
    todoWithUrl.setOrder(todo.getOrder());
    todoWithUrl.setTitle(todo.getTitle());
    todoWithUrl.setId(todoId);
    return todoWithUrl;
  }

  private ResponseEntity<Todo> updateTodo(String todoId, TodoInfo put) {
    ResponseEntity<Todo> putResult = restTemplate.exchange(baseUrl("todos/" + todoId), HttpMethod.PUT, new HttpEntity<>(put),
            Todo.class);
    Assert.assertEquals(HttpStatus.OK, putResult.getStatusCode());
    return putResult;
  }

  private Todo createAndWaitForView(TodoInfo todoToSave) {
    ResponseEntity<Todo> postResponse = createTodo(todoToSave);

    String todoId = postResponse.getBody().getId();

    return awaitCreationInView(todoId);
  }

  @Test
  public void shouldSetCORSHeaders() {
    ResponseEntity<Void> responseEntity = restTemplate.exchange(baseUrl("todos"), HttpMethod.OPTIONS, new HttpEntity<>(""), Void.class);

    Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assert.assertFalse(responseEntity.getHeaders().get("Access-Control-Allow-Origin").isEmpty());
    Assert.assertEquals("*", responseEntity.getHeaders().get("Access-Control-Allow-Origin").get(0));
    Assert.assertFalse(responseEntity.getHeaders().get("Access-Control-Allow-Methods").isEmpty());
    Assert.assertEquals("OPTIONS,GET", responseEntity.getHeaders().get("Access-Control-Allow-Methods").get(0));
    Assert.assertFalse(responseEntity.getHeaders().get("Access-Control-Allow-Headers").isEmpty());
    Assert.assertEquals("Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token", responseEntity.getHeaders().get("Access-Control-Allow-Headers").get(0));
  }

  @Test
  public void shouldShowAllTodos() {
    TodoInfo todoToSave = new TodoInfo("1st todo");

    assertTodoContains(createAndWaitForView(todoToSave),
            Arrays.asList(getTodos().getBody())
    );
  }

  @Test
  public void shouldDeleteSingleTodo() throws InterruptedException {
    TodoInfo todoToSave = new TodoInfo("a todo");
    Todo todo = createAndWaitForView(todoToSave);

    restTemplate.delete(baseUrl("todos/" + todo.getId()));

    awaitNotFoundResponse(idx -> getTodo(todo.getId()));
  }

  @Test
  public void shouldCreateNewTodo() throws InterruptedException {
    TodoInfo todoToSave = new TodoInfo("walk the dog");
    Todo todoView = createAndWaitForView(todoToSave);

    Todo expectedTodo = makeExpectedTodo(todoView.getId(), todoToSave);

    assertTodoEquals(expectedTodo, todoView);
  }

    @Test
    public void shouldUpdateTodo() throws InterruptedException {

        TodoInfo todoToSave = new TodoInfo("todo 1");
        String todoId = createAndWaitForView(todoToSave).getId();

        TodoInfo todoWithChanges = new TodoInfo();
        todoWithChanges.setTitle("todo 2");
        todoWithChanges.setCompleted(true);
        todoWithChanges.setOrder(42);

        ResponseEntity<Todo> putResult = updateTodo(todoId, todoWithChanges);

      Todo expectedTodo = makeExpectedTodo(todoId, todoWithChanges);

      Todo updatedTodo = putResult.getBody();
        assertTodoEquals(expectedTodo, updatedTodo);

      Todo updatedTodoInView = awaitSuccessfulRequest(
                () -> getTodo(todoId),
                re -> re.getTitle().equals(todoWithChanges.getTitle())
        );

        assertTodoEquals(expectedTodo, updatedTodoInView);

    }

  protected abstract String getHost();
}

