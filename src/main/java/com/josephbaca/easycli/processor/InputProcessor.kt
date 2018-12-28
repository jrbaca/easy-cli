package com.josephbaca.easycli.processor

import com.josephbaca.easycli.parser.Parser
import com.josephbaca.easycli.tokenizer.ArgumentTokenPattern
import com.josephbaca.easycli.tokenizer.Tokenizer
import com.josephbaca.easycli.tokenizer.CommandTokenPattern
import com.josephbaca.easycli.tokenizer.Token
import kotlin.reflect.full.IllegalCallableAccessException

class InputProcessor internal constructor(
    private val tokenizer: Tokenizer,
    private val parser: Parser,

    private val commandTokenPatterns: Set<CommandTokenPattern>,
    private val argumentTokenPatterns: Set<ArgumentTokenPattern>
) {

    fun parse(input: String): String {
        val function = parseTokens(tokenizeInput(input))
        return invokeFunction(function)
    }

    internal fun tokenizeInput(input: String): List<Token> {
        val tokens = tokenizer.tokenize(input, commandTokenPatterns.plus(argumentTokenPatterns))
        LOG.info("Found tokens %s".format(tokens))
        return tokens
    }

    private fun parseTokens(tokens: List<Token>): () -> String {
        val response = parser.parse(tokens, commandTokenPatterns)
        LOG.info("Responding with \"%s\"".format(response))
        return response
    }

    private fun invokeFunction(function: () -> String): String {
        return try {
            function.invoke()
        } catch (e: IllegalCallableAccessException) {
            throw UnreachableMethodException("Your function must be public in order to be called")
        }
    }

    fun help(): String {
        return ""
    }

    companion object {
        private val LOG = org.slf4j.LoggerFactory.getLogger(this::class.java)
    }
}