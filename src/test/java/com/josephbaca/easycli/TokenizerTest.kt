package com.josephbaca.easycli

import com.josephbaca.easycli.parser.Parser
import com.josephbaca.easycli.parser.ParserBuilder
import com.josephbaca.easycli.tokenizer.BasicTokenizer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class TokenizerTest {

    @Test
    fun simple() {
        val parser: Parser = ParserBuilder().withTokenizer(BasicTokenizer).build()

        val response = parser.parse("hi")
        assertEquals("Hello world", response)
    }


    private fun sayHello(): String {
        return "Hello world"
    }
}