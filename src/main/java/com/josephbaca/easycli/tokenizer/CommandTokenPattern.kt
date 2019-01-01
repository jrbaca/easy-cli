package com.josephbaca.easycli.tokenizer

import kotlin.reflect.KFunction


internal class CommandTokenPattern(
    override val name: String,
    override val patterns: Set<Regex>,
    val description: String,
    val arguments: List<ArgumentTokenPattern>,
    val function: KFunction<String>

) : TokenPattern {

    override fun toString(): String {
        return name
    }
}