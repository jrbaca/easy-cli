package com.josephbaca.easycli

class Parser internal constructor(private val tokenizer: Tokenizer) {

    private val LOG = org.slf4j.LoggerFactory.getLogger(this::class.java)


    /**
     * Uses the [Tokenizer] to tokenize an input using the [VerbToken] from the [Context]. It then
     * validates the tokens and invokes the appropriate commands.
     */
    fun parse(input: String, context: Context): String {

        val tokenizedInput = tokenizer.tokenizeInput(input, context.allTokens)

        val parsedTokens = if (tokenizedInput != null) {
            parseTokensToFunction(context, tokenizedInput)
        } else {
            LOG.warn("Tokenizer failed")
            null
        }

        return invokeFunctionAndGetResponse(parsedTokens)
    }

    private fun parseTokensToFunction(context: Context, tokens: List<Token>): (() -> String?)? {
        LOG.info("Attempting to parse tokens %s from context %s".format(tokens, context))
        return if (tokens.isEmpty()) null else createVerbTokenWithArgs(context, tokens)?.getInvocable()
    }

    private fun createVerbTokenWithArgs(context: Context, tokens: List<Token>): VerbTokenWithArgs? {

        // Cast tokens and return null if fail
        val verbToken = tokens[0] as? VerbToken ?: return null
        val nounTokens = tokens.drop(1).filterIsInstance<NounToken>()
                .apply { if (size != tokens.size - 1) return null }

        return VerbTokenWithArgs(context, verbToken, nounTokens)
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

    private class VerbTokenWithArgs(
            private val context: Context,
            private val verbToken: VerbToken,
            private val args: List<NounToken>
    ) {

        fun getInvocable(): () -> String? {
            val invocableFunction = context.allVerbTokens[verbToken]

            return if (args.size == verbToken.numArgs) {
                { invocableFunction?.invoke(args) }
            } else {
                { null }
            }
        }
    }
}