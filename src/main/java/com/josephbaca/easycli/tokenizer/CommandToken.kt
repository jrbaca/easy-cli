package com.josephbaca.easycli.tokenizer

import kotlin.reflect.KFunction

class CommandToken(
    override val name: String,
    override val data: String,
    val function: KFunction<String>
) : Token