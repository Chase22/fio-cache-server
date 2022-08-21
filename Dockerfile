FROM openjdk:17

COPY build/libs/fio-caching-server-0.0.1-SNAPSHOT.jar /usr/bin/fio.jar
WORKDIR /usr/bin
CMD ["java", "-jar", "fio.jar"]
