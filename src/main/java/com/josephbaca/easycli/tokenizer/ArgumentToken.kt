package com.josephbaca.easycli.tokenizer

import com.josephbaca.easycli.builders.Tokenizable

class ArgumentToken(
    override val name: String,
    override val data: String,
    val original: Tokenizable
) : Token