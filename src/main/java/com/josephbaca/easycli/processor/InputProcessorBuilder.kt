package com.josephbaca.easycli.processor

import com.josephbaca.easycli.parser.Parser
import com.josephbaca.easycli.parser.PlainParser
import com.josephbaca.easycli.tokenizer.*

class InputProcessorBuilder {

    private var tokenizer: Tokenizer
    private var parser: Parser
    private var commandTokenPatterns: Set<CommandTokenPattern> = mutableSetOf()

    init {
        // Set defaults
        tokenizer = BasicTokenizer
        parser = PlainParser
    }

    fun build(): InputProcessor {
        val argumentTokens = commandTokenPatterns.flatMap { it.arguments }.toSet()
        return InputProcessor(tokenizer, parser, commandTokenPatterns, argumentTokens)
    }

    fun withParser(parser: Parsers): InputProcessorBuilder {
        this.parser = when (parser) {
            Parsers.Plain -> PlainParser
        }
        return this
    }

    fun withTokenizer(tokenizer: Tokenizers): InputProcessorBuilder {
        this.tokenizer = when (tokenizer) {
            Tokenizers.Basic -> BasicTokenizer
        }
        return this
    }

    fun withCommands(vararg commands: CommandBuilder): InputProcessorBuilder {
        this.commandTokenPatterns = commands.map { it.build() }.toSet()
        return this
    }

    enum class Parsers {
        Plain
    }

    enum class Tokenizers {
        Basic
    }
}