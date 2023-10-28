package aigt.finaccounts.biz.validation

import aigt.finaccounts.biz.AccountProcessor
import aigt.finaccounts.biz.fixture.getBaseTestFinAccountsContext
import aigt.finaccounts.common.models.account.AccountId
import aigt.finaccounts.common.models.command.ContextCommand
import aigt.finaccounts.common.models.state.ContextState
import aigt.finaccounts.common.models.transaction.TransactionAccountId
import aigt.finaccounts.stubs.SimpleAccountsStub
import aigt.finaccounts.stubs.TransactionStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationTransactTest {

    private val command = ContextCommand.TRANSACT
    private val withTransaction = true
    private val processor by lazy { AccountProcessor() }

    /*
    Тест валидации очистки полей
    */

    @Test
    fun cleanedDescription() =
        validationDescriptionCleaned(command, processor, withTransaction)

    @Test
    fun cleanedOwnerId() =
        validationOwnerIdCleaned(command, processor, withTransaction)

    @Test
    fun cleanedBalance() =
        validationBalanceCleaned(command, processor, withTransaction)

    @Test
    fun cleanedCurrency() =
        validationCurrencyCleaned(command, processor, withTransaction)

    @Test
    fun cleanedStatus() =
        validationStatusCleaned(command, processor, withTransaction)

    @Test
    fun cleanedLastTransaction() =
        validationLastTransactionCleaned(command, processor, withTransaction)

    @Test
    fun cleanedPermissionClient() =
        validationPermissionsClientCleaned(command, processor, withTransaction)

    @Test
    fun cleanedTransactionId() =
        validationTransactionIdCleaned(command, processor)

    @Test
    fun cleanedTransactionTimestamp() =
        validationTransactionTimestampCleaned(command, processor)

    /*
    Тест валидации поля: id
    */

    @Test
    fun correctId() =
        validationIdCorrect(command, processor, withTransaction)

    @Test
    fun emptyIdError() =
        validationIdEmptyError(command, processor, withTransaction)

    @Test
    fun badIdContentError() =
        validationIdContentError(command, processor, withTransaction)

    /*
    Тест валидации поля: transaction/description
    */

    @Test
    fun correctTransactionDescription() =
        validationTransactionDescriptionCorrect(command, processor)

    @Test
    fun trimTransactionDescription() =
        validationTransactionDescriptionTrim(command, processor)

    @Test
    fun fixSpacesTransactionDescription() =
        validationTransactionDescriptionFixSpaces(command, processor)

    @Test
    fun badSymbolsTransactionDescriptionError() =
        validationTransactionDescriptionSymbolsError(command, processor)

    /*
    Тест валидации поля: transaction/amount
    */

    @Test
    fun emptyTransactionAmountError() =
        validationTransactionAmountEmptyError(command, processor)

    /*
    Тест валидации поля: transaction/counterparty
    */

    @Test
    fun correctTransactionCounterparty() =
        validationTransactionCounterpartyCorrect(command, processor)

    @Test
    fun emptyTransactionCounterpartyError() =
        validationTransactionCounterpartyEmptyError(command, processor)

    @Test
    fun badTransactionCounterpartyContentError() =
        validationTransactionCounterpartyContentError(command, processor)

    /*
    Тест валидации поля: transaction/type
    */

    @Test
    fun emptyTransactionTypeError() =
        validationTransactionTypeEmptyError(command, processor)

    /*
    Тест синхронизации id аккаунта и транзакции
    */

    @Test
    fun syncronizationIdAccountAndTransaction() = runTest {
        val idString = "12344321000055556789"
        val ctx = getBaseTestFinAccountsContext(command).apply {
            accountRequest = SimpleAccountsStub.SIMPLE_ACTIVE_ACCOUNT.apply {
                id = AccountId(idString)
            }
            transactionRequest =
                TransactionStub.getTransactActionTransactionStub().apply {
                    accountId = TransactionAccountId.NONE
                }
        }
        processor.exec(ctx)

        assertEquals(
            0,
            ctx.errors.size,
            message = "${ctx.transactionValidated.accountId.asString()} should not have any errors, but have: ${ctx.errors}",
        )
        assertNotEquals(ContextState.FAILING, ctx.state)
        assertEquals(idString, ctx.transactionValidated.accountId.asString())
    }

}

