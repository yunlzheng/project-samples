FROM gradle:4.9.0-jre8 AS builder

RUN mkdir -p .gradle && chmod 777 .gradle
ADD gradle ./gradle
ADD src ./src
ADD build.gradle ./build.gradle
ADD gradlew ./gradlew
ADD settings.gradle ./settings.gradle

RUN ./gradlew build

From java:8
# copy jar from the first stage
COPY --from=builder build/libs/spring-cloud-kubernetes-sample.jar spring-cloud-kubernetes-sample.jar

ADD entrypoint.sh entrypoint.sh
RUN chmod +x entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]