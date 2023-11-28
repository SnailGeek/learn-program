#FROM openjdk:8-alpine3.9
FROM adoptopenjdk/openjdk11:jdk-11.0.9_11-alpine
# 作者信息
MAINTAINER laosiji Docker springboot "laosiji@lagou.com"
# 下载密钥并导入密钥
#RUN wget -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub && \
#    wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.34-r0/glibc-2.34-r0.apk && \
#    apk add --no-cache glibc-2.34-r0.apk && \
#    rm glibc-2.34-r0.apk
# 修改源
RUN echo "https://mirrors.tuna.tsinghua.edu.cn/alpine/v3.9/main/" > /etc/apk/repositories && \
    echo "https://mirrors.tuna.tsinghua.edu.cn/alpine/v3.9/community/" >> /etc/apk/repositories
#RUN echo "http://mirrors.aliyun.com/alpine/latest-stable/main/" > /etc/apk/repositories && \
#    echo "http://mirrors.aliyun.com/alpine/latest-stable/community/" >> /etc/apk/repositories
# 安装需要的软件，解决时区问题
#RUN apk --no-cache add ca-certificates && \
#    update-ca-certificates && \
RUN apk --update add curl bash tzdata && \
    rm -rf /var/cache/apk/*

ENV TZ Asia/Shanghai
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app.jar"]