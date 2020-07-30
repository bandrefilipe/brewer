# Brewer!
A study project based on web software development aimed at practicing a wide range of technologies, libraries, patterns and best practices.

## Getting Started
(work pending)

## Architecture
The application is organized in multiple modules put together to implement a software architectural design called _"Ports & Adapters"_, or simply _"Hexagonal Architecture"_.

The main feature of the Hexagonal Architecture, as opposed to the commonly used layered architecture style, is that the dependencies between our components point "inward", towards our domain objects.

<img alt="Hexagonal Architecture" src="https://reflectoring.io/assets/img/posts/spring-hexagonal/hexagonal-architecture.png"/>

The hexagon is just a fancy way to describe the core of the application that is made up of domain objects, use cases that operate on them, and input and output ports that provide an interface to the outside "world" (the adapters).

### Modules
The application is dived in multiple modules, following the aforementioned hexagonal architecture.

- [brewer-application](./brewer-application/README.md "brewer-application")
  > Holds classes that make up the core of the application: services that implement use cases which query and modify the domain model
- [brewer-commons](./brewer-commons/README.md "brewer-commons")
  > Provides common utility functionalities that can be accessed and used by all other modules
- [brewer-configuration](./brewer-configuration/README.md "brewer-configuration")
  > Actual Spring Boot application (main class) and any Spring Java Configuration that puts together the Spring application context. To create the application context, it needs access to the other modules, which each provides certain parts of the application
- [brewer-persistence](./adapters/brewer-persistence/README.md "brewer-persistence")
  > An adapter that implements the persistence layer of the application
- [brewer-web](./adapters/brewer-web/README.md "brewer-web")
  > An adapter for the web layer of the application, which call the use cases implemented in the application's core

## Companion Articles
- [Building a Multi-Module Spring Boot Application](https://reflectoring.io/spring-boot-gradle-multi-module/ "reflectoring.io")
- [Hexagonal Architecture with Java and Spring](https://reflectoring.io/spring-hexagonal/ "reflectoring.io")

## License
```
MIT License

Copyright (c) 2020 André Barranco

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Employed Technology

|<img alt="Java" src="https://www.vectorlogo.zone/logos/java/java-ar21.svg" width="100">|<img alt="Spring Boot" src="https://www.vectorlogo.zone/logos/springio/springio-ar21.svg" width="100">|<img alt="Gradle" src="https://www.vectorlogo.zone/logos/gradle/gradle-ar21.svg" width="100">|
|:---:|:---:|:---:|

- [Java 11](https://www.oracle.com/java/ "oracle.com")
- [Spring Boot 2.3](https://spring.io/projects/spring-boot "spring.io")
- [Gradle 6.3](https://gradle.org/ "gradle.org")
