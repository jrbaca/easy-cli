package com.josephbaca.easycli.parser

import com.josephbaca.easycli.tokenizer.ArgumentToken
import com.josephbaca.easycli.tokenizer.CommandToken
import com.josephbaca.easycli.tokenizer.Token
import com.josephbaca.easycli.tokenizer.TokenPattern

internal object PlainParser : Parser {

    override fun parse(input: List<Token>, tokenPatterns: Set<TokenPattern>): () -> String {
        val commandToken = input[0] as CommandToken
        val args: Array<out Any> = input
            .drop(1)
            .filterIsInstance<ArgumentToken>()
            .map { it.original }
            .toTypedArray()

        return { commandToken.function.call(*args) }
    }
}