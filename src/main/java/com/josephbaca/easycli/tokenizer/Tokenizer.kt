package com.josephbaca.easycli.tokenizer

internal interface Tokenizer {

    fun tokenize(input: String, tokenPatterns: Set<TokenPattern>): List<Token>
}