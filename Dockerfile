# Super fancye build file from https://codefresh.io/docker-tutorial/java_docker_pipeline/
FROM maven:3.6.3-jdk-8

COPY pom.xml .
RUN mvn dependency:go-offline


COPY src ./src
RUN mvn package -DskipTests=true

EXPOSE 8080

CMD java -jar /target/*.jar