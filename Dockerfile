From maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

From openjdk-17-jdk-slim
COPY --from=build /target/BANK_WEBSITE-0.0.1-SNAPSHOT.jar BANK_WEBSITE.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","BANK_WEBSITE.jar"]
