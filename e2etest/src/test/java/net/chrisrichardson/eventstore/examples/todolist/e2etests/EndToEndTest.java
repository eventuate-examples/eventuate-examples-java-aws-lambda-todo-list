package net.chrisrichardson.eventstore.examples.todolist.e2etests;

import net.chrisrichardson.eventstore.examples.todolist.AbstractTodoRestAPITest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = {E2ETestConfiguration.class})
public class EndToEndTest extends AbstractTodoRestAPITest {

  @Value("#{systemEnvironment['AWS_GATEWAY_API_INVOKE_URL']}")
  private String apiGatewayInvokeUrl;

  @Override
  protected String getHost() {
    return apiGatewayInvokeUrl;
  }
}
