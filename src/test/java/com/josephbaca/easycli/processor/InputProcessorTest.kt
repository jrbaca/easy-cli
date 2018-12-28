package com.josephbaca.easycli.processor

import com.josephbaca.easycli.parser.PlainParser
import com.josephbaca.easycli.processor.TokenBuilder.command
import com.josephbaca.easycli.tokenizer.BasicTokenizer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class InputProcessorTest {

    private val inputProcessor = buildTestInputProcessor()

    private fun buildTestInputProcessor(): InputProcessor {
        return InputProcessorBuilder()
            .withParser(PlainParser)
            .withTokenizer(BasicTokenizer)
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
                        TokenBuilder.arg().withName("direction").fromList(
                            listOf(
                                "up",
                                "down",
                                "left",
                                "right"
                            )
                        )
                    )
//              command()
//                  .withName("equip").hasArg(
//                        arg().withName("item").thatCorrespondsTo(inventoryItems)
//                    ).withDescription("Equips specified item").thatCalls(this::equip)
            )

            .build()
    }

    @Test
    fun functionWithNoArgs() {

        val response = inputProcessor.parse("hello")
        assertEquals("Hello world", response)
    }


    private fun sayHello(args: Array<Any>): String {
        return "Hello world"
    }

    private fun go(args: Array<Any>): String {
        return "Hello world"
    }

    private fun equip(args: Array<Any>): String {
        return "Hello world"
    }
}