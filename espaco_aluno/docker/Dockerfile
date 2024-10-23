# Use uma imagem base do OpenJDK
FROM openjdk:21-jdk-slim

# Define o diretório de trabalho na imagem
WORKDIR /app

# Copie o arquivo pom.xml e o diretório src para o diretório de trabalho
COPY pom.xml .
COPY src ./src

# Adicione a dependência do Maven para construir o aplicativo
RUN apt-get update && apt-get install -y maven

# Execute a construção do aplicativo
RUN mvn clean package -DskipTests

# Copie o jar construído para o diretório de trabalho
COPY target/espaco_aluno-0.0.1-SNAPSHOT.jar app.jar

# Defina o comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

# Exponha a porta que sua aplicação Spring Boot usará
EXPOSE 8080
