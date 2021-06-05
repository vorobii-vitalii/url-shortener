FROM tomcat:jdk11-corretto
MAINTAINER 'Vorobii Vitalii'

EXPOSE 8080

COPY target/app.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]