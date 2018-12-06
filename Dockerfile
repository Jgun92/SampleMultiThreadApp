FROM openjdk:8-jre-alpine
ENV APP_FILE app.jar
ENV APP_HOME /app
EXPOSE 8080
COPY target/$APP_FILE $APP_HOME/
WORKDIR $APP_HOME
ENTRYPOINT exec java $JAVA_OPTS  -Djava.security.egd=file:/dev/./urandom -jar $APP_FILE
