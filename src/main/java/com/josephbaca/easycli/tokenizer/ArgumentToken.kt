package com.josephbaca.easycli.tokenizer

import com.josephbaca.easycli.builders.Tokenizable

class ArgumentToken(
    override val name: String,
    override val data: String,
    val original: Tokenizable
) : Token {

    override fun toString(): String {
        return "%s(\"%s\": %s)".format(name, data, original)
    }
}