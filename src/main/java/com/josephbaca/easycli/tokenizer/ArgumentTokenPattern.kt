package com.josephbaca.easycli.tokenizer


internal class ArgumentTokenPattern(
    override val name: String,
    override val pattern: Regex
) : TokenPattern {

    override fun toString(): String {
        return name
    }
}