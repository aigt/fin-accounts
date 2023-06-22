package aigt.finaccounts.common.models.accountfilter


data class AccountFilter(
    var searchString: SearchStringFilter = SearchStringFilter.NONE,
    var ownerId: OwnerIdFilter = OwnerIdFilter.NONE,
)
