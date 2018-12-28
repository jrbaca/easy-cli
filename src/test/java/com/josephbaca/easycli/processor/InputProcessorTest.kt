package com.josephbaca.easycli.processor

import com.josephbaca.easycli.parser.PlainParser
import com.josephbaca.easycli.processor.TokenBuilder.arg
import com.josephbaca.easycli.processor.TokenBuilder.command
import com.josephbaca.easycli.tokenizer.BasicTokenizer
import com.josephbaca.easycli.tokenizer.TokenPattern
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
                        arg().withName("direction").from(Direction.values().toSet())
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


    // Simple funcs
    fun sayHello(): String {
        return "Hello world"
    }

    // Use enums
    fun go(direction: Direction): String {
        return "going %s".format(direction.name)
    }

    // Use classes
    private fun equip(item: Item): String {
        return "Equipping %s(Power: %s)".format(item.name, item.power)
    }

    // Use multiple args
    private fun modifyItemPower(item: Item, power: Int): String {
        item.power = power
        return "Item power changed to %s!".format(item.power)
    }

    enum class Direction(override val pattern: Regex) : TokenPattern {
        NORTH(Regex("north")),
        SOUTH(Regex("south")),
        EAST(Regex("east")),
        WEST(Regex("west"))
    }

    class Item(val name: String, var power: Int)
}