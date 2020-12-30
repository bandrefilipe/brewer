# Brewer! [![Docker Cloud Build Status](https://img.shields.io/docker/cloud/build/bandrefilipe/brewer)](https://hub.docker.com/r/bandrefilipe/brewer) [![Docker Image Size (latest by date)](https://img.shields.io/docker/image-size/bandrefilipe/brewer?sort=date)](https://hub.docker.com/r/bandrefilipe/brewer)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=bandrefilipe_brewer&metric=bugs)](https://sonarcloud.io/dashboard?id=bandrefilipe_brewer) [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=bandrefilipe_brewer&metric=code_smells)](https://sonarcloud.io/dashboard?id=bandrefilipe_brewer) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=bandrefilipe_brewer&metric=coverage)](https://sonarcloud.io/dashboard?id=bandrefilipe_brewer) [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=bandrefilipe_brewer&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=bandrefilipe_brewer) [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=bandrefilipe_brewer&metric=ncloc)](https://sonarcloud.io/dashboard?id=bandrefilipe_brewer) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=bandrefilipe_brewer&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=bandrefilipe_brewer) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=bandrefilipe_brewer&metric=alert_status)](https://sonarcloud.io/dashboard?id=bandrefilipe_brewer) [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=bandrefilipe_brewer&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=bandrefilipe_brewer) [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=bandrefilipe_brewer&metric=security_rating)](https://sonarcloud.io/dashboard?id=bandrefilipe_brewer) [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=bandrefilipe_brewer&metric=sqale_index)](https://sonarcloud.io/dashboard?id=bandrefilipe_brewer) [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=bandrefilipe_brewer&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=bandrefilipe_brewer)

A study project based on web software development aimed at practicing a wide range of technologies, libraries, patterns and best practices.

## Getting Started
(work pending)

## Architecture
The application is organized in multiple modules put together to implement a software architectural design called _"Ports & Adapters"_, or simply _"Hexagonal Architecture"_.

The main feature of the Hexagonal Architecture, as opposed to the commonly used layered architecture style, is that the dependencies between our components point "inward", towards our domain objects.

<img alt="Hexagonal Architecture" src="https://reflectoring.io/assets/img/posts/spring-hexagonal/hexagonal-architecture.png"/>

The hexagon is just a fancy way to describe the core of the application that is made up of domain objects, use cases that operate on them, and input and output ports that provide an interface to the outside "world" (the adapters).

### Modules
The application is divided in multiple modules, following the aforementioned hexagonal architecture.

- [brewer-api](./adapters/brewer-api/README.md "brewer-api")
  > An adapter that exposes REST endpoints to consumers of our application
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
- [The Power of a Good SQL Naming Convention](https://www.xaprb.com/blog/2008/10/26/the-power-of-a-good-sql-naming-convention/ "xaprb.com")
- [How to implement Equals and HashCode for JPA entities](https://vladmihalcea.com/hibernate-facts-equals-and-hashcode/ "vladmihalcea.com")
- [How to implement equals and hashCode using the JPA entity identifier (Primary Key)](https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/ "vladmihalcea.com")
- [The best way to map a @NaturalId business key with JPA and Hibernate](https://vladmihalcea.com/the-best-way-to-map-a-naturalid-business-key-with-jpa-and-hibernate/ "vladmihalcea.com")
- [7 Popular Unit Test Naming Conventions](https://dzone.com/articles/7-popular-unit-test-naming "dzone.com")
- [Parse, don't validate](https://lexi-lambda.github.io/blog/2019/11/05/parse-don-t-validate/ "lexi-lambda.github.com")
- [Multi-arch and images, the simple way](https://www.docker.com/blog/multi-arch-build-and-images-the-simple-way/ "docker.com")

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

|<img alt="Java" src="https://www.vectorlogo.zone/logos/java/java-ar21.svg" width="100">|<img alt="Spring Boot" src="https://www.vectorlogo.zone/logos/springio/springio-ar21.svg" width="100">|<img alt="Gradle" src="https://www.vectorlogo.zone/logos/gradle/gradle-ar21.svg" width="100">|<img alt="Docker" src="https://www.vectorlogo.zone/logos/docker/docker-ar21.svg" width="100">|<img alt="SonarCloud" src="https://sonarcloud.io/images/project_badges/sonarcloud-white.svg" width="100">|
|:---:|:---:|:---:|:---:|:---:|

- [Java 11](https://www.oracle.com/java/ "oracle.com")
- [JUnit 5](https://junit.org/junit5/ "junit.org")
- [Spring Boot 2.3](https://spring.io/projects/spring-boot "spring.io")
- [Gradle 6.7](https://gradle.org/ "gradle.org")
- [Docker](https://www.docker.com "docker.com")
- [SonarCloud](https://sonarcloud.io "sonarcloud.io")
