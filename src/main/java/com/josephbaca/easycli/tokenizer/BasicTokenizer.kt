package com.josephbaca.easycli.tokenizer

import java.lang.RuntimeException

internal object BasicTokenizer : Tokenizer {

    private val LOG = org.slf4j.LoggerFactory.getLogger(this::class.java)

    /**
     * Tokenizes an input given a set of [TokenPattern] to match against.
     */
    override fun tokenize(input: String, tokenPatterns: Set<TokenPattern>): List<Token> {

        val preProcessedInput = removeExcessWhitespace(input)

        LOG.info("Attempting to tokenize \"%s\" with patterns %s".format(preProcessedInput, tokenPatterns))
        return iterativelyMatchTokens(preProcessedInput, tokenPatterns)
    }

    private fun removeExcessWhitespace(input: String): String {
        return input.trim().replace(Regex("\\s+"), " ")
    }

    /**
     * Algorithm for matching an input against a set of possible [TokenPattern]. Mutating form of a tail call recursion
     * algorithm. Runs in O(nm^2) time, where n = the number of patterns, and m = length of the input. Matches substrings
     * of increasing lengths against each token, and when found, starts search again from the previous stopping point.
     */
    private fun iterativelyMatchTokens(
        input: String,
        tokenPatterns: Set<TokenPattern>
    ): List<Token> {

        val foundTokens: MutableList<Token> = mutableListOf()
        val matchedStrings: MutableList<String> = mutableListOf()

        var startIndex = 0
        for (endIndex in 0..input.length) {
            val searchString = input.substring(startIndex, endIndex).trim()

            // Null if no token found
            val matchingToken: Token? = getMatchingToken(searchString, tokenPatterns)

            if (matchingToken != null) {
                startIndex = endIndex
                foundTokens.add(matchingToken)
                matchedStrings.add(searchString)
            }
        }
        if (matchedStrings.joinToString(" ") == input)
            return foundTokens
        else
            throw UnmatchableInputException()
    }

    private fun getMatchingToken(
        input: String,
        tokenPatterns: Set<TokenPattern>
    ): Token? {

        val matchingTokens = getMatchingTokens(input, tokenPatterns)

        if (matchingTokens.size > 1)
            throw OverlappingPatternException("Matched %s with %s".format(input, matchingTokens))
        else
            return matchingTokens.singleOrNull()

    }

    private fun getMatchingTokens(
        input: String,
        tokenPatterns: Set<TokenPattern>
    ): Set<Token> {
        return tokenPatterns
            .filter { inputMatchesTokenRegex(input, it) }
            .map { Token(it.name, it.pattern, input) }
            .toSet()
    }

    private fun inputMatchesTokenRegex(input: String, tokenPattern: TokenPattern): Boolean {
        return input.matches(tokenPattern.pattern)
    }
}