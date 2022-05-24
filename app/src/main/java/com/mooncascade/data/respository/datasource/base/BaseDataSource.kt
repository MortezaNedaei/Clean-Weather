package com.mooncascade.data.respository.datasource.base

import android.util.Log
import com.mooncascade.common.extensions.TAG
import com.mooncascade.data.respository.datasource.base.BaseDataSource.ErrorType.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

abstract class BaseDataSource {


    /**
     * received the api call response and return the flow of the response with success and failure status
     */
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Flow<Result<T>> {
        return flow {
            try {
                val response = call.invoke()
                Log.d(TAG, "getResult: code:${response.code()} data:${response.body()}")
                if (response.isSuccessful) {
                    emit(Result.success(response.body()!!))
                } else {
                    emit(
                        Result.failure(getErrorUsingCode(response))
                    )
                }
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    /**
     * @return proper [Exception] based on the http error code
     */
    private fun <T> getErrorUsingCode(response: Response<T>): Exception = with(response) {
        val errorCode = this.code()
        val defaultMessage = "Network Error: ${this.message()} "
        val errorMessage: String = when (errorCode) {
            in NOT_FOUND.errorCode -> NOT_FOUND.errorMessage
            in BAD_REQUEST.errorCode -> BAD_REQUEST.errorMessage
            in INTERNAL_SERVER.errorCode -> INTERNAL_SERVER.errorMessage
            else -> UNKNOWN_ERROR.errorMessage
        }
        return Exception(defaultMessage.plus(errorMessage))
    }

    /**
     * specifies different types of Errors by error codes and error messages
     */
    enum class ErrorType(val errorCode: IntRange, val errorMessage: String) {
        NOT_FOUND(404..404, "Not Found"),
        BAD_REQUEST(400..499, "Bad Request"),
        INTERNAL_SERVER(500..599, "Internal Server Error"),
        UNKNOWN_ERROR(-1..-1, "Unknown Error")
    }

}