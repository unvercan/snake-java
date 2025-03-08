# stage 1: build (maven)
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# copy source code
COPY . .

# maven build
RUN mvn clean package -DskipTests

# stage 2: run (jre)
FROM eclipse-temurin:17-jre

WORKDIR /app

# copy jar
COPY --from=builder /app/target/*.jar app.jar

# argument variables with default value
ARG BOARD_WIDTH=15
ARG BOARD_HEIGHT=8

# environment variables with default value
ENV BOARD_WIDTH=${BOARD_WIDTH}
ENV BOARD_HEIGHT=${BOARD_HEIGHT}

# run jar
ENTRYPOINT  ["sh", "-c", "java -jar app.jar boardWidth=$BOARD_WIDTH boardHeight=$BOARD_HEIGHT"]
