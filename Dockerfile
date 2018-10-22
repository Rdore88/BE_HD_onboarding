FROM dockerfile/gradle:jdk8
WORKDIR .
RUN gradle build
COPY . .
EXPOSE 8000
CMD ["gradle", "bootRun"]