FROM eclipse-temurin:17-jdk-alpine as base

# Install Maven and add it to the PATH environment variable
RUN apk add --no-cache maven && \
    export PATH=$PATH:/usr/share/maven/bin

WORKDIR /app
COPY . .

# Development stage
FROM base AS dev
RUN ./mvnw clean package -DskipTests

# Production stage
FROM base AS prod
RUN ./mvnw clean package -DskipTests
RUN java -Dspring.profiles.active=prod -jar target/myapp.jar