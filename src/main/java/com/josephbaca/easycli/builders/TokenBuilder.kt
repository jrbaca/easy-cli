package com.josephbaca.easycli.builders

internal object TokenBuilder {

    fun command(): CommandBuilder {
        return CommandBuilder()
    }

    fun arg(): ArgumentBuilder {
        return ArgumentBuilder()
    }
}