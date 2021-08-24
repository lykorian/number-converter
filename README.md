# Number Converter

[Mark Daugherty](mailto:mark.r.daugherty@gmail.com)

Service for providing conversion of numeric values into alternative representations. Currently, Roman numeral
representation is the only implemented conversion.

## TODO

- Refine conversion service
- Swagger for mgmt endpoints
- AWS elastic beanstalk deployment
    - add arbitrary env-specific features?
- Documentation
  - Packaging layout
  - Engineering/testing methodology
  - Dependency attribution

## Specification

Roman numeral conversion has been implemented according to the following specification:

https://roman-numerals.info/

## Technologies

- [Micronaut](https://micronaut.io/) (Full stack JVM-based framework)
- [Logback](http://logback.qos.ch/) (Application logging)
- [Micrometer](https://micronaut-projects.github.io/micronaut-micrometer/latest/guide/) (Metrics - currently in-memory
  only)
- [Jib Maven Plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin) (For containerization
  during Maven build)
- [Swagger](https://swagger.io/) (API documentation)
- [AWS Elastic Beanstalk](https://docs.aws.amazon.com/elastic-beanstalk/index.html) (Cloud deployment)

https://numberconverter.lykorian.dev/romannumeral?query=1
https://numberconverter.lykorian.dev/swagger-ui/index.html

## Build and Run

Execute the following Maven command to build, test, and run the app locally:

`./mvnw mn:run`

The built-in HTTP server runs on port 8080.  `/romannumeral` is the only implemented endpoint; however, additional
endpoints are available for management and monitoring. See
the [API documentation](https://numberconverter.lykorian.dev/swagger-ui/index.html) for complete details.

## Deployment

The Number Converter app is deployed to AWS Elastic Beanstalk by a Github Action triggered upon push to the `main`
branch of the project GitHub repository.

## Framework Evaluation

Spring Boot and Micronaut were evaluated for implementing this application. Both were viable, but Micronaut had
advantages in the following areas:

- Minimal required configuration
- Faster startup time
- Robust JUnit5 support
- Cloud-native
- Built-in support for management tooling, building Docker images, and generating Swagger documentation

## Push To Docker Registry Workflow

Workflow file: [`.github/workflows/maven.yml`](.github/workflows/maven.yml)

### Workflow description

For pushes to the `main` branch, the workflow will:

1. Setup the build environment with respect to the selected java/graalvm version.
2. Login to docker registry based on provided configuration.
3. Build, tag and push Docker image with Micronaut application to the Docker container image.

### Dependencies on other GitHub Actions

- [Docker login](`https://github.com/docker/login-action`)(`docker/login`)
- [Setup GraalVM](`https://github.com/DeLaGuardo/setup-graalvm`)(`DeLaGuardo/setup-graalvm`)

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

## Versioning

Follows [Semantic Versioning](http://semver.org/) guidelines.