# Система учета финансов

Система учёта автоматизирует учёт счетов и работу с историей их изменений.

Задачи системы учета финансов:

- хранение актуального состояния счетов
- хранение истории изменения счетов,
- возможность получения отчетов по изменениям счетов за период

## Документация

1. Маркетинг
    1. [Заинтересанты](./docs/marketing/stakeholders.md)
    2. [Целевая аудитория](./docs/marketing/target-audience.md)
    3. [Описание MVP](./docs/marketing/mvp.md)
2. DevOps
    1. [Схема инфраструктуры](./docs/devops/infrastruture.md)
    2. [Схема мониторинга](./docs/devops/monitoring.md)
3. Тесты
4. Архитектура
    1. [Компонентная схема](./docs/architecture/architecture.md)
    2. [Интеграционная схема](./docs/architecture/integration.md)
    3. [Описание API](./docs/architecture/api.md)
5. Пользовательский интерфейс
    1. [Фронтенд-представление](./docs/UI/frontend.md)

# Структура проекта

1. [acceptance](acceptance) - Приемочные тесты
2. [api-v1-jackson](api-v1-jackson) - Транспортные модели для сериализации с
   помощью jackson
3. [api-v1-kmp](api-v1-kmp) - Транспортные модели для сериализации для
   использования в kotlin multiplatform
4. [biz](biz) - Бизнес-логиа
5. [common](common) - Общие объекты используемые всеми остальными модулями (с
   поддержкой kotlin multiplatform)
6. [finaccount-app-ktor](finaccount-app-ktor) - Приложение
6. [finaccount-app-kafka](finaccount-app-kafka) - Приложение с асинхронным
   транспортным протоколом использующее kafka
7. [mappers-v1-jvm](mappers-v1-jvm) - Мапперы транспортных моделей во
   внутренние для jvm рантайма
8. [mappers-v1-kmp](mappers-v1-kmp) - Мапперы транспортных моделей во
   внутренние с поддержкой kotlin multiplatform
9. [deploy](deploy) - Инфраструктура развёртывания приложения
10. [docs](docs) - Документация
11. [specs](specs) - OpenAPI спецификации с описанием API
12. [stubs](stubs) - Заглушки, отдающие фейковый результат на запрос
