FROM --platform=linux/amd64 openjdk:22

LABEL authors="daurenberdibekov"

EXPOSE 8080

ADD backend/target/postbin.jar postbin.jar

ENTRYPOINT ["java", "-jar", "postbin.jar"]
