# Example Java AWS Lambda function for Eventuate

This project is the AWS Lambda/[Serverless framework](http://serverless.com/)-based version of the [Eventuate Todo application](https://github.com/eventuate-examples/eventuate-examples-java-spring-todo-list).
The Todo List application lets users maintain a todo list.
This example illustrates how you can use develop an application with a [microservices architecture](http://microservices.io/patterns/microservices.html) that uses the following:

* The [Eventuate&trade; Platform](http://eventuate.io) for [Event Sourcing](http://microservices.io/patterns/data/event-sourcing.html) and [Command Query Responsibility Segregation (CQRS)](http://microservices.io/patterns/data/cqrs.html).
* [AWS Lambda](https://aws.amazon.com/lambda/)
* [Serverless framework](https://serverless.com/)
* The [Eventuate AWS Gateway Plugin](https://github.com/shopcookeat/eventuate-aws-gateway-serverless-plugin), which enables a serverless lambda to subscribe to Eventuate events.

# Building the application

To build the app:

```
./gradlew build
```

# Installing the plugin

To install the [Eventuate AWS Gateway plugin for Serverless](https://github.com/eventuate-clients/eventuate-aws-gateway-serverless-plugin):

```
npm install
```
or if you are using Vagrant
```
npm install --no-bin-links
```

When the todo-queryside-lambda is deployed, this plugin registers it with Eventuate.
When the todo-queryside-lambda undeployed, the plugin unregisters it.
See the `serverless.yml` for the details of how the events of interest are specified.

# Deploying the lambdas

Set the Eventuate environment variables: `EVENTUATE_API_KEY_ID` and `EVENTUATE_API_KEY_SECRET`.

To deploy the lambdas:

```
serverless deploy
```


# Accessing the application

First, set an environment variable to the root URL.
Serverless displays this when you lambdas are deployed.
Alternatively, run this command:

```
export BASE_URL=$(serverless info -v | grep ServiceEndpoint | cut -d' ' -f2)
```

To create a todo:

```
curl -d '{"title" : "Say Hello"}' $BASE_URL/todos
```

Make a note of the id of the created todo, eg.

```
export ID=xxxx-yyyy
```


To get the Todo:

```
curl $BASE_URL/todos/$ID

```

To get all todos:

```
curl $BASE_URL/todos

```

To update a Todo:

```
curl -X PUT -d '{"title" : "Say Hello Again"}' $BASE_URL/todos/$ID
```

To delete a Todo:

```
curl -X DELETE $BASE_URL/todos/$ID
```


# Got questions?

Don't hesitate to create an issue or see

* [Website](http://eventuate.io)
* [Mailing list](https://groups.google.com/d/forum/eventuate-users)
* [Slack](https://eventuate-users.slack.com). [Get invite](https://eventuateusersslack.herokuapp.com/)
* [Contact us](http://eventuate.io/contact.html).
