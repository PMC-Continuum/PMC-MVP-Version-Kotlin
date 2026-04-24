#!/bin/sh

# Resolve the app home directory
APP_HOME="$(cd "$(dirname "$0")" && pwd -P)"
CLASSPATH="${APP_HOME}/gradle/wrapper/gradle-wrapper.jar"

# Determine java command
if [ -n "$JAVA_HOME" ]; then
    JAVACMD="$JAVA_HOME/bin/java"
else
    JAVACMD="java"
fi

exec "$JAVACMD" \
    -Xmx64m -Xms64m \
    "-Dorg.gradle.appname=gradlew" \
    -classpath "$CLASSPATH" \
    org.gradle.wrapper.GradleWrapperMain \
    "$@"
