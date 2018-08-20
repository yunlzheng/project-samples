#!/usr/bin/env bash
ACTIVE_PROFILE=${PROFILE:=default}
java -Xmx1024m -Djava.security.egd=file:/dev/./urandom -jar gs-spring-boot.jar --spring.profiles.active=${ACTIVE_PROFILE} $@