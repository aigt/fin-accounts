# API

## Сущности

1. Account (счёт)
2. Transaction (транзакция)

## Описание сущности Account

1. Id (набор 20 цифр) - Цифровой идентификатор счёта
2. Description (0-10000 символов) - Описание счёта
3. OwnerId (UUID) - Идентификатор владельца счёта
4. Balance (Cents) - баланс
5. Currency (строка 3 символа) - ISO 4217 код валюты, например: USD, EUR, CHF
6. LastTransaction (timestamp) - Дата и время последней операции по счёту
7. Status (Active, Closed, Frozen) - Статус счёта

## Описание сущности Transaction

1. Amount (Cents) - Сумма операции
2. Сounterparty (набор цифр) - Счёт контрагента по операции
3. Timestamp (timestamp) - Время операции
4. Type (Income, Withdraw) - Тип опирации (списание, зачисление)
5. Description (0-10000 символов) - Описание, примечание к операции

## Функции (эндпониты)

1. Account CRUDS
    1. create
    2. read
    3. update
    4. search
    5. history - отображение истории изменения счетов за период
    6. transact - добвить операцию по счёту

