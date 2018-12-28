package com.josephbaca.easycli.processor

import com.josephbaca.easycli.tokenizer.CommandToken

object TokenBuilder {

    fun command(): CommandBuilder {
        return CommandBuilder()
    }

    fun arg(): ArgumentBuilder {
        return ArgumentBuilder()
    }
}