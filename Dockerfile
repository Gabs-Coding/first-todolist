FROM ubuntu:latest as build

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y

COPY . .

RUN apt-get install maven -y
RUN export TODOLIST_DB_URL=jdbc:h2:mem:todolist
RUN export TODOLIST_DB_USER=admin
RUN export TODOLIST_DB_PASSWORD=admin
RUN mvn clean install

EXPOSE 8080

COPY --from=build /target/todolist-0.0.1.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]