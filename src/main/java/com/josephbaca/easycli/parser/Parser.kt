package com.josephbaca.easycli.parser

import com.josephbaca.easycli.tokenizer.Token
import com.josephbaca.easycli.tokenizer.TokenPattern

internal interface Parser {

    fun parse(input: List<Token>, tokenPatterns: Set<TokenPattern>): () -> String
}