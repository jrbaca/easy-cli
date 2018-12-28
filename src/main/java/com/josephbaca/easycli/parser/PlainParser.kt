package com.josephbaca.easycli.parser

import com.josephbaca.easycli.tokenizer.Token
import com.josephbaca.easycli.tokenizer.CommandToken

object PlainParser : Parser {

    override fun parse(input: List<Token>): String {
        val commandToken = input[0] as CommandToken
        return commandToken.getFunctionResult(arrayOf())
    }
}