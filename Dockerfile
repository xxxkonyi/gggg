FROM maven:3.3.9

ADD / /tmp/build/

        #构建应用
RUN cd /tmp/build && mvn clean install -P prod \
        #拷贝编译结果到指定目录
        && mv target/*.jar /app.jar \
        #清理编译痕迹
        && cd / && rm -rf /tmp/build

EXPOSE 8080

ENV JAVA_OPTS=""

RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]