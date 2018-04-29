# pull base image.
FROM maven:3.5.3-jdk-8


# attach volumes
VOLUME /vol/development

# create working directory
RUN mkdir -p /vol/development
WORKDIR /vol/development

# maven exec
CMD ["mvn", "clean", "package", "exec:java"]