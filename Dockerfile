FROM --platform=linux/amd64 openjdk:22

#ENV POSTBIN_DB=jdbc:postgresql://postgres:5432/postbin

LABEL authors="daurenberdibekov"

EXPOSE 8080

ADD backend/target/postbin.jar postbin.jar

CMD ["sh", "-c", "java -jar /postbin.jar --spring.config.name=application-local"]
