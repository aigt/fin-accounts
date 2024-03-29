package aigt.finaccounts.blackbox.test.action.v1.jvm

import aigt.finaccounts.api.v1.jackson.models.AccountCreateResponse
import aigt.finaccounts.api.v1.jackson.models.AccountHistoryResponse
import aigt.finaccounts.api.v1.jackson.models.AccountReadResponse
import aigt.finaccounts.api.v1.jackson.models.AccountResponseObject
import aigt.finaccounts.api.v1.jackson.models.AccountSearchResponse
import aigt.finaccounts.api.v1.jackson.models.AccountTransactResponse
import aigt.finaccounts.api.v1.jackson.models.AccountUpdateResponse
import aigt.finaccounts.api.v1.jackson.models.IResponse
import aigt.finaccounts.api.v1.jackson.models.ResponseResult
import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.and

fun haveResult(result: ResponseResult) = Matcher<IResponse> {
    MatcherResult(
        it.result == result,
        { "actual result ${it.result} but we expected $result" },
        { "result should not be $result" },
    )
}

val haveNoErrors = Matcher<IResponse> {
    MatcherResult(
        it.errors.isNullOrEmpty(),
        { "actual errors ${it.errors} but we expected no errors" },
        { "errors should not be empty" },
    )
}

fun haveError(code: String) = haveResult(ResponseResult.ERROR)
    .and(
        Matcher<IResponse> {
            MatcherResult(
                it.errors?.firstOrNull { e -> e.code == code } != null,
                { "actual errors ${it.errors} but we expected error with code $code" },
                { "errors should not contain $code" },
            )
        },
    )

val haveSuccessResult = haveResult(ResponseResult.SUCCESS) and haveNoErrors

val IResponse.account: AccountResponseObject?
    get() = when (this) {
        is AccountCreateResponse -> account
        is AccountReadResponse -> account
        is AccountUpdateResponse -> account
        is AccountSearchResponse -> account
        is AccountHistoryResponse -> account
        is AccountTransactResponse -> account
        else -> throw IllegalArgumentException("Invalid response type: ${this::class}")
    }

