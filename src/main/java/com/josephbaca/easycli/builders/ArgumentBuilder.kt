package com.josephbaca.easycli.builders

import com.josephbaca.easycli.tokenizer.ArgumentTokenPattern

class ArgumentBuilder {

    private var name: String? = null
    private var regexToOriginal: Map<Regex, Tokenizable> = mapOf()

    fun withName(name: String): ArgumentBuilder {
        this.name = name.toUpperCase()
        return this
    }

    internal fun build(): ArgumentTokenPattern {
        return ArgumentTokenPattern(name!!, regexToOriginal)
    }

    fun from(tokenPatterns: Set<Tokenizable>): ArgumentBuilder {
        this.regexToOriginal = tokenPatterns.associate { Pair(it.pattern, it) }
        return this
    }
}