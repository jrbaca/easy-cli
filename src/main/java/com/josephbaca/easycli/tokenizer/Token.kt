package com.josephbaca.easycli.tokenizer

/**
 * Any token that can be extracted from a string via regex.
 */
interface Token {

    val regex: Regex
}