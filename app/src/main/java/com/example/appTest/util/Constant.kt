package com.example.appTest.util

// HTTP RESPONSE -----------------------------------------------------------------------------------

const val HTTP_NO_CONTENT = 204
const val BUNDLE_KEY = "bundle"
const val ITEM_ENTITY_KEY = "item"

// ERROR CODE --------------------------------------------------------------------------------------

enum class ErrorCode {
    HTTP_ERROR,
    CONNECT_ERROR,
    UNAUTHORIZED,
    FORBIDDEN,
    UNKNOWN_ERROR
}