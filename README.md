# Number Converter

[numberconverter.lykorian.dev](https://numberconverter.lykorian.dev)

Microservice providing conversion of numeric values into alternative representations. Currently, Roman numeral
representation is the only implemented conversion.

![Maven Build](https://github.com/lykorian/number-converter/actions/workflows/maven-package.yml/badge.svg)
[![codecov](https://codecov.io/gh/lykorian/number-converter/branch/develop/graph/badge.svg?token=NPYB3HHIW6)](https://codecov.io/gh/lykorian/number-converter)
[![License: Unlicense](https://img.shields.io/badge/license-Unlicense-blue.svg)](http://unlicense.org/)

## Overview

Number Converter is a microservice providing conversion of numeric values into Roman Numerals implemented
with [Micronaut](https://micronaut.io/) and deployed
to [AWS Elastic Beanstalk](https://docs.aws.amazon.com/elastic-beanstalk/index.html).

### Specification

Roman numeral conversion has been implemented according to the following specification:

https://roman-numerals.info/

## Build and Run

Execute the following Maven command to build, test, and run the app locally:

`./mvnw mn:run`

The built-in HTTP server runs on port `8080`.  `/romannumeral` is the only implemented endpoint; however, additional
endpoints are available for management and monitoring. See the [API documentation](https://numberconverter.lykorian.dev)
for complete details.

## Deployment Workflows

### AWS Elastic Beanstalk

[`.github/workflows/maven-deploy-eb.yml`](.github/workflows/maven-deploy-eb.yml)

Maven build and deploy to AWS Elastic Beanstalk for pushes to the `main` branch.

### Docker

[`.github/workflows/maven-deploy-docker.yml`](.github/workflows/maven-deploy-docker.yml)

Maven build, create Docker image, and push image to GitHub Container registry for pushes to the `main` branch.

## Application Architecture

### Framework Evaluation

Spring Boot and Micronaut were evaluated for implementing this application. Both were viable, but Micronaut had
advantages in the following areas:

- Minimal required configuration
- Faster startup time
- Robust JUnit support
- Cloud-native
- Built-in support for management tooling, building Docker images, and generating Swagger documentation

### Packaging

The app uses Maven to build, test, and package a runnable JAR file. Project resources are structured as follows:

| Resource(s)  | Description |
| ------------- | ------------- |
| `.github/workflows` | GitHub Action configurations supporting build, test, and deployment tasks |
| `.mvn/wrapper` | Maven wrapper for build portability |
| `micronaut-cli.yml` | Micronaut CLI configuration |
| `mvnw` | Maven wrapper executable |
| `openapi.properties` | Swagger API configuration |
| `pom.xml` | Maven build configuration |
| `src/main/assembly/zip.xml` | Assembly plugin descriptor |
| `src/main/java` | Java sources |
| `src/main/resources/application.yml` | Micronaut app configuration |
| `src/main/resources/logback.xml` | Logging configuration |
| `src/test/java` | Java test sources |
| `src/test/resources/conversion.csv` | Unit and Load test data |
| `src/test/resources/load-test-script.yml` | Artillery load test configuration |
| `src/test/resources/logback-test.xml` | Test logging configuration |

### Controllers

The [Roman Numeral Number Converter Controller](src/main/java/dev/lykorian/numberconverter/controllers/RomanNumeralNumberConverterController.java)
is responsible for handling requests to the `/romannumeral` endpoint, validating inputs, and returning JSON responses to
the client with the conversion result. Conversion logic is delegated to services.

#### Error Handling

Missing or invalid inputs will return a `400` response code with a JSON object containing the error message details.
See [Swagger](https://numberconverter.lykorian.dev/#/number-converter/convert) for details.

### Services

The service layer of the application implements number conversion logic. Decoupling of the controller from underlying
implementation allows for services to be consumed in other contexts to better support extensibility of the app.

### Validation

Inputs are validated at both the controller and service layers. The secondary service validation is intended to ensure
valid inputs regardless of source (e.g. event handlers or other services) while delegating context-appropriate messaging
to the client (in this case, the Roman Numeral controller).

### Unit Tests

Unit tests are implemented in [JUnit](https://junit.org/junit5/docs/current/user-guide/) using Micronaut's
built-in [test extension](https://micronaut-projects.github.io/micronaut-test/latest/guide/) and executed in the test
phase of the Maven build. Test coverage is evaluated using
the [JaCoCo Maven plugin](https://www.eclemma.org/jacoco/trunk/doc/maven.html) and uploaded
to [Codecov](https://app.codecov.io/gh/lykorian/number-converter) by
the [Maven Package](https://github.com/lykorian/number-converter/actions/workflows/maven-package.yml) GitHub action,
which is triggered by pushes to the `develop` branch and when Pull Requests are opened.

See [Codecov](https://app.codecov.io/gh/lykorian/number-converter) for current coverage details.

### Load Tests

Load tests are manually dispatched by
the [Load Test](https://github.com/lykorian/number-converter/actions/workflows/load-test.yml) GitHub Action. Tests are
executed by [Artillery](https://artillery.io/) using the test script [`load-test-script.yml`](src/test/resources/load-test-script.yml). Results
are output to the **Artillery Load Test** Job log.

Since there is only a single deployed environment, load tests are executed directly against this target as opposed to a
Development or Staging server.

The load test script and unit tests share the same input values
from [`conversions.csv`](src/test/resources/conversions.csv)

### Management

Monitoring and management are interfaced with [Micrometer](https://micrometer.io/). Endpoints are detailed
in [Swagger](https://numberconverter.lykorian.dev/#/management). Metrics are currently in-memory only using
Micrometer's `SimpleMeterRegistry`.

## Resources

- [Micronaut](https://micronaut.io/)
- [Logback](http://logback.qos.ch/)
- [Guava](https://guava.dev/)
- [Micrometer](https://micronaut-projects.github.io/micronaut-micrometer/latest/guide/)
- [Jib Maven Plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin)
- [Swagger](https://swagger.io/)
- [AWS Elastic Beanstalk](https://docs.aws.amazon.com/elastic-beanstalk/index.html)
- [Artillery](https://artillery.io/)

## Versioning

Follows [Semantic Versioning](http://semver.org/) guidelines.