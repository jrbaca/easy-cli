package com.josephbaca.easycli.tokenizer

import kotlin.reflect.KFunction

class CommandToken(
    override val name: String,
    override val pattern: Regex,
    val description: String,
    val arguments: List<ArgumentToken>,
    val function: KFunction<String>

) : TokenPattern