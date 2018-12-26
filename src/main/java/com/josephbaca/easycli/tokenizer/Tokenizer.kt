package com.josephbaca.easycli.tokenizer

interface Tokenizer {

    fun tokenizeInput(input: String, tokens: Set<Token>): List<Token>?
}