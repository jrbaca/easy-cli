package com.josephbaca.easycli.parser

import com.josephbaca.easycli.tokenizer.Token

interface Parser {

    fun parse(input: List<Token>): String
}