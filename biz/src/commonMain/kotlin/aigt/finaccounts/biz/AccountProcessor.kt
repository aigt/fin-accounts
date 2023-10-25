package aigt.finaccounts.biz

import aigt.finaccounts.biz.groups.operation
import aigt.finaccounts.biz.groups.stubs
import aigt.finaccounts.biz.validation.clean.validateIdContent
import aigt.finaccounts.biz.validation.clean.validatingCleanBalance
import aigt.finaccounts.biz.validation.clean.validatingCleanCurrency
import aigt.finaccounts.biz.validation.clean.validatingCleanDescription
import aigt.finaccounts.biz.validation.clean.validatingCleanId
import aigt.finaccounts.biz.validation.clean.validatingCleanLastTransactionTime
import aigt.finaccounts.biz.validation.clean.validatingCleanOwnerId
import aigt.finaccounts.biz.validation.clean.validatingCleanPermissionsClient
import aigt.finaccounts.biz.validation.clean.validatingCleanStatus
import aigt.finaccounts.biz.validation.clean.validatingTrimDescription
import aigt.finaccounts.biz.validation.finishAccountFilterValidation
import aigt.finaccounts.biz.validation.finishAccountValidation
import aigt.finaccounts.biz.validation.finishTransactionValidation
import aigt.finaccounts.biz.validation.validateCurrencyNotEmpty
import aigt.finaccounts.biz.validation.validateDescriptionContent
import aigt.finaccounts.biz.validation.validateIdNotEmpty
import aigt.finaccounts.biz.validation.validateOwnerIdContent
import aigt.finaccounts.biz.validation.validateOwnerIdNotEmpty
import aigt.finaccounts.biz.validation.validatingCopyAccount
import aigt.finaccounts.biz.validation.validatingCopyFilter
import aigt.finaccounts.biz.validation.validatingCopyTransaction
import aigt.finaccounts.biz.validation.validation
import aigt.finaccounts.biz.workers.initStatus
import aigt.finaccounts.biz.workers.stubCreateSuccess
import aigt.finaccounts.biz.workers.stubDbError
import aigt.finaccounts.biz.workers.stubHistorySuccess
import aigt.finaccounts.biz.workers.stubNoCase
import aigt.finaccounts.biz.workers.stubReadSuccess
import aigt.finaccounts.biz.workers.stubSearchSuccess
import aigt.finaccounts.biz.workers.stubTransactSuccess
import aigt.finaccounts.biz.workers.stubUpdateSuccess
import aigt.finaccounts.biz.workers.stubValidationBadBalance
import aigt.finaccounts.biz.workers.stubValidationBadCurrency
import aigt.finaccounts.biz.workers.stubValidationBadDescription
import aigt.finaccounts.biz.workers.stubValidationBadId
import aigt.finaccounts.biz.workers.stubValidationBadOwnerId
import aigt.finaccounts.biz.workers.stubValidationBadOwnerIdFilter
import aigt.finaccounts.biz.workers.stubValidationBadSearchStringFilter
import aigt.finaccounts.biz.workers.stubValidationBadStatus
import aigt.finaccounts.biz.workers.stubValidationBadTransactionAmount
import aigt.finaccounts.biz.workers.stubValidationBadTransactionCounterparty
import aigt.finaccounts.biz.workers.stubValidationBadTransactionDescription
import aigt.finaccounts.biz.workers.stubValidationBadTransactionType
import aigt.finaccounts.common.FinAccountsContext
import aigt.finaccounts.common.FinAccountsCorSettings
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.cor.rootChain

class AccountProcessor(
    @Suppress("unused")
    private val corSettings: FinAccountsCorSettings = FinAccountsCorSettings.NONE,
) {
    suspend fun exec(ctx: FinAccountsContext) = BusinessChain.exec(ctx)

    companion object {
        private val BusinessChain = rootChain {
            initStatus("Инициализация статуса")

            operation("Создание аккаунта", ContextCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadOwnerId("Имитация ошибки валидации владельца счёта")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubValidationBadCurrency("Имитация ошибки валидации валюты счёта")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    // Подготовка к валидации
                    validatingCopyAccount("Копируем поля в accountValidating")

                    // Очистка неиспользуемых в комманде полей
                    validatingCleanId("Очистка id")
                    validatingCleanBalance("Очистка баланса")
                    validatingCleanStatus("Очистка статуса")
                    validatingCleanLastTransactionTime("Очистка времени последней транзакции")
                    validatingCleanPermissionsClient("Очистка разрешений")

                    // Корректировка полей
                    validatingTrimDescription("Очистка пустых символов в начале и конце описания")

                    // Валидация данных
                    validateOwnerIdNotEmpty("Проверка, что идентификатор владельца счёта не пуст")
                    validateOwnerIdContent("Проверка идентификатора владельца счёта")
                    validateCurrencyNotEmpty("Проверка, что указана валюта счёта")
                    validateDescriptionContent("Проверка описания")

                    // Завершение валидации
                    finishAccountValidation("Завершение проверок")
                }
            }

            operation("Получить аккаунт", ContextCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    // Подготовка к валидации
                    validatingCopyAccount("Копируем поля в accountValidating")

                    // Очистка неиспользуемых в комманде полей
                    validatingCleanDescription("Очистка описаия")
                    validatingCleanOwnerId("Очистка идентификатора владельца счёта")
                    validatingCleanBalance("Очистка баланса")
                    validatingCleanCurrency("Очистка валюты счёта")
                    validatingCleanStatus("Очистка статуса")
                    validatingCleanLastTransactionTime("Очистка времени последней транзакции")
                    validatingCleanPermissionsClient("Очистка разрешений")

                    // Валидация данных
                    validateIdNotEmpty("Проверка, что идентификатор счёта не пуст")
                    validateIdContent("Проверка идентификатора")

                    // Завершение валидации
                    finishAccountValidation("Завершение проверок")
                }
            }

            operation("Изменить аккаунт", ContextCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadOwnerId("Имитация ошибки валидации владельца счёта")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubValidationBadCurrency("Имитация ошибки валидации валюты счёта")
                    stubValidationBadBalance("Имитация ошибки валидации баланса счёта")
                    stubValidationBadStatus("Имитация ошибки валидации статуса счёта")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    // Подготовка к валидации
                    validatingCopyAccount("Копируем поля в accountValidating")

                    // Очистка неиспользуемых в комманде полей
                    validatingCleanLastTransactionTime("Очистка времени последней транзакции")
                    validatingCleanPermissionsClient("Очистка разрешений")

                    // Корректировка полей
                    validatingTrimDescription("Очистка пустых символов в начале и конце описания")

                    // Валидация данных
                    validateIdNotEmpty("Проверка, что идентификатор счёта не пуст")
                    validateIdContent("Проверка идентификатора")
                    validateDescriptionContent("Проверка описания")
                    validateOwnerIdNotEmpty("Проверка, что идентификатор владельца счёта не пуст")
                    validateOwnerIdContent("Проверка идентификатора владельца счёта")
                    validateCurrencyNotEmpty("Проверка, что указана валюта счёта")

                    // Завершение валидации
                    finishAccountValidation("Завершение проверок")
                }
            }

            operation("История транзакций по счёту", ContextCommand.HISTORY) {
                stubs("Обработка стабов") {
                    stubHistorySuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    // Подготовка к валидации
                    validatingCopyAccount("Копируем поля в accountValidating")

                    // Очистка неиспользуемых в комманде полей
                    validatingCleanDescription("Очистка описаия")
                    validatingCleanOwnerId("Очистка идентификатора владельца счёта")
                    validatingCleanBalance("Очистка баланса")
                    validatingCleanCurrency("Очистка валюты счёта")
                    validatingCleanStatus("Очистка статуса")
                    validatingCleanLastTransactionTime("Очистка времени последней транзакции")
                    validatingCleanPermissionsClient("Очистка разрешений")

                    // Валидация данных
                    validateIdNotEmpty("Проверка, что идентификатор счёта не пуст")
                    validateIdContent("Проверка идентификатора")

                    // Завершение валидации
                    finishAccountValidation("Завершение проверок")
                }
            }

            operation("Поиск аккаунтов", ContextCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadSearchStringFilter("Имитация ошибки валидации строки поиска")
                    stubValidationBadOwnerIdFilter("Имитация ошибки валидации строки поиска по владельцу счёта")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    // Подготовка к валидации
                    validatingCopyFilter("Копируем фильтр в accountFilterValidating")

                    // Очистка неиспользуемых в комманде полей
                    // validatingCleanId("Очистка id")

                    // Валидация данных
                    // validateOwnerIdNotEmpty("Проверка, что идентификатор владельца счёта не пуст")

                    // Завершение валидации
                    finishAccountFilterValidation("Завершение проверок")
                }
            }

            operation(
                "Добавление транзакции по счёту",
                ContextCommand.TRANSACT,
            ) {
                stubs("Обработка стабов") {
                    stubTransactSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadTransactionAmount("Имитация ошибки суммы транзакции")
                    stubValidationBadTransactionCounterparty("Имитация ошибки валидации счёта контрагента транзакции")
                    stubValidationBadTransactionType("Имитация ошибки валидации типа транзакции")
                    stubValidationBadTransactionDescription("Имитация ошибки валидации описания транзакции")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    // Подготовка к валидации
                    validatingCopyAccount("Копируем поля в accountValidating")
                    validatingCopyTransaction("Копируем поля в transactionValidating")

                    // Очистка неиспользуемых в комманде полей
                    validatingCleanDescription("Очистка описаия")
                    validatingCleanOwnerId("Очистка идентификатора владельца счёта")
                    validatingCleanBalance("Очистка баланса")
                    validatingCleanCurrency("Очистка валюты счёта")
                    validatingCleanStatus("Очистка статуса")
                    validatingCleanLastTransactionTime("Очистка времени последней транзакции")
                    validatingCleanPermissionsClient("Очистка разрешений")

                    // Валидация данных
                    validateIdNotEmpty("Проверка, что идентификатор счёта не пуст")
                    validateIdContent("Проверка идентификатора")

                    // Завершение валидации
                    finishAccountValidation("Завершение проверок счёта")
                    finishTransactionValidation("Завершение проверок транзакции")
                }
            }
        }.build()
    }
}
