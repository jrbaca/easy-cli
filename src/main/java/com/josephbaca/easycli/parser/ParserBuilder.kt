package com.josephbaca.easycli.parser

import com.josephbaca.easycli.tokenizer.BasicTokenizer
import com.josephbaca.easycli.tokenizer.NounToken
import com.josephbaca.easycli.tokenizer.Tokenizer
import com.josephbaca.easycli.tokenizer.VerbToken

class ParserBuilder {

    private var tokenizer: Tokenizer
    private val verbTokenMap = mutableMapOf<VerbToken, (List<NounToken>) -> String?>()
    private var nounTokens = mutableSetOf<NounToken>()

    init {
        tokenizer = BasicTokenizer
    }

    fun build(): Parser {
        return Parser(BasicTokenizer, verbTokenMap, nounTokens)
    }

    fun withTokenizer(tokenizer: Tokenizer): ParserBuilder {
        this.tokenizer = tokenizer
        return this
    }
}