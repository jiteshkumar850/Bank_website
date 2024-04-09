From ubuntu:latest AS src
RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .
RUN ./master bootjar --no-doreamon

From openjdk-17-jdk-slim
EXPOSE 8080
COPY --from-src/java/controller/UserController app.jar