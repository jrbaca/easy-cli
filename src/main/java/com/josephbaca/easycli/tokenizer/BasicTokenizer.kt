package com.josephbaca.easycli.tokenizer

import java.lang.RuntimeException

internal object BasicTokenizer: Tokenizer {

    private val LOG = org.slf4j.LoggerFactory.getLogger(this::class.java)

    /**
     * Tokenizes an input given a set of possible allTokens. Returns null if tokenizer fails.
     */
    override fun tokenize(
            input: String,
            tokens: Set<TokenPattern>
    ): List<Token> {
        val preProcessedInput = preProcessInput(input)

        LOG.info("Attempting to tokenize \"%s\" with tokens %s".format(preProcessedInput, tokens))
        return iterativelyMatchTokens(preProcessedInput, tokens)
    }

    private fun preProcessInput(input: String): String {
        val processedInput = input.toLowerCase().trim().replace(Regex("\\s+"), " ")
        LOG.debug("Processed \"%s\" into \"%s\"".format(input, processedInput))
        return processedInput
    }

    /**
     * Algorithm for matching a set of allTokens with regex against an input. Mutating form of a tail call recursion
     * algorithm. Runs in O(nm^2) time, where n = the number of tokens, and m = length of the input. Matches substrings
     * of increasing lengths against each token, and when found, starts search again from the previous stopping point.
     * Returns null if the input could not be fully matched.
     */
    private fun iterativelyMatchTokens(
            input: String,
            tokens: Set<TokenPattern>
    ): List<Token> {

        val foundTokens: MutableList<Token> = mutableListOf()
        val matchedStrings: MutableList<String> = mutableListOf()

        var startIndex = 0
        for (endIndex in 0..input.length) {
            val searchString = input.substring(startIndex, endIndex).trim()
            LOG.debug("Searching \"%s\" from \"%s\"".format(searchString, input))

            // Null if no token found
            val matchingToken: Token?=
                    getSingleMatchingToken(searchString, tokens)

            if (matchingToken != null) {
                startIndex = endIndex
                foundTokens.add(matchingToken)
                matchedStrings.add(searchString)
            }
        }
        return if (matchedStrings.joinToString(" ") == input) foundTokens else throw RuntimeException("Didn't fully tokenize") // Didn't fully tokenize TODO custom exception
    }

    /**
     * Matches each token against a string. Returns null if more than one match, and an empty list if no matches.
     */
    private fun getSingleMatchingToken(
            input: String,
            tokens: Set<TokenPattern>
    ): Token? {

        val matchingTokens = getMatchingTokens(input, tokens)

        return if (matchingTokens.size > 1) {
            throw RuntimeException("Too many tokens matched!") //TODO custom exception
        } else {
            matchingTokens.singleOrNull()
        }
    }

    private fun getMatchingTokens(
            input: String,
            tokens: Set<TokenPattern>
    ): Set<Token> {
        return tokens.filter { inputMatchesTokenRegex(input, it) }.map { Token(it.name, it.pattern, input) }.toSet()
    }

    private fun inputMatchesTokenRegex(input: String, token: TokenPattern): Boolean {
        return input.matches(token.pattern)
    }
}