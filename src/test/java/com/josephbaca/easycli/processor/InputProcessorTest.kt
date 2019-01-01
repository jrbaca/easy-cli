package com.josephbaca.easycli.processor

import com.josephbaca.easycli.builders.InputProcessorBuilder
import com.josephbaca.easycli.builders.TokenBuilder.arg
import com.josephbaca.easycli.builders.TokenBuilder.command
import com.josephbaca.easycli.builders.Tokenizable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InputProcessorTest {

    private val inventoryItems = setOf(Item("sword", 2), Item("Knife", 3))
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
                    ),
                command()
                    .withName("equip").hasArg(
                        arg().withName("item").from(inventoryItems)
                    ).withDescription("Equips specified item").thatCalls(this::equip)
//                command()
//                    .withName("change power").hasArg(
//                        arg().withName("item").from(inventoryItems)
//                    ).hasArg(
//                        arg().withName("power").from(inventoryItems)
//                    ).withDescription("Changes power of specified item").thatCalls(this::modifyItemPower)
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
    fun functionWithOneNonEnumArg() {

        val response = inputProcessor.parse("equip sword")
        assertEquals("Equipping sword(Power: 2)", response)
    }

    @Test
    fun functionWithTwoArgs() {

        val response = inputProcessor.parse("change power sword 2")
        assertEquals("Item power changed to 2!", response)
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
        return "going %s".format(direction.name.toLowerCase())
    }

    // Use classes
    @SuppressWarnings("WeakerAccess")
    fun equip(item: Item): String {
        return "Equipping %s(Power: %s)".format(item.name, item.power)
    }

    // Use multiple args
    @SuppressWarnings("WeakerAccess")
    fun modifyItemPower(item: Item, power: Int): String {
        item.power = power
        return "Item power changed to %s!".format(item.power)
    }

    enum class Direction(override val pattern: Regex) : Tokenizable {
        NORTH(Regex("north")),
        SOUTH(Regex("south")),
        EAST(Regex("east")),
        WEST(Regex("west"))
    }

    class Item(val name: String, var power: Int) : Tokenizable {
        override val pattern = Regex(name)

        override fun toString(): String {
            return "%s(%s)".format(name, power)
        }
    }
}