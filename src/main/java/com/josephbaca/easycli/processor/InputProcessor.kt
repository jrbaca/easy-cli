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

//    private val commandTokenToFunctionMap: Map<CommandToken, (List<ArgumentToken>) -> String?>,
//    private val argTokens: Set<ArgumentToken>
) {

    private val LOG = org.slf4j.LoggerFactory.getLogger(this::class.java)

    fun parse(input: String): String {
        LOG.info("Processing input: \"%s\"".format(input))

        val tokens = tokenizer.tokenize(input, commandTokens.plus(argumentTokens))
        LOG.info("Found tokens %s".format(tokens))

        val response = parser.parse(tokens)
        LOG.info("Responding with \"%s\"".format(response))
        return response
    }

//    fun help(): String {
//        return allTokens
//            .sortedBy { it.name }
//            .joinToString(separator = "\n") { "%s: %s".format(it.name, it.description) }
//
////        return allTokens.toString()
////        return allVerbTokens.keys.sortedBy { token -> token.helpUsage }
////            .joinToString(separator = "\n") { token ->
////                "%s: %s".format(token.helpUsage, token.helpString)
////            }
//    }
}