package com.josephbaca.easycli.tokenizer

open class Token(val name: String, val regex: Regex) {

    override fun toString(): String {
        return name.toUpperCase()
    }
}