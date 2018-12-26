package com.josephbaca.easycli

internal interface Tokenizer {

    fun tokenizeInput(input: String, tokens: Set<Token>): List<Token>?
}