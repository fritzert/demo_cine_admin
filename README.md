
## Versiones

    - Java 21
    - Spring Boot 3.3.5
    - Spring Data
    - Spring Secutiry - JWT
    - Postgresql
    - OpenApi 2.3.0

## Dockerfile
***
#### Construccion
    > DOCKER_BUILDKIT=1 docker build -t cine_admin_sb:1 .

#### Ejecucion (Conexion a BD local)
    > docker run \
    --rm \
    -p 8080:8080 \
    -e "SPRING_PROFILES_ACTIVE=docker" \
    --name cine_admin_api \
    cine_admin_sb:1


## Docker compose
***
#### Construccion

    > docker compose build

#### Ejecucion
    > docker compose up -d

#### Detener
    > docker compose down
