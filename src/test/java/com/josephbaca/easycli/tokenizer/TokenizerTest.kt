package com.josephbaca.easycli.tokenizer

import com.josephbaca.easycli.builders.InputProcessorBuilder
import com.josephbaca.easycli.builders.TokenBuilder.arg
import com.josephbaca.easycli.builders.TokenBuilder.command
import com.josephbaca.easycli.builders.Tokenizable
import com.josephbaca.easycli.processor.InputProcessor
import com.josephbaca.easycli.processor.InputProcessorTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

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
                    .withDescription("Takes you in a direction")
                    .thatCalls(this::go)
                    .hasArg(
                        arg().withName("direction").from(InputProcessorTest.Direction.values().toSet())
                    ),
                command()
                    .withName("move") // Intentional duplicate
                    .withDescription("Takes you in a direction")
                    .thatCalls(this::go)
                    .hasArg(
                        arg().withName("direction").from(InputProcessorTest.Direction.values().toSet())
                    )
            )

            .build()
    }

    @Test
    fun oneTokenMatches() {

        val tokens = inputProcessor.tokenizeInput("hello")

        assertTrue(tokens.size == 1)
        assertEquals("HELLO", tokens[0].name)
    }

    @Test
    fun twoTokensMatch() {

        val tokens = inputProcessor.tokenizeInput("go north")

        assertTrue(tokens.size == 2)
        assertEquals("GO", tokens[0].name)

        assertEquals("DIRECTION", tokens[1].name)
    }

    @Test
    fun duplicateTokensMergeAndStillParse() {
        val tokens = inputProcessor.tokenizeInput("move north")

        assertTrue(tokens.size == 2)
        assertEquals("DIRECTION", tokens[1].name)
    }


    // Simple funcs
    @SuppressWarnings("WeakerAccess")
    fun sayHello(): String {
        return "Hello world"
    }

    // Use enums
    @SuppressWarnings("WeakerAccess")
    fun go(direction: Direction): String {
        return "going %s".format(direction.name.toLowerCase())
    }

    enum class Direction(override val pattern: Regex) : Tokenizable {
        NORTH(Regex("north")),
        SOUTH(Regex("south")),
        EAST(Regex("east")),
        WEST(Regex("west"))
    }
}