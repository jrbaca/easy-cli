package com.josephbaca.easycli.tokenizer

/**
 * A token that can be attached to a function.
 */
interface VerbToken : Token {

    val numArgs: Int
    val helpUsage: String
    val helpString: String
}