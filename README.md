# FFB-Proxy

Currently, this application is more of a redirect service than a proxy but will probably be adjusted in future versions.

## Building

Build the war file via `./mvnw clean install` or use the release artifact from github.

## Starting

To start the application use `java -jar <pathToWar>`. The application opens two ports which can be defined via properties at startup.

Alternatively you can use spring-boot plugin without building first: `./mvnw spring-boot:run`

## Configuration

The following properties are used:

* server.port: defines the port for internal and administrative endpoints, defaults to 8080
* server.publicPort: defines the port for public endpoints, defaults to 8081

The application also requires a configuration file called `application.yml` defining the ffb instances. The file needs to be placed in the directory 
where the command is run from or a folder named `/config` located in there.

Should that not be suitable then the location can be customized by adding `--spring.config.location=file:///Users/home/config/jdbc.properties` to the command.

To create the file copy the example in the project root and adjust accordingly. `apiBaseUrl` refers to the root of the 
ffb server context path while `jnlpUrl` refers to the complete url of the sites jnlp endpoint. Both values can contain complete URLs including path segments and query parameters.

Each connection object has `name` attribute which serves as id. The property `primaryName` has to contain a name of one of the defined connections.
This is the connection used as base for all requests made to the endpoints.

## Endpoints

### Redirecting

All redirecting endpoints use the respective url of the current primary connection, preserving all query parameters.

* `/public/jnlp`: Triggers a redirect to the configured `jnlpUrl`
* `/ffb`: Triggers a redirect to the configured `apiBaseUrl` and preserves all optional trailing path elements which will be appended after path elements of the configured url

### Adminstration

* `/connections`: Returns a json representation of the current connections configuration
* `/connections/primary/{newPrimary}`: Sets the primary connection to the one referred to by `newPrimary` and returns a json representation of the resulting connections configuration

## Known Issues/Shortcomings

So far once the primary connection is changed there is no way to connect to the previous primary connection through this service.

This means that spectators would not be able to join games running on the previous referenced ffb instance. 
It can happen that one coach opens a game before the switch and their opponent tries to join after the switch was made, resulting in
both sitting alone in their respective game instances.

To mitigate this the proxy would need to determine on which instance a game (referred to by either gameId or game name)
is located and then redirect to this instance. Games that are found on neither instance would always be directed to the current primary.

To accomplish this changes will be needed on either ffb or fumbbl side which requires further drafting.