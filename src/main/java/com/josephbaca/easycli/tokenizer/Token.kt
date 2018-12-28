package com.josephbaca.easycli.tokenizer

open class Token(
    override val name: String,
    override val pattern: Regex,
    val data: String

) : TokenPattern {

    override fun toString(): String {
        return name.toUpperCase()
    }
}