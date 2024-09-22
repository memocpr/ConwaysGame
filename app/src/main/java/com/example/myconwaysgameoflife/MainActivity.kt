package com.example.myconwaysgameoflife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myconwaysgameoflife.ui.theme.MyConwaysGameofLifeTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyConwaysGameofLifeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Calls the GameOfLife composable to display the game boards
                    GameOfLife(
                        rows = 5,
                        cols = 5,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GameOfLife(rows: Int, cols: Int, modifier: Modifier = Modifier) {
    // Task-related: Initializes the old board with random 0 (dead) or 1 (alive) values
    val oldBoard = Array(rows) { Array(cols) { Random.nextInt(0, 2) } }

    // Task-related: Initializes the new board where updated states will be stored
    val newBoard = Array(rows) { Array(cols) { 0 } }

    // Task-related: Loops through every cell in the board
    for (row in 0 until rows) {
        for (col in 0 until cols) {
            // Game rule: Counts live neighbors of the current cell
            val liveNeighbors = countLiveNeighbors(oldBoard, row, col)

            // Game rule: Apply Conway's rules to decide the state of the current cell in the new board
            newBoard[row][col] = if (oldBoard[row][col] == 1) { // *** if old board cell is alive (==1)
                // Rule: A live cell dies if it has fewer than 2 or more than 3 live neighbors
                if (liveNeighbors < 2 || liveNeighbors > 3) 0 else 1
            } else { // *** or died (==0)
                // Rule: A dead cell becomes alive if it has exactly 3 live neighbors
                if (liveNeighbors == 3) 1 else 0
            }
        }
    }

    // Task-related: Display both the old and new boards
    Column(modifier = modifier) {
        Text(text = "Old Board:")
        // Task-related: Prints the old board
        oldBoard.forEach { row ->
            Text(text = row.joinToString(" "))
        }
        Text(text = "\nNew Board:")
        // Task-related: Prints the new board after one iteration
        newBoard.forEach { row ->
            Text(text = row.joinToString(" "))
        }
    }
}

// Function to count live neighbors (Game rule)
fun countLiveNeighbors(board: Array<Array<Int>>, row: Int, col: Int): Int {
    var liveCount = 0
    // Loops through the 8 neighbors of the cell
    // Iterate over the 3x3 grid surrounding the current cell at (row, col):
    for (i in -1..1) {
        for (j in -1..1) {
            // for example:  (-1, -1) → top-left neighbor. (0, 1) → right neighbor. (1, 0) → bottom neighbor.
            if (i == 0 && j == 0) continue // Skip the current cell itself
            // Calculate the Neighbor's Position: Example: If the current cell is at (3, 3) and i = -1, j = 0, the calculated neighbor position is (2, 3) (top neighbor).
            val r = row + i
            val c = col + j
            // Check if the neighbor is within bounds and alive (==1)
            if (r in board.indices && c in board[0].indices && board[r][c] == 1) { // r in board.indices checks if r is a valid row index.
                liveCount++
            }
        }
    }
    return liveCount
}

@Preview(showBackground = true)
@Composable
fun GameOfLifePreview() {
    MyConwaysGameofLifeTheme {
        // Preview the game with a 5x5 grid
        GameOfLife(rows = 5, cols = 5)
    }
}
