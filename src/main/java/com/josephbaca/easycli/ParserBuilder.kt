package com.josephbaca.easycli

class ParserBuilder {

    fun build(): Parser {
        return Parser(BasicTokenizer)
    }
}