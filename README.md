# Roman Numeral Converter

## TODO

- Documentation of all aspects
- Deployment strategy
- Docker image

## Requirements

Your HTTP Endpoint must accept a URI in this format:
http://localhost:8080/romannumeral?query={integer}

{integer} must be any integer value in the range 1-3999.

Errors can be returned in plain text format.

Include additional DevOps capabilities in your project to represent how you would prepare your project for ease of operation in a production environment (e.g. metrics, monitoring, logging, etc.). Add tooling to build a runnable Docker container for your service if you are familiar with Docker.

## Rules

For the number to Roman numeral conversion itself, do not use existing code or libraries. We want to see your development methodology in action.

Use Java or JavaScript as the programming language. If you wish to work in another language, please ask first in response to the email accompanying this test.

Use a public Github repository for your work. Include the link to the Github repository in your response. You may use publicly available technologies for the HTTP server, test framework, etc. Please identify any such dependencies you use.

## Tips

- Find and reference a specification for Roman numerals online. Wikipedia is acceptable.
- Provide clear and concise documentation:
  - README.md with:
    - How to build and run your project.
    - Your engineering and testing methodology.
    - Your packaging layout
    - Dependency attribution
  - Inline documentation in your source code
- Tests
- Error Handling

## Resources

- https://roman-numerals.info/

## Micronaut 3.0.0 Documentation

- [User Guide](https://docs.micronaut.io/3.0.0/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.0.0/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.0.0/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

---

## Push To Docker Registry Workflow

Workflow file: [`.github/workflows/maven.yml`](.github/workflows/maven.yml)

### Workflow description

For pushes to the `master` branch, the workflow will:

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

#### Google Container Registry (GCR)

Create service account with permission to edit GCR or use predefined Storage Admin role.

- `DOCKER_USERNAME` - set exactly to `_json_key`
- `DOCKER_PASSWORD` - content of the service account json key file
- `DOCKER_REPOSITORY_PATH` - `<project-id>/foo`
- `DOCKER_REGISTRY_URL` - `gcr.io`

> See [docker/login-action for GCR](https://github.com/docker/login-action#google-container-registry-gcr)

#### AWS Elastic Container Registry (ECR)

Create IAM user with permission to push to ECR (or use AmazonEC2ContainerRegistryFullAccess role).

- `DOCKER_USERNAME` - access key ID
- `DOCKER_PASSWORD` - secret access key
- `DOCKER_REPOSITORY_PATH` - no need to set
- `DOCKER_REGISTRY_URL` - set to `<aws-account-number>.dkr.ecr.<region>.amazonaws.com`

> See [docker/login-action for ECR](https://github.com/docker/login-action#aws-elastic-container-registry-ecr)

#### Oracle Infrastructure Cloud Registry (OCIR)

[Create auth token](https://www.oracle.com/webfolder/technetwork/tutorials/obe/oci/registry/index.html#GetanAuthToken)
for authentication.

- `DOCKER_USERNAME` - username in format `<tenancy>/<username>`
- `DOCKER_PASSWORD` - account auth token
- `DOCKER_REPOSITORY_PATH` - `<tenancy>/<registry>/foo`
- `DOCKER_REGISTRY_URL` - set to `<region>.ocir.io`

> See [docker/login-action for OCIR](https://github.com/docker/login-action#oci-oracle-cloud-infrastructure-registry-ocir)

## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

## Feature github-workflow-docker-registry documentation

- [https://docs.github.com/en/free-pro-team@latest/actions](https://docs.github.com/en/free-pro-team@latest/actions)

