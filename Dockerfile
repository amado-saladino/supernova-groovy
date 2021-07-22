FROM selenium/standalone-chrome
WORKDIR /app
RUN sudo apt update && \
    sudo apt install -y default-jdk
RUN sudo wget https://services.gradle.org/distributions/gradle-6.6.1-bin.zip -P /tmp
RUN sudo unzip -d /opt/gradle /tmp/gradle-*.zip
ENV gradle=/opt/gradle/gradle-6.6.1/bin
ENV PATH=${gradle}:${PATH}
ENTRYPOINT ["gradle"]
CMD ["clean", "test"]