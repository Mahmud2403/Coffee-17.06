package com.example.coffee1706.domain.data

data class ErrorResponse(val items: ErrorItems)

data class ErrorItems(
    val message: String,
    val errors: Map<String, List<String>>
)