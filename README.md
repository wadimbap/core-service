# Core-Service
Этот проект представляет собой сервис для управления изображениями пользователей, включая их загрузку, скачивание и управление.

## Требования

- Java 21
- Maven 3.9.6
- RabbitMQ
- Amazon S3

## Установка и запуск

1. **Клонируйте репозиторий:**
   ```bash
   git clone https://github.com/yourusername/core-service.git
   cd core-service
   ```
2. **Настройка RabbitMQ:**
   ```bash
    rabbitmqctl add_user myuser mypassword
    rabbitmqctl add_vhost myvhost
    rabbitmqctl set_permissions -p myvhost myuser ".*" ".*" ".*"
   ```
3. **Настройка S3:**
   
    Убедитесь, что у вас есть доступ к Amazon S3 и создан бакет для хранения изображений. Обновите application.yaml с вашими учетными данными и именем бакета.

5. **Настройка конфигурационного файла:**
  Обновите src/main/resources/application.yaml
  ```yaml
  spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: myuser
    password: mypassword
    virtual-host: myvhost

  datasource:
    url: jdbc:mysql://localhost:3306/coreservice
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL5Dialect

cloud:
  aws:
    credentials:
      access-key: YOUR_AWS_ACCESS_KEY
      secret-key: YOUR_AWS_SECRET_KEY
    region:
      static: YOUR_AWS_REGION
    s3:
      bucket-name: YOUR_S3_BUCKET_NAME
```
5. **Сборка и запуск:**
```bash
mvn clean install
java -jar target/core-service-0.0.1-SNAPSHOT.jar
```

# API эндпоинты

## Пользовательский контроллер

**POST /user/register**

• Регистрация нового пользователя.

• Пример запроса:

```json
{
  "username": "johndoe",
  "password": "password123",
  "email": "johndoe@example.com"
}
```

## Контроллер изображений

**POST /image/upload**

- Загрузка изображений пользователем.
  
- Параметры: userId, files (список файлов)

**GET /image/list**
- Получение списка изображений пользователя с сортировкой.
- Параметры: userId, sortBy

**GET /image/{imageId}**
- Скачивание изображения.
- Параметры: userId, imageId
  
## Контроллер модератора

**GET /moderator/all-images**
- Получение списка всех изображений (только для модераторов).

## Примеры запросов
Загрузка изображений:
```bash
curl -X POST -F "userId=1" -F "files=@path/to/image1.jpg" -F "files=@path/to/image2.png" http://localhost:8080/image/upload
```

Получение списка изображений с сортировкой:
```bash
curl -X GET "http://localhost:8080/image/list?userId=1&sortBy=date"
```

Скачивание изображения:
```bash
curl -X GET "http://localhost:8080/image/1?userId=1" --output downloaded_image.jpg
```
  
