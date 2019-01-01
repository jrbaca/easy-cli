package com.josephbaca.easycli.processor

import com.josephbaca.easycli.builders.InputProcessorBuilder
import com.josephbaca.easycli.builders.TokenBuilder.arg
import com.josephbaca.easycli.builders.TokenBuilder.command
import com.josephbaca.easycli.builders.Tokenizable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InputProcessorTest {

    private val inputProcessor = buildTestInputProcessor()

    private fun buildTestInputProcessor(): InputProcessor {
        return InputProcessorBuilder()
            .withParser(InputProcessorBuilder.Parsers.Plain)
            .withTokenizer(InputProcessorBuilder.Tokenizers.Basic)
            .withCommands(
                command()
                    .withName("hello")
                    .withAdditionalRegex(Regex("hi"))
                    .withDescription("Says hi")
                    .thatCalls(this::sayHello),

                command()
                    .withName("go")
                    .withAdditionalRegex(Regex("move"))
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

    @Test
    fun functionWithOneArg() {

        val response = inputProcessor.parse("go north")
        assertEquals("going north", response)
    }

    @Test
    fun getHelp() {

        val response = inputProcessor.help()
        assertEquals("", response)
    }


    // Simple funcs
    @SuppressWarnings("WeakerAccess")
    fun sayHello(): String {
        return "Hello world"
    }

    // Use enums
    @SuppressWarnings("WeakerAccess")
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

    enum class Direction(override val pattern: Regex) : Tokenizable {
        NORTH(Regex("north")),
        SOUTH(Regex("south")),
        EAST(Regex("east")),
        WEST(Regex("west"))
    }

    class Item(val name: String, var power: Int)
}