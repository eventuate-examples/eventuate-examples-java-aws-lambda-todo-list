package net.chrisrichardson.eventstore.examples.todolist.db;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseTodoConfiguration {

  //  DynamoDB configuration
  @Bean
  public AmazonDynamoDB amazonDynamoDB() {
    AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
    return amazonDynamoDB;
  }

  @Bean
  public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
    return new DynamoDBMapper(amazonDynamoDB);
  }

  @Bean
  public DynamoDB dynamoDB(AmazonDynamoDB amazonDynamoDB) {
    return new DynamoDB(amazonDynamoDB);
  }

  @Bean
  public TodoDAO routesDAO(DynamoDBMapper dynamoDBMapper) {
    return new TodoDAO(dynamoDBMapper);
  }
}
