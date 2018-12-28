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

    fun withParser(parser: Parser): InputProcessorBuilder {
        this.parser = parser
        return this
    }

    fun withTokenizer(tokenizer: Tokenizer): InputProcessorBuilder {
        this.tokenizer = tokenizer
        return this
    }

    fun withCommands(vararg commands: CommandBuilder): InputProcessorBuilder {
        this.commandTokenPatterns = commands.map { it.build() }.toSet()
        return this
    }
}