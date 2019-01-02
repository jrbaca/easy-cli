package com.josephbaca.easycli.tokenizer

import com.josephbaca.easycli.builders.Tokenizable

internal object BasicTokenizer : Tokenizer {

    private val LOG = org.slf4j.LoggerFactory.getLogger(this::class.java)

    /**
     * Tokenizes an input given a set of [Tokenizable] to match against.
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
     * Algorithm for matching an input against a set of possible [Tokenizable]. Mutating form of a tail call recursion
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

    private fun getMatchingToken(input: String, tokenPatterns: Set<TokenPattern>): Token? {

        val matchingTokens = purgeIdenticalTokens(getMatchingTokens(input, tokenPatterns))

        if (matchingTokens.size > 1)
            throw OverlappingPatternException("Matched %s with %s".format(input, matchingTokens))
        else
            return matchingTokens.singleOrNull()

    }

    private fun getMatchingTokens(input: String, tokenPatterns: Set<TokenPattern>): Set<Token> {

        return tokenPatterns.mapNotNull { makeTokenIfRegexMatches(input, it) }.toSet()
    }

    private fun purgeIdenticalTokens(tokens: Set<Token>): Set<Token> {
        // TODO this
        val uniqueArgumentTokens = mutableSetOf<ArgumentToken>()
        val commandTokens = mutableSetOf<CommandToken>()

        for (token in tokens) {
            when (token) {
                is ArgumentToken -> {
                    if (!uniqueArgumentTokens
                            .map { it.original == token.original }
                            .fold(false) { l, r -> l || r }
                    ) {
                        uniqueArgumentTokens.add(token)
                    }
                }
                is CommandToken -> commandTokens.add(token)
            }
        }
        return uniqueArgumentTokens.plus(commandTokens)
    }

    private fun makeTokenIfRegexMatches(input: String, tokenPattern: TokenPattern): Token? {

        val tokens = makeTokens(input, tokenPattern)

        return if (tokens.size > 1)
            throw OverlappingPatternException(
                "Multiple patterns in %s match against %s".format(
                    input,
                    tokenPattern.patterns
                )
            )
        else
            tokens.singleOrNull()
    }

    private fun makeTokens(input: String, tokenPattern: TokenPattern): Set<Token> {
        return tokenPattern.patterns
            .filter { it.matches(input) }
            .map {
                when (tokenPattern) {
                    is ArgumentTokenPattern ->
                        ArgumentToken(tokenPattern.name, input, tokenPattern.regexToOriginal[it]!!)
                    is CommandTokenPattern ->
                        CommandToken(tokenPattern.name, input, tokenPattern.function)
                    else -> throw RuntimeException("Should only have argument or command tokens")
                }
            }
            .toSet()
    }
}