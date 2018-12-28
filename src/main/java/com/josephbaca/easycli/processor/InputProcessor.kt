package com.josephbaca.easycli.processor

import com.josephbaca.easycli.parser.Parser
import com.josephbaca.easycli.tokenizer.ArgumentToken
import com.josephbaca.easycli.tokenizer.Tokenizer
import com.josephbaca.easycli.tokenizer.CommandToken
import com.josephbaca.easycli.tokenizer.Token

class InputProcessor internal constructor(
    private val tokenizer: Tokenizer,
    private val parser: Parser,

    private val commandTokens: Set<CommandToken>,
    private val argumentTokens: Set<ArgumentToken>
) {

    private val LOG = org.slf4j.LoggerFactory.getLogger(this::class.java)

    fun parse(input: String): String {
        return parse(tokenize(input))
    }

    fun tokenize(input: String): List<Token> {
        val tokens = tokenizer.tokenize(input, commandTokens.plus(argumentTokens))
        LOG.info("Found tokens %s".format(tokens))
        return tokens
    }

    fun parse(tokens: List<Token>): String {
        val response = parser.parse(tokens, commandTokens)
        LOG.info("Responding with \"%s\"".format(response))
        return response
    }
}