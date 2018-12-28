package com.josephbaca.easycli

import com.josephbaca.easycli.parser.PlainParser
import com.josephbaca.easycli.processor.InputProcessor
import com.josephbaca.easycli.processor.InputProcessorBuilder
import com.josephbaca.easycli.tokenizer.BasicTokenizer
import com.josephbaca.easycli.processor.TokenBuilder.command
import com.josephbaca.easycli.processor.TokenBuilder.arg
import com.josephbaca.easycli.tokenizer.CommandToken
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class TokenizerTest {

    private val LOG = org.slf4j.LoggerFactory.getLogger(this::class.java)

    @Test
    fun sample() {
        val inventoryItems = listOf("sword", "knife")

        val inputProcessor: InputProcessor =
            InputProcessorBuilder()
                .withParser(PlainParser)
                .withTokenizer(BasicTokenizer)
                .withCommands(
                    command().withName("hello").withDescription("Says hi").thatCalls(this::sayHello),

                    command().withName("go").withAdditionalRegex("move").hasArg(
                        arg().withName("direction").fromList(
                            listOf("up",
                            "down",
                            "left",
                            "right")
                        )
                    ).withDescription("Takes you in a direction").thatCalls(this::go)

//                    command().withName("equip").hasArg(
//                        arg().withName("item").thatCorrespondsTo(inventoryItems)
//                    ).withDescription("Equips specified item").thatCalls(this::equip)
                )
                .build()

//        LOG.info("Help: %s".format(inputProcessor.help()))

        val response = inputProcessor.parse("go right")
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