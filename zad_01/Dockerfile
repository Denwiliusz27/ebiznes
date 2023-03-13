FROM ubuntu:22.04

ENV TZ=Europe/Warsaw
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN apt-get update
RUN apt-get -y upgrade

# pobranie pythona 3.8
RUN apt-get install -y software-properties-common
RUN add-apt-repository ppa:deadsnakes/ppa
RUN apt install -y python3.8

# pobranie Javy 8 oraz Kotlina
RUN apt-get install -y openjdk-8-jdk
RUN apt-get -y install kotlin

# pobranie Gradl'a
RUN apt install wget
RUN wget https://services.gradle.org/distributions/gradle-8.0.2-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-8.0.2-bin.zip \
    && rm /tmp/gradle-8.0.2-bin.zip

ENV GRADLE_HOME=/opt/gradle/gradle-8.0.2
ENV PATH=$PATH:$GRADLE_HOME/bin

# pobranie paczki JDBC SQlite
RUN echo 'dependencies { implementation "org.xerial:sqlite-jdbc:3.34.0" }' >> build.gradle

#RUN mkdir HelloWorld
#RUN cd HelloWorld
#workdir /HelloWorld
#RUN gradle init --type java-application
#COPY . /HelloWorld
#RUN gradle build
