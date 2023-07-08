package aigt.finaccounts.mappers.jvm.v1.exceptions

class UnknownRequestClass(clazz: Class<*>) :
    RuntimeException("Class $clazz cannot be mapped to FinAccountsContext")
