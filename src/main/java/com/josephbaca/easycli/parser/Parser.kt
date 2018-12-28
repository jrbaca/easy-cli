package com.josephbaca.easycli.parser

import com.josephbaca.easycli.tokenizer.CommandTokenPattern
import com.josephbaca.easycli.tokenizer.Token

interface Parser {

    fun parse(input: List<Token>, commandTokenPatterns: Set<CommandTokenPattern>): String
}