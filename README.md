# -Migration-API

This project is an implementation of Uncle Bob's Clean Architecture using Spring Boot, Java 17, and PostgreSQL database.

## About

The aim of this project is to adhere to the principles of Clean Architecture, which emphasizes the separation of concerns, making the system easy to understand, develop, and maintain.

In this project, we have:

- **Entities**: These are our business objects.
- **Use Cases**: These contain all business rules.
- **Interface Adapters**: This set converts data from the format most convenient for use cases and entities, to the format most convenient for things like the web.
- **Frameworks and Drivers**: This is where all the details go: the web is a detail, the database is a detail, we keep these things on the outside.

One of the common use cases for enterprises has been implemented in this project to provide a practical example of how the architecture works. For more details, please refer to the use cases document in the repository.

## Technologies Used

- Spring Boot: Provides a good platform for Java developers to develop a stand-alone and production-grade spring application that you can just run.
- Java 17: The latest version of Java, providing various features and improvements over the previous versions.
- PostgreSQL: A powerful, open-source object-relational database system.

## Challenges

With Spring Boot, it was challenging to keep the interface, adapters, and framework layer separate, but every effort was made to adhere to the principles of Clean Architecture.

## Contributing

Contributions are welcome. Please feel free to fork, clone, and submit pull requests.

## License

This project is licensed under the terms of the MIT license.