package com.josephbaca.easycli.tokenizer

interface TokenPattern {
    val name: String
    val patterns: Set<Regex>
}