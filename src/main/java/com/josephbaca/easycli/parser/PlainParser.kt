package com.josephbaca.easycli.parser

import com.josephbaca.easycli.tokenizer.Token
import com.josephbaca.easycli.tokenizer.CommandTokenPattern

internal object PlainParser : Parser {

    override fun parse(input: List<Token>, commandTokenPatterns: Set<CommandTokenPattern>): () -> String {
        val commandToken = input[0]
        val function = commandTokenPatterns.single { it.name == commandToken.name }.function
        val args: Array<out Any> = input.drop(1).toTypedArray()

        return { function.call(*args) }
    }
}