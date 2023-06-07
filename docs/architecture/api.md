# API

## Сущности

1. Account (счёт)
2. Transaction (транзакция)

## Описание сущности Account

1. Info
    1. Title (6-128 символов)
    2. Description (100-10000 символов)
    3. Owner (UserId)
2. Balance (Cents) - баланс
3. NumberId (набор цифр) - Цифровой идентификатор
4. LastUpdate (timestamp) - идентификатор модели товара
5. Currency (Код валюты) - Код валюты
6. Status (Active, Closed, Frozen) - Статус счёта

## Описание сущности Transaction

1. Value (Cents) - Сумма операции
2. Account (набор цифр) - Счёт операции
3. Сounterparty (набор цифр) - Счёт контрагента по операции
4. Time (timestamp) - Время операции
5. Type (Тип) - Тип операции (Зачислеие, списание)

## Функции (эндпониты)

1. Account CRUDS
    1. create
    2. read
    3. update
    4. history - отображение истории изменения счетов за период
2. Transaction CRUDS
    1. create
    2. read
    3. search - поиск по фильтрам

