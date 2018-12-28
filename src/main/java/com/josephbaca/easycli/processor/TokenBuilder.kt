package com.josephbaca.easycli.processor

object TokenBuilder {

    fun command(): CommandBuilder {
        return CommandBuilder()
    }

    fun arg(): ArgumentBuilder {
        return ArgumentBuilder()
    }
}