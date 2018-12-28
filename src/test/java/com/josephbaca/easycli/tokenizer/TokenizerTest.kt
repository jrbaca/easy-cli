package com.josephbaca.easycli.tokenizer

import com.josephbaca.easycli.processor.InputProcessor
import com.josephbaca.easycli.processor.InputProcessorBuilder
import com.josephbaca.easycli.processor.InputProcessorTest
import com.josephbaca.easycli.processor.TokenBuilder.command
import com.josephbaca.easycli.processor.TokenBuilder.arg
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class TokenizerTest {

    private val inputProcessor = buildTestInputProcessor()

    private fun buildTestInputProcessor(): InputProcessor {
        return InputProcessorBuilder()
            .withParser(InputProcessorBuilder.Parsers.Plain)
            .withTokenizer(InputProcessorBuilder.Tokenizers.Basic)
            .withCommands(
                command()
                    .withName("hello")
                    .withDescription("Says hi")
                    .thatCalls(this::sayHello),

                command()
                    .withName("go")
                    .withAdditionalRegex("move")
                    .withDescription("Takes you in a direction")
                    .thatCalls(this::go)
                    .hasArg(
                        arg().withName("direction").from(InputProcessorTest.Direction.values().toSet())
                    )
//              command()
//                  .withName("equip").hasArg(
//                        arg().withName("item").thatCorrespondsTo(inventoryItems)
//                    ).withDescription("Equips specified item").thatCalls(this::equip)
            )

            .build()
    }

    @Test
    fun oneTokenMatches() {

        val tokens = inputProcessor.tokenize("hello")

        assertTrue(tokens.size == 1)
        assertEquals("HELLO", tokens[0].name)
    }

    @Test
    fun twoTokensMatch() {

        val tokens = inputProcessor.tokenize("go north")

        assertTrue(tokens.size == 2)
        assertEquals("GO", tokens[0].name)
//        assertEquals("up", tokens[0].)

        assertEquals("DIRECTION", tokens[1].name)
//        assertEquals("up", tokens[1].name)
    }


    private fun sayHello(args: Array<Any>): String {
        return "Hello world"
    }

    private fun go(args: Array<Any>): String {
        return "Hello world"
    }
}