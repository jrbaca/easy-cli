package com.josephbaca.easycli.tokenizer

class OverlappingPatternException(message: String?) : Throwable(message) {
    constructor() : this(null)
}