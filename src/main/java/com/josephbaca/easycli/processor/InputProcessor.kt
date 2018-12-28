package com.josephbaca.easycli.processor

import com.josephbaca.easycli.parser.Parser
import com.josephbaca.easycli.tokenizer.ArgumentTokenPattern
import com.josephbaca.easycli.tokenizer.Tokenizer
import com.josephbaca.easycli.tokenizer.CommandTokenPattern
import com.josephbaca.easycli.tokenizer.Token

class InputProcessor internal constructor(
    private val tokenizer: Tokenizer,
    private val parser: Parser,

    private val commandTokenPatterns: Set<CommandTokenPattern>,
    private val argumentTokenPatterns: Set<ArgumentTokenPattern>
) {

    fun parse(input: String): String {
        return parse(tokenize(input)).invoke()
    }

    internal fun tokenize(input: String): List<Token> {
        val tokens = tokenizer.tokenize(input, commandTokenPatterns.plus(argumentTokenPatterns))
        LOG.info("Found tokens %s".format(tokens))
        return tokens
    }

    private fun parse(tokens: List<Token>): () -> String {
        val response = parser.parse(tokens, commandTokenPatterns)
        LOG.info("Responding with \"%s\"".format(response))
        return response
    }

    companion object {
        private val LOG = org.slf4j.LoggerFactory.getLogger(this::class.java)
    }
}