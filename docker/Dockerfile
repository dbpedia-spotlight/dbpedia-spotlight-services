FROM openjdk:8-jre-alpine

MAINTAINER  DBpedia Spotlight Team <dbp-spotlight-developers@lists.sourceforge.net>


ENV RELEASE_SERVER    sourceforge.net/projects/dbpedia-spotlight/files
ENV RELEASE_FILENAME  dbpedia-spotlight-services-1.0.0.jar

RUN mkdir -p /opt/spotlight/templates && \
    cd /opt/spotlight && \
    wget "https://$RELEASE_SERVER/spotlight/$RELEASE_FILENAME" && \
    wget "https://$RELEASE_SERVER/spotlight/services/application.yml" && \
    wget "https://$RELEASE_SERVER/spotlight/services/templates/nif-21.vm" && \
    cd /bin && \
    wget "https://$RELEASE_SERVER/spotlight/services/spotlight.sh"

RUN chmod +x /bin/spotlight.sh

EXPOSE 8080
