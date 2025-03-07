# jdk and maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# copy source code
COPY . .

# maven build
RUN mvn clean package -DskipTests

# jre
FROM eclipse-temurin:17-jre

WORKDIR /app

# copy jar
COPY --from=builder /app/target/*.jar app.jar

# run jar
CMD ["java", "-jar", "app.jar"]
