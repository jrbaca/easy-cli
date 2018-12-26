package com.josephbaca.easycli


/**
 * Interface for any class that can be used in [GameStateManager],  [Parserold], etc.
 */
interface Context {
    /**
     * Set of all tokens.
     */
    val allTokens: Set<Token>

    /**
     * Combined map of local and global verb tokens.
     */
    val allVerbTokens: Map<VerbToken, (List<NounToken>) -> String?>
}
