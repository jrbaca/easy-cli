package com.josephbaca.easycli.processor

class UnreachableMethodException(message: String?) : Throwable(message) {
    constructor() : this(null)
}