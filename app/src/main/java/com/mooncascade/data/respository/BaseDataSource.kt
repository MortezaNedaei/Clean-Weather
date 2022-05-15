package com.mooncascade.data.respository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

abstract class BaseDataSource {

    companion object {
        const val TAG = "BaseDataSource"
    }

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
                        Result.failure(
                            Exception(
                                response.message()
                            )
                        )
                    )
                }
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

}