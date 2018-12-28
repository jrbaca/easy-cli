package com.josephbaca.easycli.tokenizer

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