package com.androida.currencyexchanger.domain.useCases

import com.androida.currencyexchanger.core.activity.handlers.SideEffectHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

abstract class BaseUseCase<T, in Params> {

    @Inject
    lateinit var sideEffectHandler: SideEffectHandler

    abstract suspend fun run(params: Params): T

    open operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        withLoader: Boolean = false,
        onSuccess: (T) -> Unit
    ) {
        sideEffectHandler.apply {
            sideEffectHandler.execute(scope = scope, withLoader = withLoader) {
                val response = withContext(scope.coroutineContext) {
                    run(params)
                }
                onSuccess(response)
            }
        }
    }
}