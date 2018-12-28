package com.josephbaca.easycli.tokenizer

class UnmatchableInputException(message: String?) : Throwable(message) {
    constructor() : this(null)
}