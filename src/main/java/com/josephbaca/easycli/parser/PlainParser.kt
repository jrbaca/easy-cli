package com.josephbaca.easycli.parser

import com.josephbaca.easycli.tokenizer.Token
import com.josephbaca.easycli.tokenizer.CommandTokenPattern

object PlainParser : Parser {

    override fun parse(input: List<Token>, commandTokenPatterns: Set<CommandTokenPattern>): String {
        val commandToken = input[0]
        val function = commandTokenPatterns.filter { it.name == commandToken.name }.single().function

        val args: Array<out Any> = arrayOf()

        return function.call(*args)
    }
}