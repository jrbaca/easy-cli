package com.josephbaca.easycli.processor

import com.josephbaca.easycli.tokenizer.ArgumentTokenPattern
import com.josephbaca.easycli.tokenizer.TokenPattern

class ArgumentBuilder {

    var name: String? = null
    var regex: Regex? = null

    fun withName(name: String): ArgumentBuilder {
        this.name = name.toUpperCase()
        return this
    }

    fun build(): ArgumentTokenPattern {
        return ArgumentTokenPattern(name!!, regex!!)
    }

    fun from(tokenPatterns: Set<TokenPattern>): ArgumentBuilder {
        this.regex = Regex("(?i)" + tokenPatterns.map { it.pattern }.joinToString(separator = "|"))
        return this
    }
}