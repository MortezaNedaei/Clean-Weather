package com.mooncascade.domain.interactor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Using UseCase class in domain layer to separate business logic from presenter,
 * [R] -> Response Model Object
 * [P] -> Request Parameters
 *
 */
abstract class UseCase<in P, out R>(
    private val coroutineDispatcher: CoroutineDispatcher
) {

    /**
     * Executes the UseCase asynchronously
     * @param parameters the input parameters to run the use case with
     */
    suspend operator fun invoke(parameters: P? = null): R = withContext(coroutineDispatcher) {
        execute(parameters)
    }

    /**
     * This is should be overridden by children to execute their special case
     * @param parameters the input parameters to run the use case with
     */
    protected abstract suspend fun execute(parameters: P?): R
}
