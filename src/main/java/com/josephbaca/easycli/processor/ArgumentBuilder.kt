package com.josephbaca.easycli.processor

import com.josephbaca.easycli.tokenizer.ArgumentToken

class ArgumentBuilder {
    var name: String? = null
    var regex: Regex? = null

    fun withName(name: String): ArgumentBuilder {
        this.name = name.toUpperCase()
        return this
    }

    fun fromList(list: List<Any>): ArgumentBuilder {
        this.regex = Regex("(?i)" + list.joinToString(separator = "|"))
        return this
    }

    fun build(): ArgumentToken {
        return ArgumentToken(name!!, regex!!)
    }
}