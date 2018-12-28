package com.josephbaca.easycli.parser

import com.josephbaca.easycli.tokenizer.Token
import com.josephbaca.easycli.tokenizer.CommandToken

object PlainParser : Parser {

    override fun parse(input: List<Token>, commandTokens: Set<CommandToken>): String {
        val commandToken = input[0]
        val function = commandTokens.filter { it.name == commandToken.name }.single().function

        val args: Array<out Any> = arrayOf()

        return function.call(*args)
    }
}