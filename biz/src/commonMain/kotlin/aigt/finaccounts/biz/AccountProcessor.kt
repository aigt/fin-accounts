package aigt.finaccounts.biz

import aigt.finaccounts.biz.groups.operation
import aigt.finaccounts.biz.groups.stubs
import aigt.finaccounts.biz.validation.clean.validatingCleanBalance
import aigt.finaccounts.biz.validation.clean.validatingCleanCurrency
import aigt.finaccounts.biz.validation.clean.validatingCleanDescription
import aigt.finaccounts.biz.validation.clean.validatingCleanId
import aigt.finaccounts.biz.validation.clean.validatingCleanLastTransactionTime
import aigt.finaccounts.biz.validation.clean.validatingCleanOwnerId
import aigt.finaccounts.biz.validation.clean.validatingCleanPermissionsClient
import aigt.finaccounts.biz.validation.clean.validatingCleanStatus
import aigt.finaccounts.biz.validation.clean.validatingCleanTransactionId
import aigt.finaccounts.biz.validation.clean.validatingCleanTransactionTimestamp
import aigt.finaccounts.biz.validation.clean.validatingCopyTransactionAccountId
import aigt.finaccounts.biz.validation.clean.validatingFixSpacesInDescription
import aigt.finaccounts.biz.validation.clean.validatingFixSpacesInTransactionDescription
import aigt.finaccounts.biz.validation.clean.validatingTrimDescription
import aigt.finaccounts.biz.validation.clean.validatingTrimTransactionDescription
import aigt.finaccounts.biz.validation.finishAccountFilterValidation
import aigt.finaccounts.biz.validation.finishAccountValidation
import aigt.finaccounts.biz.validation.finishTransactionValidation
import aigt.finaccounts.biz.validation.validateCurrencyNotEmpty
import aigt.finaccounts.biz.validation.validateDescriptionContent
import aigt.finaccounts.biz.validation.validateFilterNotEmpty
import aigt.finaccounts.biz.validation.validateFilterOwnerIdContent
import aigt.finaccounts.biz.validation.validateFilterSearchStringContent
import aigt.finaccounts.biz.validation.validateIdContent
import aigt.finaccounts.biz.validation.validateIdNotEmpty
import aigt.finaccounts.biz.validation.validateOwnerIdContent
import aigt.finaccounts.biz.validation.validateOwnerIdNotEmpty
import aigt.finaccounts.biz.validation.validateTransactionAmountNotEmpty
import aigt.finaccounts.biz.validation.validateTransactionCounterpartyContent
import aigt.finaccounts.biz.validation.validateTransactionCounterpartyNotEmpty
import aigt.finaccounts.biz.validation.validateTransactionDescriptionContent
import aigt.finaccounts.biz.validation.validateTransactionTypeNotEmpty
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
import aigt.finaccounts.biz.workers.stubValidationBadDescription
import aigt.finaccounts.biz.workers.stubValidationBadId
import aigt.finaccounts.biz.workers.stubValidationBadOwnerId
import aigt.finaccounts.biz.workers.stubValidationBadOwnerIdFilter
import aigt.finaccounts.biz.workers.stubValidationBadSearchStringFilter
import aigt.finaccounts.biz.workers.stubValidationBadTransactionCounterparty
import aigt.finaccounts.biz.workers.stubValidationBadTransactionDescription
import aigt.finaccounts.biz.workers.stubValidationEmptyCurrency
import aigt.finaccounts.biz.workers.stubValidationEmptyFilter
import aigt.finaccounts.biz.workers.stubValidationEmptyId
import aigt.finaccounts.biz.workers.stubValidationEmptyOwnerId
import aigt.finaccounts.biz.workers.stubValidationEmptyTransactionAmount
import aigt.finaccounts.biz.workers.stubValidationEmptyTransactionCounterparty
import aigt.finaccounts.biz.workers.stubValidationEmptyTransactionType
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
                    stubValidationEmptyOwnerId("Имитация ошибки валидации наличия владельца счёта")
                    stubValidationEmptyCurrency("Имитация ошибки валидации наличия валюты счёта")
                    stubValidationBadOwnerId("Имитация ошибки валидации владельца счёта")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
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
                    validatingFixSpacesInDescription("Очистка повторяющихся пробелов и замена пробельных символов на стандартный")

                    // Проверка наличия обязательных полей
                    validateOwnerIdNotEmpty("Проверка, что идентификатор владельца счёта не пуст")
                    validateCurrencyNotEmpty("Проверка, что указана валюта счёта")

                    // Валидация данных
                    validateOwnerIdContent("Проверка идентификатора владельца счёта")
                    validateDescriptionContent("Проверка описания")

                    // Завершение валидации
                    finishAccountValidation("Завершение проверок")
                }
            }

            operation("Получить аккаунт", ContextCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationEmptyId("Имитация ошибки валидации наличия id")
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

                    // Проверка наличия обязательных полей
                    validateIdNotEmpty("Проверка, что идентификатор счёта не пуст")

                    // Валидация данных
                    validateIdContent("Проверка идентификатора")

                    // Завершение валидации
                    finishAccountValidation("Завершение проверок")
                }
            }

            operation("Изменить аккаунт", ContextCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationEmptyId("Имитация ошибки валидации наличия id")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubValidationBadOwnerId("Имитация ошибки валидации владельца счёта")
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
                    validatingFixSpacesInDescription("Очистка повторяющихся пробелов и замена пробельных символов на стандартный")

                    // Проверка наличия обязательных полей
                    validateIdNotEmpty("Проверка, что идентификатор счёта не пуст")

                    // Валидация данных
                    validateIdContent("Проверка идентификатора")
                    validateDescriptionContent("Проверка описания")
                    validateOwnerIdContent("Проверка идентификатора владельца счёта")

                    // Завершение валидации
                    finishAccountValidation("Завершение проверок")
                }
            }

            operation("История транзакций по счёту", ContextCommand.HISTORY) {
                stubs("Обработка стабов") {
                    stubHistorySuccess("Имитация успешной обработки")
                    stubValidationEmptyId("Имитация ошибки валидации наличия id")
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

                    // Проверка наличия обязательных полей
                    validateIdNotEmpty("Проверка, что идентификатор счёта не пуст")

                    // Валидация данных
                    validateIdContent("Проверка идентификатора")

                    // Завершение валидации
                    finishAccountValidation("Завершение проверок")
                }
            }

            operation("Поиск аккаунтов", ContextCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationEmptyFilter("Имитация ошибки валидации наличия id")
                    stubValidationBadSearchStringFilter("Имитация ошибки валидации строки поиска")
                    stubValidationBadOwnerIdFilter("Имитация ошибки валидации строки поиска по владельцу счёта")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    // Подготовка к валидации
                    validatingCopyFilter("Копируем фильтр в accountFilterValidating")

                    // Проверка наличия обязательных полей
                    validateFilterNotEmpty("Проверка, что фильтр не пуст")

                    // Валидация данных
                    validateFilterSearchStringContent("Проверка строки поиска")
                    validateFilterOwnerIdContent("Проверка идентификатора")

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
                    stubValidationEmptyId("Имитация ошибки валидации наличия id")
                    stubValidationEmptyTransactionAmount("Имитация ошибки валидации наличия id")
                    stubValidationEmptyTransactionCounterparty("Имитация ошибки валидации наличия id")
                    stubValidationEmptyTransactionType("Имитация ошибки валидации наличия id")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadTransactionCounterparty("Имитация ошибки валидации счёта контрагента транзакции")
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
                    validatingCleanTransactionId("Очистка идентификатора транзакции")
                    validatingCleanTransactionTimestamp("Очистка метки времени транзакции")

                    // Корректировка полей
                    validatingTrimTransactionDescription("Очистка пустых символов в начале и конце описания")
                    validatingFixSpacesInTransactionDescription("Очистка повторяющихся пробелов и замена пробельных символов на стандартный")

                    // Проверка наличия обязательных полей
                    validateIdNotEmpty("Проверка, что идентификатор счёта не пуст")
                    validateTransactionAmountNotEmpty("Проверка, что сумма транзакции указана")
                    validateTransactionCounterpartyNotEmpty("Проверка, что идентификатор счёта ответного контрагента по операции не пуст")
                    validateTransactionTypeNotEmpty("Проверка, что тип транзакции указан")

                    // Валидация данных
                    validateIdContent("Проверка идентификатора")
                    validateTransactionCounterpartyContent("Проверка счёта ответного контрагента")
                    validateTransactionDescriptionContent("Проверка содержимого описания транзакции")

                    // Синхронизация аккаунта и транзакции
                    validatingCopyTransactionAccountId("Копирование номера счёта в транзакцию")

                    // Завершение валидации
                    finishAccountValidation("Завершение проверок счёта")
                    finishTransactionValidation("Завершение проверок транзакции")
                }
            }
        }.build()
    }
}
