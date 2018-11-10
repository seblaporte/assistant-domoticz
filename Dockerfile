FROM resin/armv7hf-debian-qemu

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN [ "cross-build-start" ]

# Update repo and install tools
RUN apt-get update && apt-get install -y --no-install-recommends \
		bzip2 \
		unzip \
		xz-utils \
	&& rm -rf /var/lib/apt/lists/

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-armhf/jre
ENV JAVA_VERSION 8u181
ENV JAVA_DEBIAN_VERSION 8u181-b13-1~deb9u1
ENV CA_CERTIFICATES_JAVA_VERSION 20170531+nmu1

# Install Java
RUN set -x \
	&& apt-get update \
	&& apt-get install -y  \
		openjdk-8-jre-headless="$JAVA_DEBIAN_VERSION" \
	ca-certificates-java=$CA_CERTIFICATES_JAVA_VERSION && rm -rf /var/lib/apt/lists/* \
	&& [ "$JAVA_HOME" = "$(docker-java-home)" ]

RUN /var/lib/dpkg/info/ca-certificates-java.postinst configure

# Download and install Maven 3.2.5
RUN wget http://www.mirrorservice.org/sites/ftp.apache.org/maven/maven-3/3.2.5/binaries/apache-maven-3.2.5-bin.tar.gz && \
    cd /opt && sudo tar -xzvf apache-maven-3.2.5-bin.tar.gz

# Build and install application
RUN /opt/apache-maven-3.2.5/mvn -f /usr/src/app/pom.xml clean package -DskipTests && \
    cp /usr/src/app/target/*.jar /usr/app/assistant-bridge.jar

RUN [ "cross-build-end" ]

EXPOSE 8080

ENTRYPOINT ["java","-jar","/usr/app/assistant-bridge.jar"]