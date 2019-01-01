package com.josephbaca.easycli.tokenizer

import com.josephbaca.easycli.builders.Tokenizable


internal class ArgumentTokenPattern(
    override val name: String,
    val regexToOriginal: Map<Regex, Tokenizable>
) : TokenPattern {

    override val patterns: Set<Regex>
        get() = regexToOriginal.keys

    override fun toString(): String {
        return name
    }
}