package com.example.appTest.util.api


import com.example.appTest.util.ErrorCode
import com.example.appTest.util.tryCatchLogSuspendNullable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
inline fun <REMOTE, LOCAL> networkBoundResource (
    crossinline fetchFromRemote: () -> Flow<APIResponse<REMOTE>>,
    crossinline remoteResponse:  suspend (response: REMOTE?) -> Flow<LOCAL>
) = flow {

    // Emit loading state without data
    emit(Resource.loading(null))

    when (val fetchResult = tryCatchLogSuspendNullable(null) { fetchFromRemote().first() }) {


        is APIResponse.Success -> {
            emitAll(remoteResponse(fetchResult.body).map { Resource.success(it) })
        }

        is APIResponse.HttpError -> {
            emitAll(remoteResponse(fetchResult.combineResponse).map { Resource.error(it, ErrorCode.HTTP_ERROR)  })
        }

        is APIResponse.UnauthorizedError -> {
            emitAll(remoteResponse(fetchResult.combineResponse).map { Resource.error(it, ErrorCode.UNAUTHORIZED)  })
        }

        is APIResponse.ForbiddenError -> {
            emitAll(remoteResponse(fetchResult.combineResponse).map { Resource.error(it, ErrorCode.FORBIDDEN)  })
        }

        is APIResponse.NetWorkError -> {
            emitAll(remoteResponse(fetchResult.combineResponse).map { Resource.error(it, ErrorCode.CONNECT_ERROR)  })
        }

        is APIResponse.GenericError -> {
            emitAll(remoteResponse(fetchResult.combineResponse).map { Resource.error(it, ErrorCode.UNKNOWN_ERROR) })
        }

        is APIResponse.CombineHttpError -> {
            emitAll(remoteResponse(fetchResult.combineResponse).map { Resource.error(it, ErrorCode.HTTP_ERROR) })
        }

        is APIResponse.CombineNetworkError -> {
            emitAll(remoteResponse(fetchResult.combineResponse).map { Resource.error(it, ErrorCode.CONNECT_ERROR) })
        }

        is APIResponse.CombineError -> {
            emitAll(remoteResponse(fetchResult.combineResponse).map { Resource.error(it, ErrorCode.UNKNOWN_ERROR) })
        }

        else -> {
            emitAll(remoteResponse(null).map { Resource.error(it, ErrorCode.UNKNOWN_ERROR) })
        }
    }
}

/**
 * This core function will handle database caching after performing network request
 */
// TODO: Check each function work on what thread
@ExperimentalCoroutinesApi
inline fun <DB, REMOTE> networkBoundResource(
    crossinline fetchFromLocal: suspend () -> Flow<DB>,
    crossinline shouldFetchFromRemote: suspend (DB?) -> Boolean,
    crossinline fetchFromRemote: () -> Flow<APIResponse<REMOTE>>,
    crossinline processRemoteResponse: suspend (response: APIResponse.Success<REMOTE>) -> Unit,
    crossinline saveRemoteData: suspend (response: APIResponse.Success<REMOTE>) -> Unit,
    crossinline onFetchFailed: suspend (error: APIResponse.BaseError<REMOTE>) -> Unit
) = flow {

    // Emit loading state without data
    emit(Resource.loading(null))

    val localData = tryCatchLogSuspendNullable(null) {
        fetchFromLocal().first()
    }

    if (localData == null || shouldFetchFromRemote(localData)) {

        // Emit loading state with nullable data
        emit(Resource.loading(localData))

        when (val fetchResult = tryCatchLogSuspendNullable(null) { fetchFromRemote().first() }) {

            is APIResponse.Empty -> emitAll(fetchFromLocal().map { Resource.success(it) })

            is APIResponse.Success -> {
                processRemoteResponse(fetchResult)
                saveRemoteData(fetchResult)
                emitAll(fetchFromLocal().map { Resource.success(it) })
            }

            is APIResponse.UnauthorizedError -> {
                onFetchFailed(fetchResult)
                emitAll(fetchFromLocal().map { Resource.error(it, ErrorCode.UNAUTHORIZED) })
            }

            is APIResponse.ForbiddenError -> {
                onFetchFailed(fetchResult)
                emitAll(fetchFromLocal().map { Resource.error(it, ErrorCode.FORBIDDEN) })
            }

            is APIResponse.HttpError -> {
                onFetchFailed(fetchResult)
                emitAll(fetchFromLocal().map { Resource.error(it, ErrorCode.HTTP_ERROR) })
            }

            is APIResponse.NetWorkError -> {
                onFetchFailed(fetchResult)
                emitAll(fetchFromLocal().map { Resource.error(it, ErrorCode.CONNECT_ERROR) })
            }

            is APIResponse.GenericError -> {
                onFetchFailed(fetchResult)
                emitAll(fetchFromLocal().map { Resource.error(it, ErrorCode.UNKNOWN_ERROR) })
            }

            is APIResponse.CombineHttpError -> {
                onFetchFailed(fetchResult)
                emitAll(fetchFromLocal().map { Resource.error(it, ErrorCode.HTTP_ERROR) })
            }

            is APIResponse.CombineNetworkError -> {
                onFetchFailed(fetchResult)
                emitAll(fetchFromLocal().map { Resource.error(it, ErrorCode.CONNECT_ERROR) })
            }

            is APIResponse.CombineError -> {
                onFetchFailed(fetchResult)
                emitAll(fetchFromLocal().map { Resource.error(it, ErrorCode.UNKNOWN_ERROR) })
            }

            else -> emitAll(fetchFromLocal().map { Resource.error(it, ErrorCode.UNKNOWN_ERROR) })
        }
    } else {
        emitAll(fetchFromLocal().map { Resource.success(it) })
    }
}
