# Number Converter

[numberconverter.lykorian.dev](https://numberconverter.lykorian.dev)

Microservice providing conversion of numeric values into alternative representations. Currently, Roman numeral
representation is the only implemented conversion.

![Maven Build](https://github.com/lykorian/number-converter/actions/workflows/maven-package.yml/badge.svg)
[![codecov](https://codecov.io/gh/lykorian/number-converter/branch/develop/graph/badge.svg?token=NPYB3HHIW6)](https://codecov.io/gh/lykorian/number-converter)
[![License: Unlicense](https://img.shields.io/badge/license-Unlicense-blue.svg)](http://unlicense.org/)

## TODO

- Documentation
    - Packaging layout
    - Dependency attribution
- Fix github pages site

## Specification

Roman numeral conversion has been implemented according to the following specification:

https://roman-numerals.info/

## Overview

Number Converter is a microservice providing conversion of numeric values into Roman Numerals implemented
with [Micronaut](https://micronaut.io/) and deployed
to [AWS Elastic Beanstalk](https://docs.aws.amazon.com/elastic-beanstalk/index.html).

## Build and Run

Execute the following Maven command to build, test, and run the Number Converter app locally:

`./mvnw mn:run`

The built-in HTTP server runs on port `8080`.  `/romannumeral` is the only implemented endpoint; however, additional
endpoints are available for management and monitoring. See the [API documentation](https://numberconverter.lykorian.dev)
for complete details.

## Deployment Workflows

### AWS Elastic Beanstalk

[`.github/workflows/maven-deploy-eb.yml`](.github/workflows/maven-deploy-eb.yml)

#### Description

The Number Converter app is deployed to AWS Elastic Beanstalk by a Github Action triggered upon push to the `main`
branch of the project GitHub repository.

### Docker

[`.github/workflows/maven-deploy-docker.yml`](.github/workflows/maven-deploy-docker.yml)

#### Description

For pushes to the `main` branch, the workflow will:

1. Setup the build environment with respect to the selected java/graalvm version.
2. Login to Docker registry based on provided configuration.
3. Build, tag and push Docker image with Micronaut application to the Docker container image.

### Setup

Add the following GitHub secrets:

| Name | Description |
| ---- | ----------- |
| DOCKER_USERNAME | Username for Docker registry authentication. |
| DOCKER_PASSWORD | Docker registry password. |
| DOCKER_REPOSITORY_PATH | Path to the docker image repository inside the registry, e.g. for the image `foo/bar/micronaut:0.1` it is `foo/bar`. |
| DOCKER_REGISTRY_URL | Docker registry url. |

#### Configuration examples

Specifics on how to configure public cloud docker registries like DockerHub, Google Container Registry (GCR), AWS
Container Registry (ECR), Oracle Cloud Infrastructure Registry (OCIR) and many more can be found
in [docker/login-action](https://github.com/docker/login-action)
documentation.

#### DockerHub

- `DOCKER_USERNAME` - DockerHub username
- `DOCKER_PASSWORD` - DockerHub password or personal access token
- `DOCKER_REPOSITORY_PATH` - DockerHub organization or the username in case of personal registry
- `DOCKER_REGISTRY_URL` - No need to configure for DockerHub

> See [docker/login-action for GCR](https://github.com/docker/login-action#dockerhub)

## Framework Evaluation

Spring Boot and Micronaut were evaluated for implementing this application. Both were viable, but Micronaut had
advantages in the following areas:

- Minimal required configuration
- Faster startup time
- Robust JUnit5 support
- Cloud-native
- Built-in support for management tooling, building Docker images, and generating Swagger documentation

## Packaging

TODO

## Unit Tests

Unit tests are implemented in JUnit 5 using Micronaut's
built-in [test extension](https://micronaut-projects.github.io/micronaut-test/latest/guide/) and executed in the test
phase of the Maven build. Test coverage is evaluated using
the [JaCoCo Maven plugin](https://www.eclemma.org/jacoco/trunk/doc/maven.html) and uploaded
to [Codecov](https://app.codecov.io/gh/lykorian/number-converter) by
the [Maven Package](https://github.com/lykorian/number-converter/actions/workflows/maven-package.yml) GitHub action,
which is triggered by pushes to the `develop` branch and when Pull Requests are opened. Current code coverage is
displayed in a badge at the top of this README.

## Load Tests

Load tests are manually dispatched by
the [Load Test](https://github.com/lykorian/number-converter/actions/workflows/load-test.yml) GitHub Action. Tests are
executed by [Artillery](https://artillery.io/) using the test script `src/test/resources/load-test-script.yml`. Results
are output to the **Artillery Load Test** Job log.

Since there is only a single deployed environment, load tests are executed directly against this target as opposed to a
Development or Staging server.

The load test script and unit tests share the same input values
from [`src/test/resources/conversions.csv`](src/test/resources/conversions.csv)

### Controllers

The [Roman Numeral Number Converter Controller](src/main/java/dev/lykorian/numberconverter/controllers/RomanNumeralNumberConverterController.java) is responsible for handling requests to the `/romannumeral` endpoint, validating inputs, and returning JSON responses to the client with the conversion result.  Conversion logic is delegated to services.

#### Error Handling

Missing or invalid inputs will return a `400` response code with a JSON object containing the error message details.
See [Swagger](https://numberconverter.lykorian.dev/#/number-converter/convert) for details.

### Services

The service layer of the application implements number conversion logic.  Decoupling of the controller from underlying implementation allows for services to be consumed in other contexts to better support extensibility of the app.

### Validation

Inputs are validated at both the controller and service layers. The secondary service validation is intended to ensure
valid inputs regardless of source (e.g. event handlers or other services) while delegating context-appropriate messaging
to the client (in this case, the Roman Numeral controller).

### Management

Monitoring and management are interfaced with [Micrometer](https://micrometer.io/). Endpoints are detailed
in [Swagger](https://numberconverter.lykorian.dev/#/management). Metrics are currently in-memory only using
Micrometer's `SimpleMeterRegistry`.

## Resources

- [Micronaut](https://micronaut.io/)
- [Logback](http://logback.qos.ch/)
- [Micrometer](https://micronaut-projects.github.io/micronaut-micrometer/latest/guide/)
- [Jib Maven Plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin)
- [Swagger](https://swagger.io/)
- [AWS Elastic Beanstalk](https://docs.aws.amazon.com/elastic-beanstalk/index.html)

- [Swagger UI](https://numberconverter.lykorian.dev)
- [Codecov](https://app.codecov.io/gh/lykorian/number-converter)

## Versioning

Follows [Semantic Versioning](http://semver.org/) guidelines.