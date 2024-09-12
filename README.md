# Clean Microservice Architecture with Spring Boot

This project is an implementation of [Clean Architecture by Robert C. Martin](https://www.oreilly.com/library/view/clean-architecture-a/9780134494272/) using Spring Boot, Java 17, and PostgreSQL database.

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

## Clean Microservice Architecture

Uncle Bob Martin discusses a [Clean Microservice Architecture](https://blog.cleancoder.com/uncle-bob/2014/10/01/CleanMicroserviceArchitecture.html), emphasizing the importance of organizing systems into small, independent services. He advocates for a layered structure that separates concerns such as use cases and business rules from external frameworks and databases. This approach ensures that microservices remain clean, manageable, and adaptable to future changes.

### Key Principles

1. **Separation of Concerns**: Each microservice should have a clear responsibility, separating business logic from technical details.
2. **Independence**: Microservices should be independently deployable and scalable.
3. **Testability**: The architecture should promote easy testing of individual services.
4. **Low Coupling**: Services should have minimal dependencies on each other to reduce the impact of changes.

### Benefits

- **Scalability**: Independent services can be scaled individually based on demand.
- **Maintainability**: Clear separation of concerns makes the system easier to understand and maintain.
- **Flexibility**: The architecture allows for easier adaptation to new requirements or technologies.
- **Resilience**: Failures in one service are less likely to impact others, improving overall system reliability.

### Implementation

In this project, we have applied these principles by:

- Designing services around specific business capabilities.
- Ensuring that each service has its own database to avoid tight coupling.
- Using Spring Boot to create lightweight, standalone services.
- Writing comprehensive tests for each service to ensure reliability and correctness.

By adhering to these principles, we aim to build a robust and scalable microservice architecture that can evolve with changing business needs.

## Challenges

1. With Spring Boot, it was challenging to keep the interface, adapters, and framework layer separate, but every effort was made to adhere to the principles of Clean Architecture.
2. The globally distributed team and the long-running nature of the project, combined with constant changes in team structure, provided valuable learning experiences in maintaining the integrity of the Clean Architecture. These challenges helped us refine our approach and improve our practices over time.


## Contributing

Contributions are welcome. Please feel free to fork, clone, and submit pull requests.

## License

This project is licensed under the terms of the MIT license.

# References
1. [Clean Architecture by Robert C. Martin](https://www.oreilly.com/library/view/clean-architecture-a/9780134494272/)
2. [Clean Micro-service Architecture](https://blog.cleancoder.com/uncle-bob/2014/10/01/CleanMicroserviceArchitecture.html)
3. [Microservices Patterns by Chris Richardson](https://www.oreilly.com/library/view/microservices-patterns/9781617294549/)
