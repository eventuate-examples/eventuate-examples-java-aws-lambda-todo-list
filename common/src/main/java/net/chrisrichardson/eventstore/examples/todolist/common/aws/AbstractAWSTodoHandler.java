package net.chrisrichardson.eventstore.examples.todolist.common.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAWSTodoHandler<I> implements RequestHandler<I, ApiGatewayResponse> {

  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public ApiGatewayResponse handleRequest(I input, Context context) {
    logger.debug("Got request: {}", input);

    return handleAWSRequest(input, context);
  }

  protected abstract ApiGatewayResponse handleAWSRequest(I request, Context context);
}
