package aigt.finaccounts.common.models.stubcase

enum class ContextStubCase {
    NONE,
    SUCCESS,
    NOT_FOUND,
    EMPTY_ID,
    EMPTY_OWNER_ID,
    EMPTY_CURRENCY,
    EMPTY_FILTER,
    EMPTY_TRANSACTION_AMOUNT,
    EMPTY_TRANSACTION_COUNTERPARTY,
    EMPTY_TRANSACTION_TYPE,
    BAD_ID,
    BAD_DESCRIPTION,
    BAD_OWNER_ID,
    BAD_BALANCE,
    BAD_CURRENCY,
    BAD_LAST_TRANSACTION,
    BAD_STATUS,
    BAD_TRANSACTION_AMOUNT,
    BAD_TRANSACTION_COUNTERPARTY,
    BAD_TRANSACTION_DESCRIPTION,
    BAD_TRANSACTION_TYPE,
    BAD_SEARCH_STRING_FILTER,
    BAD_OWNER_ID_FILTER,
    DB_ERROR,
}
