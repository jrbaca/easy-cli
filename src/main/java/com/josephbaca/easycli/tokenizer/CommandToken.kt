package com.josephbaca.easycli.tokenizer

import java.util.function.Function
import kotlin.reflect.KFunction

/**
 * A token that can be attached to a function.
 */
class CommandToken(
    name: String,
    regex: Regex,
    val description: String,
    val arguments: List<ArgumentToken>,
    val functionToCall: (Array<Any>) -> String
) : Token(name, regex) {

    fun getFunctionResult(args: Array<Any>): String {
        return functionToCall(args)
    }
}