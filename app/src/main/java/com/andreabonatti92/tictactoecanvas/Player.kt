package com.andreabonatti92.tictactoecanvas

sealed class Player(val symbol: Char) {
    object X : Player('X')
    object O : Player('O')

    // to change between the 2 players
    operator fun not(): Player {
        return if (this is X) O else X
    }
}
