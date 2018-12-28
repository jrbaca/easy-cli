package com.josephbaca.easycli.processor

import com.josephbaca.easycli.tokenizer.ArgumentToken
import com.josephbaca.easycli.tokenizer.CommandToken
import kotlin.reflect.KFunction

class CommandBuilder {

    private var name: String? = null
    private var additionalRegex: Regex? = null
    private var description: String? = null
    private var arguments: MutableList<ArgumentToken> = mutableListOf()
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

    fun withAdditionalRegex(pattern: String): CommandBuilder {
        return withAdditionalRegex(Regex(pattern))
    }

    fun withAdditionalRegex(regex: Regex): CommandBuilder {
        this.additionalRegex = regex
        return this
    }

    fun hasArg(argumentBuilder: ArgumentBuilder): CommandBuilder {
        arguments.add(argumentBuilder.build())
        return this
    }

    internal fun build(): CommandToken {
        return CommandToken(name!!, Regex("(?i)" + name!!), description!!, arguments, function!!)
    }

}