package com.josephbaca.easycli.builders

import com.josephbaca.easycli.tokenizer.ArgumentTokenPattern
import com.josephbaca.easycli.tokenizer.CommandTokenPattern
import kotlin.reflect.KFunction

class CommandBuilder {

    private var name: String? = null
    private var additionalRegex: MutableSet<Regex> = mutableSetOf()
    private var description: String? = null
    private var argumentPatterns: MutableList<ArgumentTokenPattern> = mutableListOf()
    private var function: KFunction<String>? = null

    fun withName(name: String): CommandBuilder {
        this.name = name.toUpperCase()
        return this
    }

    fun withDescription(description: String): CommandBuilder {
        this.description = description
        return this
    }

    fun thatCalls(function: KFunction<String>): CommandBuilder {
        this.function = function
        return this
    }

    fun withAdditionalRegex(regex: Regex): CommandBuilder {
        this.additionalRegex.plus(regex)
        return this
    }

    fun hasArg(argumentBuilder: ArgumentBuilder): CommandBuilder {
        argumentPatterns.add(argumentBuilder.build())
        return this
    }

    internal fun build(): CommandTokenPattern {
        return CommandTokenPattern(
            name!!,
            setOf(Regex(name!!.toLowerCase())).plus(additionalRegex),
            description!!,
            argumentPatterns,
            function!!
        )
    }

}