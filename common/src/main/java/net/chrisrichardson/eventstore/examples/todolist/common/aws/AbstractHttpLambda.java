package net.chrisrichardson.eventstore.examples.todolist.common.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.springframework.context.ApplicationContext;

import static net.chrisrichardson.eventstore.examples.todolist.common.aws.ApiGatewayResponse.build500ErrorResponse;

public abstract class AbstractHttpLambda<I> implements RequestHandler<I, ApiGatewayResponse> {

  protected final ApplicationContext applicationContext;

  public AbstractHttpLambda(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  public ApiGatewayResponse handleRequest(I request, Context context) {
    try {
      return applicationContext.getBean(AbstractAWSTodoHandler.class).handleRequest(request, context);
    } catch (Exception e) {
      return build500ErrorResponse();
    }
  }
}
