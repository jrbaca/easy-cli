package com.josephbaca.easycli.tokenizer

interface Tokenizer {

    fun tokenize(input: String, tokens: Set<TokenPattern>): List<Token>
}