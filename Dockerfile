FROM openjdk:8-jdk-alpine
LABEL maintainer="bo chohyunil"

COPY target/*.jar /home/netty/app.jar

#개발에 필요한 유틸리티를 빌드 시 컨테이너에 미리 다운로드. 
#RUN apt-get update -y && apt-get install curl -y && apt-get install dnsutils -y


ENV BROKERS=172.31.240.1:9092
ENV PROFILE=local
ENV FCM_CONNECTION_POOL_IDLETIME_MAX=5
ENV FCM_EVENTLOOP_THREAD_NUMBER=1

#host 파일 변경을 위한 쉘 이동
COPY changeHost.sh /etc

#도메인 이슈가 생길 경우, RUN COMMAND를 통하여 리눅스 내의 hosts 파일 변경
RUN chmod 777 /etc/changeHost.sh 

ENTRYPOINT ["sh","/etc/changeHost.sh"]
#ENTRYPOINT ["java", "-jar","-Dspring.http.encoding.charset=UTF-8","-Dspring.http.encodingnabled=true","-Dspring.http.encoding.force=true","-Dfile.encoding=UTF-8"]
#CMD ["-Dspring.profiles.active=${PROFILE}","/app.jar"]