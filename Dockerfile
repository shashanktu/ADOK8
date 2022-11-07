FROM openjdk

COPY ./target/test-0.0.1-SNAPSHOT.jar test.jar
CMD ["java","-jar","test.jar"]
