package com.josephbaca.easycli.processor

internal object TokenBuilder {

    fun command(): CommandBuilder {
        return CommandBuilder()
    }

    fun arg(): ArgumentBuilder {
        return ArgumentBuilder()
    }
}