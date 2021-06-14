package com.example.appTest.util.api


import com.example.appTest.util.ErrorCode
import com.example.appTest.util.HTTP_NO_CONTENT
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Wrap response type, header and error for API call
 */
sealed class APIResponse<out T> {

    companion object {

        fun <T> create(t: Throwable): APIResponse<T> =
            if (t is IOException) {
                NetWorkError(t)
            } else {
                GenericError(t)
            }

        fun <T> create(response: Response<T>): APIResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == HTTP_NO_CONTENT) {
                    Empty
                } else {
                    Success(
                        body = body,
                        header = response.headers().toMultimap()
                    )
                }
            } else {
                when (response.code()) {
                    // HTTP 401 Unauthorized
                    401 -> UnauthorizedError()

                    // HTTP 403 Forbidden
                    403 -> ForbiddenError()

                    else -> HttpError(HttpException(response))
                }
            }
        }
    }

    object Empty : APIResponse<Nothing>()

    data class Success<T>(
        val body: T,
        val header: Map<String, List<String>> = emptyMap()
    ) : APIResponse<T>()

    open class BaseError<T>(
        val t: Throwable? = null,
        val combineResponse: T? = null
    ) : APIResponse<T>()

    class HttpError<T>(t: Throwable) : BaseError<T>(t)

    class UnauthorizedError<T> : BaseError<T>(Throwable(ErrorCode.UNAUTHORIZED.toString(), null))

    class ForbiddenError<T> : BaseError<T>(Throwable(ErrorCode.FORBIDDEN.toString(), null))

    class NetWorkError<T>(t: Throwable) : BaseError<T>(t)

    class GenericError<T>(t: Throwable) : BaseError<T>(t)

    class CombineError<T>(combineResponse: T) : BaseError<T>(combineResponse = combineResponse)

    class CombineHttpError<T>(combineResponse: T) : BaseError<T>(combineResponse = combineResponse)

    class CombineNetworkError<T>(combineResponse: T) :
        BaseError<T>(combineResponse = combineResponse)
}
