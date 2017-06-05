package net.chrisrichardson.eventstore.examples.todolist.e2etests.db;


import net.chrisrichardson.eventstore.examples.todolist.db.DatabaseTodoConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.db.Todo;
import net.chrisrichardson.eventstore.examples.todolist.db.TodoDAO;
import net.chrisrichardson.eventstore.examples.todolist.model.TodoInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = {
        "eventuate.aws.accessKeyId=${AWS_ACCESS_KEY_ID}",
        "eventuate.aws.secretAccessKey=${AWS_SECRET_ACCESS_KEY}",
        "eventuate.aws.region=${AWS_REGION}",
}, classes = DatabaseTodoConfiguration.class)
public class TodoDAOTest {

  @Autowired
  private TodoDAO todoDAO;

  @Test
  public void shouldSaveAndGetTodo() {
    Todo todo = generateTodo();
    todoDAO.save(todo);

    Todo savedTodo = todoDAO.findOne(todo.getId()).get();
    assertEquals(todo, savedTodo);
  }


  private Todo generateTodo() {
    Todo todo = new Todo(new TodoInfo(generateId()));
    todo.setId(generateId());
    return todo;
  }

  private String generateId() {
    return UUID.randomUUID().toString();
  }
}
