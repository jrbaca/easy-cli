package com.josephbaca.easycli.tokenizer

/**
 * Any token that can be extracted from a string via regex.
 */
open class Token(val name: String, val regex: Regex) {

    override fun toString(): String {
        return name
    }
}