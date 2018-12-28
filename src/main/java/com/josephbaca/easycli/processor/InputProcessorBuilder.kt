package com.josephbaca.easycli.processor

import com.josephbaca.easycli.parser.Parser
import com.josephbaca.easycli.parser.PlainParser
import com.josephbaca.easycli.tokenizer.*

class InputProcessorBuilder {

    private var tokenizer: Tokenizer
    private var parser: Parser
    private var commandTokens: Set<CommandToken> = mutableSetOf()

    init {
        // Set defaults
        tokenizer = BasicTokenizer
        parser = PlainParser
    }

    fun build(): InputProcessor {
        val argumentTokens = commandTokens.flatMap { it.arguments }.toSet()
        return InputProcessor(tokenizer, parser, commandTokens, argumentTokens)
    }

    fun withParser(parser: Parser): InputProcessorBuilder {
        this.parser = parser
        return this
    }

    fun withTokenizer(tokenizer: Tokenizer): InputProcessorBuilder {
        this.tokenizer = tokenizer
        return this
    }

    fun withCommands(vararg commands: CommandBuilder): InputProcessorBuilder {
        this.commandTokens = commands.map { it.build() }.toSet()
        return this
    }
}