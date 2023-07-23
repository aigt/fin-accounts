# Приемочные тесты

## Целевое решение

C использованием docker-compose запускаются наши приложения
(spring, ktor, kafka и т.п.), поднимается база данных и
тестируются обе версии API путем отправки соответствующих
запросов и проверки ответов.

Проект зависит только от транспортных моделей (чтобы было удобно отправлять
запросы и проверять ответы).

## Roadmap

1. [x] Оснастка и проверка на Wiremock
2. [ ] Только spring и без БД
3. [ ] Добавляем Ktor
4. [ ] Добавляем Rabbit
5. [ ] Добавляем Kafka
6. [ ] При появлении работы с БД
    * [ ] раскомментируем и убираем все // TODO
    * [ ] в каждое из приложений выше добавляем БД
    * [ ] добавляем очистку БД

## Организация проекта

* [docker](./src/test/kotlin/docker) - обертки над соответствующими
  docker-compose
* [fixture](./src/test/kotlin/fixture) - оснастка
    * [docker](./src/test/kotlin/fixture/docker) - оснастка для docker
    * [client](./src/test/kotlin/fixture/client) - клиенты для разных
      протоколов
* [test](./src/test/kotlin/test) - сами тесты
    * [AccRestTest](./src/test/kotlin/test/AccRestTest.kt) - запуск http/rest
      тестов для spring и ktor
    * `testVx` - тесты для соотв. версии АПИ
    * `action.vx` - примитивные действия для тестов и вспомогательные matcher-ы

## Поведение тестов

* Тесты разбиты на отдельные классы (наследники BaseFunSpec, который основан на
  FunSpec).
* Перед запуском тестов в классе поднимается соответствующий docker-compose
* После завершения тестов в классе docker-compose завершается
* Перед каждым тестом выполняется очистка базы данных, чтобы сделать тесты
  независимыми друг от друга

# Запуск в Ubuntu с docker compose v2

Для запуска тестов можно создать alias docker-compose:

1. Создайте файл `/bin/docker-compose`
    ```bash
    sudo nano /bin/docker-compose
    ```
2. Впишите:
    ```
    docker compose "$@"
    ```
3. Сохраните и закройте
4. Добавьте права на запуск файла:
   ```bash
   sudo chmod +x /bin/docker-compose
   ```
5. Проверить
   ```bash
   docker-compose version
   ```
