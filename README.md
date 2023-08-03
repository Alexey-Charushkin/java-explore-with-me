# Explore-with-me

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

### О проекте

Backend сервиса афиши ExploreWithMe.

В этой афише можно предложить какое-либо событие от выставки до похода в кино и собрать компанию для участия в нём.
Разработано на базе Spring Boot и Hibernate ORM.
Приложение состоит из 2-х сервисов.
Основной сервис содержит всё необходимое для работы продукта.
Сервис статистики хранит количество просмотров и позволит делать различные выборки для анализа работы приложения.

_____

### Функциональность

Основное приложение

+ **admin**
    + поддерживается:
        + Добавление нового пользователя
        + Получение информации о пользователях
        + Удаление пользователя по идентификатору
        + Добавление новой категории
        + Изменение категории
        + Удаление категории
        + Поиск событий
        + Редактирование данных события и его статуса (отклонение/публикация)
        + Добавление новой подборки событий
        + Обновление информации о подборке событий
        + Удаление подборки событий
        + Изменение статуса комментария (отклонение/публикация)
        + Получене списка комментариев по пользователям с дополнительными параметрами
        + Получение списка комметариев по событиям с дополнительными параметрами

+ **private**
    + поддерживается:
        + Добавление нового события
        + Изменение события добавленного текущим пользователем
        + Изменение статуса (подтверждена отменена) заявок на участие в событии текущего пользователя
        + Получение событий добавленных текущим пользователям
        + Получение полной информации о событии добавленном текущим пользователем
        + Получение информации о запросах на участие в событии текущего пользователя
        + Добавление запроса от текущего пользователя на участие в событии
        + Отмена своего запроса на участие в событии
        + Получение информации о заявках текущего пользователя на участие в чужых событиях
        + Добавление нового комментария
        + Обновление комментария созданного текущим пользователем
        + Удаление комментария созданного текущим пользователем
        + Получение комментария по идентификатору пользователя и идентификатору события
        + Получение списка комментариев по идентификатору пользователя и дополнительным параметрам

+ **public**
    + поддерживается:
        + получение события с возможностью фильтрации
        + Получение подробной информации об опубликованном событии по его идентификатору
        + Получение категорий
        + Получение информации о категории по её идентификатору
        + Получение подборок событий
        + Получение подборки событий по её идентификатору
        + Получение комментария по его идентификатору
        + Получение списка комментариев по идентификатору события и дополнительным параметрам

Сервис статистики

+ Сохранение информации о том, что к эндпойнту был запрос
+ Получение списка стаистики по посещения с дополнительными параметрами

----- 
Спецификации API

+ **Спецификации API находяться в файлах проекта:**
    + ewm-main-service-spec.json
    + ewm-stats-service-spec.json

-----

### Стек технологий

Технологии которые были использованы в проекте:

+ Java
+ Spring Boot
+ Hibernate ORM
+ Maven
+ Git
+ Lombok
+ PostgreSQL
+ Docker
+ Postman

-----

### Системные требования

+ Java 11 и выше
+ Spring Boot 3.1
+ PostgreSQL 15
+ Apache Maven 3.9 и выше
+ Docker 24.0.4 и выше (Опционально при развертывании проекта в докер контейнере)

-----

### Установка и запуск

В зависимости от типа установки в клиенте статистики
расположенного по пути: "src/main/java/ru/practicum/client/StatisticsWebClient.java"
используется разный URI сервера статистики

+ для обычной установки: "http://localhost:9090"
+ для установки в docker контейнер: "http://stats-server:9090"

**без использования докера:**

+ Создайте сборку модуля main с помощью Maven
+ запустите файл main-0.0.1-SNAPSHOT.jar

**в докер контейнере:**

+ Создайте сборку с помощью Maven
+ запустите файл docker-compose.yml

-----

### Проверка работы

В папке проекта "postman" находяться коллекции тестов Postman

_____
**разработчик:**

+ [Alexey Charushkin](https://github.com/Alexey-Charushkin)

