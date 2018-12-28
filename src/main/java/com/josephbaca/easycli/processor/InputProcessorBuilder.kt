package com.josephbaca.easycli.processor

import com.josephbaca.easycli.parser.Parser
import com.josephbaca.easycli.parser.PlainParser
import com.josephbaca.easycli.tokenizer.*

class InputProcessorBuilder {

    private var tokenizer: Tokenizer
    private var parser: Parser
    private var commandTokens: Set<CommandToken> = mutableSetOf()

//    private var commandTokens = mutableSetOf<CommandToken>()
//
//    private val commandTokenToFunctionMap = mutableMapOf<CommandToken, (List<ArgumentToken>) -> String?>()
//    private val argumentTokens = mutableSetOf<ArgumentToken>()

    init {
        // Set defaults
        tokenizer = BasicTokenizer
        parser = PlainParser
    }

    fun build(): InputProcessor {
        val argumentTokens = commandTokens.flatMap { it.arguments }.toSet()
        return InputProcessor(tokenizer, parser, commandTokens, argumentTokens)
//        return PlainParser(BasicTokenizer, verbTokenMap, argumentTokens)
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

//    fun withNounToken(argumentToken: ArgumentToken): InputProcessorBuilder {
//        this.argumentTokens.add(argumentToken)
//        return this
//    }
//
//    fun withVerbToken(commandToken: CommandToken, mappedFunction: () -> String): InputProcessorBuilder {
////        mappedFunction
//        return withVerbTokenn(commandToken) { (_) -> mappedFunction() }
//    }
//
//    private fun withVerbTokenn(commandToken: CommandToken, mappedFunction: (List<ArgumentToken>) -> String?): InputProcessorBuilder {
//        this.verbTokenMap[commandToken] = mappedFunction
//        return this
//    }
}