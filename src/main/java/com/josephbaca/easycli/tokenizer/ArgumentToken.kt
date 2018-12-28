package com.josephbaca.easycli.tokenizer


class ArgumentToken(
    override val name: String,
    override val pattern: Regex
) : TokenPattern