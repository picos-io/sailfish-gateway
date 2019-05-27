########## How To Use Docker Image ###############
##
##  Image Name: soterianetworks/cip:gateway
##  How To Build Docker Image: docker build -f Dockerfile --no-cache -t soterianetworks/cip:gateway --rm=true .
##  Docker hub link:
##  Description: This docker container is for the API gateway server.
##################################################
# build stage
FROM soterianetworks/devops:jdk_images as builder

LABEL maintainer "developer@soterianetworks.com"

RUN mkdir -p /code

ADD . /code

WORKDIR /code

RUN cd /code && \
    chmod +x gradlew && \
    ./gradlew -p gateway clean build

# final stage
FROM soterianetworks/devops:jre_images

LABEL maintainer "developer@soterianetworks.com"

ADD shared/scripts/docker-entrypoint.sh /

COPY --from=builder /code/gateway/build/libs/gateway-1.0.0-SNAPSHOT.jar /gateway-1.0.0-SNAPSHOT.jar

RUN chmod +x /*.sh

VOLUME ["/usr/share/cip/gateway","/etc/nginx/conf.d/gateway"]

EXPOSE 80

WORKDIR /
ENTRYPOINT /docker-entrypoint.sh

#TODO: use jq to parse Spring Actuator's Health
HEALTHCHECK --interval=30s --timeout=3s --retries=6 \
            CMD curl -f http://localhost/health || exit 1
