# Architecture-as-code

This is an example of an architecture diagram produced by Structurizr.

Run `./gradlew run` to generate the diagram via the API, passing in the "api.secret" environment variable. Ask me for this.

The architecture source code:

https://github.com/mogronalol/architecture-example/blob/master/src/main/java/com/mycompany/mysystem/Structurizr.java

The example diagram which is produced:

https://raw.githubusercontent.com/mogronalol/architecture-example/master/example-diagram.png

More information can be found here:

- https://c4model.com/
- https://structurizr.com/

In our case we have a container level diagram. 

Notice how this is not a deployment diagram, and we are also omitting information about infrastructure. Here we only care about services and whether they communicate via messaging or rest, and whether they uses a database.


