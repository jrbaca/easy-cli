package com.josephbaca.easycli.parser

import com.josephbaca.easycli.tokenizer.ArgumentToken
import com.josephbaca.easycli.tokenizer.Token
import com.josephbaca.easycli.tokenizer.Tokenizer
import com.josephbaca.easycli.tokenizer.CommandToken

object PlainParser : Parser {

    private val LOG = org.slf4j.LoggerFactory.getLogger(this::class.java)

//    private val allTokens
//        get() = commandTokenMap.keys.toSet().plus(argumentTokens)

    override fun parse(input: List<Token>): String {
        val commandToken = input[0] as CommandToken
        return commandToken.functionToCall.invoke(arrayOf())
    }

//    /**
//     * Uses the [Tokenizer] to tokenize an input using the [CommandToken] from the [Context]. It then
//     * validates the tokens and invokes the appropriate commandTokenMap.
//     */
//    fun parse(input: String): String {
//
//        val tokenizedInput = tokenizer.tokenize(input, allTokens)
//
//        val parsedTokens = if (tokenizedInput != null) {
//            parseTokensToFunction(tokenizedInput)
//        } else {
//            LOG.warn("tokenizer failed")
//            null
//        }
//
//        return invokeFunctionAndGetResponse(parsedTokens)
//    }

//    private fun parseTokensToFunction(tokens: List<Token>): (() -> String?)? {
////        LOG.info("Attempting to parse tokens %s from context %s".format(tokens))
//        return if (tokens.isEmpty()) null else createVerbTokenWithArgs(tokens)?.getInvocable()
//    }
//
//    private fun createVerbTokenWithArgs(tokens: List<Token>): VerbTokenWithArgs? {
//
//        // Cast tokens and return null if fail
//        val verbToken = tokens[0] as? CommandToken ?: return null
//        val nounTokens = tokens.drop(1).filterIsInstance<ArgumentToken>()
//                .apply { if (size != tokens.size - 1) return null }
//
//        return VerbTokenWithArgs(verbToken, nounTokens)
//    }
//
//    private fun invokeFunctionAndGetResponse(parsedInput: (() -> String?)?): String {
//        return parsedInput?.invoke() ?: getFailureString()
//    }
//
//    private fun getFailureString(): String {
//        LOG.warn("Cannot parse tokens.")
//
//        return listOf(
//                "i dont think you're old enough for that mister",
//                "No no no I don't think so!",
//                "Nah man, not today",
//                "Baby shark says nuh uh"
//        ).random()
//    }

//    private inner class VerbTokenWithArgs(
//        private val commandToken: CommandToken,
//        private val args: List<ArgumentToken>
//    ) {
//
//        fun getInvocable(): () -> String? {
//            val invocableFunction = commandTokenMap[commandToken]
//
//            return if (args.size == commandToken.numArgs) {
//                { invocableFunction?.invoke(args) }
//            } else {
//                { null }
//            }
//        }
//    }
}