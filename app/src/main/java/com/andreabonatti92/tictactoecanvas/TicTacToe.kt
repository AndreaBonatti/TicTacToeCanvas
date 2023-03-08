package com.andreabonatti92.tictactoecanvas

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TicTacToe(
    playerXColor: Color = Color.Red,
    playerOColor: Color = Color.Green,
    onPlayerWin: (Player) -> Unit,
    onNewRound: () -> Unit = {}
) {
    // the first player mark is always an X
    var selectedPlayer by remember {
        mutableStateOf<Player>(Player.X)
    }

    var center by remember {
        mutableStateOf(Offset.Unspecified)
    }

    // the first gameState is: all the grid cells are empties
    var gameState by remember {
        mutableStateOf(initialGameState())
    }

    val scope = rememberCoroutineScope()
    var isGameRunning by remember {
        mutableStateOf(true)
    }

    // to draw X and O
    var animations = remember {
        emptyAnimations()
    }

    Canvas(
        modifier = Modifier
            .size(300.dp)
            .padding(10.dp)
            .pointerInput(true) {
                detectTapGestures { selectedCoordinate ->
                    if (!isGameRunning) {
                        return@detectTapGestures
                    }
                    // we need to understand if a player select one of our grid cells
                    when {
                        // top-left
                        (selectedCoordinate.x < size.width / 3f && selectedCoordinate.y < size.height / 3f) -> {
                            // only if the cell is empty we will update the cell value
                            if (gameState[0][0] == 'E') {
                                // update the game state
                                gameState = newGameState(gameState, 0, 0, selectedPlayer.symbol)
                                // draw the player mark
                                scope.animateFloatToOne(animations[0][0])
                                // change player
                                selectedPlayer = !selectedPlayer

                            }
                        }
                        // top-middle
                        (selectedCoordinate.x in (size.width / 3f)..(2 * size.width / 3f)
                                && selectedCoordinate.y < size.height / 3f) -> {
                            // only if the cell is empty we will update the cell value
                            if (gameState[1][0] == 'E') {
                                // update the game state
                                gameState = newGameState(gameState, 1, 0, selectedPlayer.symbol)
                                // draw the player mark
                                scope.animateFloatToOne(animations[1][0])
                                // change player
                                selectedPlayer = !selectedPlayer

                            }
                        }
                        // top-right
                        (selectedCoordinate.x > 2 * size.width / 3f && selectedCoordinate.y < size.height / 3f) -> {
                            // only if the cell is empty we will update the cell value
                            if (gameState[2][0] == 'E') {
                                // update the game state
                                gameState = newGameState(gameState, 2, 0, selectedPlayer.symbol)
                                // draw the player mark
                                scope.animateFloatToOne(animations[2][0])
                                // change player
                                selectedPlayer = !selectedPlayer

                            }
                        }
                        // middle-left
                        (selectedCoordinate.x < size.width / 3f
                                && selectedCoordinate.y in (size.height / 3f)..(2 * size.height / 3f)) -> {
                            // only if the cell is empty we will update the cell value
                            if (gameState[0][1] == 'E') {
                                // update the game state
                                gameState = newGameState(gameState, 0, 1, selectedPlayer.symbol)
                                // draw the player mark
                                scope.animateFloatToOne(animations[0][1])
                                // change player
                                selectedPlayer = !selectedPlayer

                            }
                        }
                        // middle-middle = center
                        (selectedCoordinate.x in (size.width / 3f)..(2 * size.width / 3f)
                                && selectedCoordinate.y in (size.height / 3f)..(2 * size.height / 3f)) -> {
                            // only if the cell is empty we will update the cell value
                            if (gameState[1][1] == 'E') {
                                // update the game state
                                gameState = newGameState(gameState, 1, 1, selectedPlayer.symbol)
                                // draw the player mark
                                scope.animateFloatToOne(animations[1][1])
                                // change player
                                selectedPlayer = !selectedPlayer

                            }
                        }
                        // middle-right
                        (selectedCoordinate.x > 2 * size.width / 3f
                                && selectedCoordinate.y in (size.height / 3f)..(2 * size.height / 3f)) -> {
                            // only if the cell is empty we will update the cell value
                            if (gameState[2][1] == 'E') {
                                // update the game state
                                gameState = newGameState(gameState, 2, 1, selectedPlayer.symbol)
                                // draw the player mark
                                scope.animateFloatToOne(animations[2][1])
                                // change player
                                selectedPlayer = !selectedPlayer

                            }
                        }
                        // bottom-left
                        (selectedCoordinate.x < size.width / 3f
                                && selectedCoordinate.y > 2 * size.height / 3f) -> {
                            // only if the cell is empty we will update the cell value
                            if (gameState[0][2] == 'E') {
                                // update the game state
                                gameState = newGameState(gameState, 0, 2, selectedPlayer.symbol)
                                // draw the player mark
                                scope.animateFloatToOne(animations[0][2])
                                // change player
                                selectedPlayer = !selectedPlayer

                            }
                        }
                        // bottom-middle
                        (selectedCoordinate.x in (size.width / 3f)..(2 * size.width / 3f)
                                && selectedCoordinate.y > 2 * size.height / 3f) -> {
                            // only if the cell is empty we will update the cell value
                            if (gameState[1][2] == 'E') {
                                // update the game state
                                gameState = newGameState(gameState, 1, 2, selectedPlayer.symbol)
                                // draw the player mark
                                scope.animateFloatToOne(animations[1][2])
                                // change player
                                selectedPlayer = !selectedPlayer

                            }
                        }
                        // bottom-right
                        (selectedCoordinate.x > 2 * size.width / 3f
                                && selectedCoordinate.y > 2 * size.height / 3f) -> {
                            // only if the cell is empty we will update the cell value
                            if (gameState[2][2] == 'E') {
                                // update the game state
                                gameState = newGameState(gameState, 2, 2, selectedPlayer.symbol)
                                // draw the player mark
                                scope.animateFloatToOne(animations[2][2])
                                // change player
                                selectedPlayer = !selectedPlayer

                            }
                        }
                    }
                    // test log
                    Log.d("TicTacToe", "Game State: ${gameState.contentDeepToString()}")
                    Log.d("TicTacToe", "Next Player: ${selectedPlayer.symbol}")
                    // check if a player have won the game => new game
                    val didPlayerXWin = didPlayerWin(gameState, Player.X)
                    val didPlayerOWin = didPlayerWin(gameState, Player.O)
                    // Trigger the text of the winning player
                    if (didPlayerXWin) {
                        onPlayerWin(Player.X)
                    } else if (didPlayerOWin) {
                        onPlayerWin(Player.O)
                    }
                    // check if the board is full => new game
                    val isBoardFull = gameState.all { row ->
                        row.all { it != 'E' }
                    }
                    if (isBoardFull || didPlayerXWin || didPlayerOWin) {
                        Log.d("TicTacToe", "END GAME")
                        // Start a new game after 5 seconds
                        scope.launch {
                            isGameRunning = false
                            delay(5000L)
                            isGameRunning = true
                            gameState = initialGameState()
                            animations = emptyAnimations()
                            onNewRound()
                        }
                    }
                }
            }
    ) {
        center = this.center

        // Grid
        // Horizontal lines
        drawLine(
            color = Color.Black,
            start = Offset(0f, size.width / 3f),
            end = Offset(size.height, size.width / 3f),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color.Black,
            start = Offset(0f, 2 * size.width / 3f),
            end = Offset(size.height, 2 * size.width / 3f),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )
        // Vertical lines
        drawLine(
            color = Color.Black,
            start = Offset(size.width / 3f, 0f),
            end = Offset(size.width / 3f, size.height),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color.Black,
            start = Offset(2 * size.width / 3f, 0f),
            end = Offset(2 * size.width / 3f, size.height),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )

        // for each cell grid we check the value to known where to draw X or O
        gameState.forEachIndexed { i, row ->
            row.forEachIndexed { j, symbol ->
                if (symbol == Player.X.symbol) {
                    val path1 = Path().apply {
                        moveTo(
                            i * size.width / 3f + size.width / 6f - 50f,
                            j * size.height / 3f + size.height / 6f - 50f
                        )
                        lineTo(
                            i * size.width / 3f + size.width / 6f + 50f,
                            j * size.height / 3f + size.height / 6f + 50f
                        )
                    }
                    val path2 = Path().apply {
                        moveTo(
                            i * size.width / 3f + size.width / 6f - 50f,
                            j * size.height / 3f + size.height / 6f + 50f
                        )
                        lineTo(
                            i * size.width / 3f + size.width / 6f + 50f,
                            j * size.height / 3f + size.height / 6f - 50f,
                        )
                    }
                    val outPath1 = Path()
                    PathMeasure().apply {
                        setPath(path1, false)
                        getSegment(0f, animations[i][j].value * length, outPath1)
                    }
                    val outPath2 = Path()
                    PathMeasure().apply {
                        setPath(path2, false)
                        getSegment(0f, animations[i][j].value * length, outPath2)
                    }
                    drawPath(
                        path = outPath1,
                        color = playerXColor,
                        style = Stroke(
                            width = 5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                    drawPath(
                        path = outPath2,
                        color = playerXColor,
                        style = Stroke(
                            width = 5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                } else if (symbol == Player.O.symbol) {
                    drawArc(
                        color = playerOColor,
                        startAngle = 0f,
                        sweepAngle = animations[i][j].value * 360f,
                        useCenter = false,
                        topLeft = Offset(
                            i * size.width / 3f + size.width / 6f - 50f,
                            j * size.height / 3f + size.height / 6f - 50f
                        ),
                        size = Size(100f, 100f),
                        style = Stroke(
                            width = 5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                }
            }
        }
    }
}

// the matrix represents the initial game state
// E char = empty
// O char = mark of the O Player
// X char = mark of the X Player
fun initialGameState(): Array<CharArray> {
    return arrayOf(
        charArrayOf('E', 'E', 'E'),
        charArrayOf('E', 'E', 'E'),
        charArrayOf('E', 'E', 'E')
    )
}

fun newGameState(
    oldGameState: Array<CharArray>,
    x: Int,
    y: Int,
    symbol: Char
): Array<CharArray> {
    val arrayCopy = oldGameState.copyOf()
    arrayCopy[x][y] = symbol
    return arrayCopy

}

fun didPlayerWin(gameState: Array<CharArray>, player: Player): Boolean {
    val firstRowFull = gameState[0][0] == gameState[1][0] &&
            gameState[1][0] == gameState[2][0] && gameState[0][0] == player.symbol
    val secondRowFull = gameState[0][1] == gameState[1][1] &&
            gameState[1][1] == gameState[2][1] && gameState[0][1] == player.symbol
    val thirdRowFull = gameState[0][2] == gameState[1][2] &&
            gameState[1][2] == gameState[2][2] && gameState[0][2] == player.symbol

    val firstColFull = gameState[0][0] == gameState[0][1] &&
            gameState[0][1] == gameState[0][2] && gameState[0][0] == player.symbol
    val secondColFull = gameState[1][0] == gameState[1][1] &&
            gameState[1][1] == gameState[1][2] && gameState[1][0] == player.symbol
    val thirdColFull = gameState[2][0] == gameState[2][1] &&
            gameState[2][1] == gameState[2][2] && gameState[2][0] == player.symbol

    val firstDiagonalFull = gameState[0][0] == gameState[1][1] &&
            gameState[1][1] == gameState[2][2] && gameState[0][0] == player.symbol
    val secondDiagonalFull = gameState[0][2] == gameState[1][1] &&
            gameState[1][1] == gameState[2][0] && gameState[0][2] == player.symbol

    return firstRowFull || secondRowFull || thirdRowFull || firstColFull ||
            secondColFull || thirdColFull || firstDiagonalFull || secondDiagonalFull
}

private fun emptyAnimations(): ArrayList<ArrayList<Animatable<Float, AnimationVector1D>>> {
    val arrayList = arrayListOf<ArrayList<Animatable<Float, AnimationVector1D>>>()
    for (i in 0..2) {
        arrayList.add(arrayListOf())
        for (j in 0..2) {
            arrayList[i].add(Animatable(0f))
        }
    }
    return arrayList
}

private fun CoroutineScope.animateFloatToOne(animatable: Animatable<Float, AnimationVector1D>) {
    launch {
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500
            )
        )
    }
}

