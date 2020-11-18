# Shufflrr API usage sample

#### Important: This library is not maintained.

A `Shufflrr` instance is tied to the credentials of one user. Send synchronous or asynchronous requests using the Shufflrr object.

`InType`s and `OutType`s contain the handling behavior for request input and response output. They are designed to be reused with different content. For example, many requests will contain JSON input and return JSON as a response. The JsonBodyHandler can be used to map JSON responses to POJOs or records using the Jackson Databind module.

Java 13 or above is recommended, as a key issue with custom BodyHandlers was addressed in Java 13.

```java
// Deprecated; to be replaced with OAuth.
Credentials.builder()
    .withSite(site)
    .withUsername(username)
    .withPassword(password)
    .build();

Shufflrr conn = Shufflrr.connect(credentials);

// Here, we send the folder contents request to obtain the contents of folder with ID 10514080.
// There is no input for a GET request and we chose to receive the output as a JsonNode.
conn.sendAsync(Requests.FOLDER_CONTENTS, InTypes.NULL, OutTypes.NODE, "10514080")
        .thenAccept(response -> response.body().map(JsonNode::toPrettyString).ifPresent(System.out::println));

// Use composable JSON lenses (static members of the Lens class) to extract data.
conn.sendAsync(Requests.FILE, InTypes.NULL, OutTypes.NODE, "14534001").thenAccept(response -> {
    response.body().ifPresent(node -> {

        var lens1 = Lens.TRAVERSE.apply("slide");
        var lens2 = Lens.TRAVERSE.apply("createdDate");
        var lens3 = Lens.DATE.compose(lens2).compose(lens1);

        // Prints the hour component of the parsed date from within the JSON tree structure.
        // Lenses are composable and reusable.
        System.out.println(lens3.apply(node).getHour());;

    });
});

// Join your futures or use the synchronous request method if your program does not run continuously to ensure task completion.
```

To use this library, create a main class similar to the one above and run the modular app:

`java -p <path_to_modules>:<path_to_app_jar> -m <main_module>/<main_class>`

Optionally, package your app with jpackage/jlink to streamline use.
