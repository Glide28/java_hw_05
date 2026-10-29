# Домашнее задание №5 по Java

**Группа:** BHEMBD-25  
**Студент:** Арсений Паниклов

Тема: «Архитектура веб-приложения на Spring».

Spring Boot-приложение реализует REST API сообщений с in-memory хранилищем.
Архитектура разделена на контроллер, сервис, репозиторий и модель данных.

## Реализовано

- `GET /api/` — ответ `Hello, World!`;
- `GET /api/messages` — последние 30 сообщений;
- `POST /api/messages` — создание сообщения с текстом и автором;
- потокобезопасное хранилище на `ConcurrentHashMap`;
- генерация идентификаторов через `AtomicLong`;
- проверка обязательных полей и ответ `400 Bad Request`;
- интеграционные тесты Spring MVC.

## Требования

- Java 17 или новее;
- Gradle Wrapper включён в репозиторий.

## Сборка и тестирование

```powershell
.\gradlew.bat clean test
```

## Запуск

```powershell
.\gradlew.bat bootRun
```

Приложение доступно по адресу `http://localhost:8080/api`.

## Примеры запросов

```powershell
curl.exe http://localhost:8080/api/
curl.exe http://localhost:8080/api/messages

curl.exe -X POST http://localhost:8080/api/messages `
  -H "Content-Type: application/json" `
  -d '{\"content\":\"Новое сообщение\",\"author\":\"Арсений\"}'
```
