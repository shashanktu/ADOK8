FROM openjdk:8-jdk-alpine

copy ./target/test-0.0.1-SNAPSHOT.jar test.jar

CMD ["java","-jar","test.jar"]