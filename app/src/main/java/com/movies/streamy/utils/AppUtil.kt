package com.movies.streamy.utils

import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse

object AppUtil {
    fun getErrorResponse(errorResponse: ErrorResponse?): String {
        return errorResponse?.message.toString()
    }
}