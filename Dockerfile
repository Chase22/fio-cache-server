FROM openjdk:17

COPY build/libs/fio-caching-server-0.0.1.jar /usr/bin/fio.jar
WORKDIR /usr/bin
CMD ["java", "-jar", "fio.jar"]
