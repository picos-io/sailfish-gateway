FROM soterianetworks/devops:jre_images

LABEL maintainer "developer@soterianetworks.com"

ADD shared/etc/nginx/conf.d/gateway/ /etc/nginx/conf.d/gateway/
ADD shared/usr/share/cip/gateway/ /usr/share/cip/gateway/
ADD shared/scripts/docker-entrypoint.sh /

ADD gateway/build/libs/gateway-1.0.0-SNAPSHOT.jar /gateway-1.0.0-SNAPSHOT.jar

RUN chmod +x /*.sh

VOLUME ["/usr/share/cip/gateway","/etc/nginx/conf.d/gateway"]

WORKDIR /
ENTRYPOINT /docker-entrypoint.sh

#TODO: use jq to parse Spring Actuator's Health
HEALTHCHECK --interval=30s --timeout=3s --retries=6 \
            CMD curl -f http://localhost/health || exit 1
