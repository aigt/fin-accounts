package aigt.finaccounts.biz

import aigt.finaccounts.biz.groups.operation
import aigt.finaccounts.biz.groups.stubs
import aigt.finaccounts.biz.workers.initStatus
import aigt.finaccounts.biz.workers.stubCreateSuccess
import aigt.finaccounts.biz.workers.stubDbError
import aigt.finaccounts.biz.workers.stubHistorySuccess
import aigt.finaccounts.biz.workers.stubNoCase
import aigt.finaccounts.biz.workers.stubOffersSuccess
import aigt.finaccounts.biz.workers.stubReadSuccess
import aigt.finaccounts.biz.workers.stubSearchSuccess
import aigt.finaccounts.biz.workers.stubUpdateSuccess
import aigt.finaccounts.biz.workers.stubValidationBadDescription
import aigt.finaccounts.biz.workers.stubValidationBadId
import aigt.finaccounts.biz.workers.stubValidationBadOwnerId
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
        private val BusinessChain = rootChain<FinAccountsContext> {
            initStatus("Инициализация статуса")

            operation("Создание аккаунта", ContextCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadOwnerId("Имитация ошибки валидации владельца счёта")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }

            operation("Получить аккаунт", ContextCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }

            operation("Изменить аккаунт", ContextCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadOwnerId("Имитация ошибки валидации владельца счёта")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }

            operation("История транзакций по счёту", ContextCommand.HISTORY) {
                stubs("Обработка стабов") {
                    stubHistorySuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }

            operation("Поиск аккаунтов", ContextCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }

            }

            operation(
                "Добавление транзакции по счёту",
                ContextCommand.TRANSACT,
            ) {
                stubs("Обработка стабов") {
                    stubOffersSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }
        }.build()
    }
}
