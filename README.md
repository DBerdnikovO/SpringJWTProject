# Spring security with JWT token

## Обзор

Java Spring Security проект, который демонстрирует работу с jwt токенами. Позволяет регистрировать новых пользователей,
выполнять вход уже зарегистрированных и предоставлять доступ к ресурсам в зависимости от роли пользователя

| Spring Security app with JWT auth | Spring-Boot |
|-----------------------------------|-------------|

## Содержание

- [Требования](#требования)
- [Быстрая настройка](#быстрая-настройка)
- [Функционал](#функционал)
- [Документация](#документация)

## Требования

- Java 11 или выше
- Spring Boot
- Spring Security
- Postgres DB

## Быстрая настройка

* Скачайте проект

```sh
  git clone https://github.com/DBerdnikovO/SpringJWTProject.git
```

* Выполните команду

```sh
  mvn clean install
```

* Добавьте зависимость в проект файл `env.properties` со следующими параметрами:

```properties
  SERVER_PORT=
  
  DATABASE_URL=
  DATABASE_PASSWORD=
  DATABASE_USER=
```

* Запустите проект

## Функционал
Приложение предоставляет функционал для аутентификации, авторизации и управления доступом пользователей. Оно состоит из трёх основных контроллеров:

### AuthController:
Обрабатывает регистрацию пользователей, вход в систему и обновление токенов аутентификации.
Позволяет пользователям регистрироваться, логиниться и обновлять свои токены доступа.

### AdminController:
Управляет операциями, доступными только администраторам.
Обеспечивает доступ к специфическим административным функциям.

### UserController:
Управляет операциями, доступными только авторизованным пользователям с ролью "USER".
Обеспечивает доступ к функциям, предназначенным для обычных пользователей.

## Документация
Документация доступна по ссылке [http://localhost:8081/swagger-ui/index.html#/](http://localhost:8081/swagger-ui/index.html#/), где на месте 8081 указывайте порт, на котором приложение запущено.