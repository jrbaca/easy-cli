package com.josephbaca.easycli.parser

import com.josephbaca.easycli.tokenizer.NounToken
import com.josephbaca.easycli.tokenizer.Token
import com.josephbaca.easycli.tokenizer.Tokenizer
import com.josephbaca.easycli.tokenizer.VerbToken

class Parser internal constructor(
        private val tokenizer: Tokenizer,
        private val verbTokenMap: Map<VerbToken, (List<NounToken>) -> String?>,
        private val nounTokens: Set<NounToken>
) {

    private val LOG = org.slf4j.LoggerFactory.getLogger(this::class.java)

    private val allTokens
        get() = verbTokenMap.keys.toSet().plus(nounTokens)

    /**
     * Uses the [Tokenizer] to tokenize an input using the [VerbToken] from the [Context]. It then
     * validates the tokens and invokes the appropriate verbTokenMap.
     */
    fun parse(input: String): String {

        val tokenizedInput = tokenizer.tokenizeInput(input, allTokens)

        val parsedTokens = if (tokenizedInput != null) {
            parseTokensToFunction(tokenizedInput)
        } else {
            LOG.warn("tokenizer failed")
            null
        }

        return invokeFunctionAndGetResponse(parsedTokens)
    }

    private fun parseTokensToFunction(tokens: List<Token>): (() -> String?)? {
//        LOG.info("Attempting to parse tokens %s from context %s".format(tokens))
        return if (tokens.isEmpty()) null else createVerbTokenWithArgs(tokens)?.getInvocable()
    }

    private fun createVerbTokenWithArgs(tokens: List<Token>): VerbTokenWithArgs? {

        // Cast tokens and return null if fail
        val verbToken = tokens[0] as? VerbToken ?: return null
        val nounTokens = tokens.drop(1).filterIsInstance<NounToken>()
                .apply { if (size != tokens.size - 1) return null }

        return VerbTokenWithArgs(verbToken, nounTokens)
    }

    private fun invokeFunctionAndGetResponse(parsedInput: (() -> String?)?): String {
        return parsedInput?.invoke() ?: getFailureString()
    }

    private fun getFailureString(): String {
        LOG.warn("Cannot parse tokens.")

        return listOf(
                "i dont think you're old enough for that mister",
                "No no no I don't think so!",
                "Nah man, not today",
                "Baby shark says nuh uh"
        ).random()
    }

    private inner class VerbTokenWithArgs(
            private val verbToken: VerbToken,
            private val args: List<NounToken>
    ) {

        fun getInvocable(): () -> String? {
            val invocableFunction = verbTokenMap[verbToken]

            return if (args.size == verbToken.numArgs) {
                { invocableFunction?.invoke(args) }
            } else {
                { null }
            }
        }
    }
}