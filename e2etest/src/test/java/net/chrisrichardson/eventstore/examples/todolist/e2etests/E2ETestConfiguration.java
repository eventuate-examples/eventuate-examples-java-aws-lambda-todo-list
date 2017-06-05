package net.chrisrichardson.eventstore.examples.todolist.e2etests;

import net.chrisrichardson.eventstore.examples.todolist.testutil.BasicWebTestConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class E2ETestConfiguration extends BasicWebTestConfiguration {
}
