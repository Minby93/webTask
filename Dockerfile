# Базовый образ с Maven и JDK для сборки приложения
FROM maven:3.9.9-eclipse-temurin-21-jammy AS build

# Копируем файлы проекта в контейнер
COPY . /app
WORKDIR /app

# Выполняем сборку проекта и создаем JAR файл
RUN mvn clean install -DskipTests

# Новый этап: создаем легковесный образ с OpenJDK для запуска приложения
FROM openjdk:22-oracle

# Копируем JAR файл из предыдущего этапа сборки
COPY --from=build /app/target/web-0.0.1-SNAPSHOT.jar /app/app.jar

# Устанавливаем команду запуска JAR файла
CMD ["java", "-jar", "/app/app.jar"]

# Открываем порт для приложения
EXPOSE 8080