FROM openjdk:8-jdk
ENV PORT=8080
EXPOSE 8080
VOLUME /tmp
RUN mkdir /work
ENV GOOGLE_APPLICATION_CREDENTIALS=./application_default_credentials.json
COPY . /work
COPY application_default_credentials.json /tmp/
WORKDIR /work
RUN cp /work/build/libs/gs-rest-service-0.1.0.jar /work/app.jar

CMD ["java", "-jar", "/work/app.jar"]