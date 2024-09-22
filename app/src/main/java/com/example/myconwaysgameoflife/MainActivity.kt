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
    val oldBoard = Array(rows) { Array(cols) { Random.nextInt(0, 2) } }
    val newBoard = Array(rows) { Array(cols) { 0 } }

    // Apply the rules of Conway's Game of Life
    for (row in 0 until rows) {
        for (col in 0 until cols) {
            val liveNeighbors = countLiveNeighbors(oldBoard, row, col)
            newBoard[row][col] = if (oldBoard[row][col] == 1) {
                if (liveNeighbors < 2 || liveNeighbors > 3) 0 else 1
            } else {
                if (liveNeighbors == 3) 1 else 0
            }
        }
    }

    // Display both old and new boards in a simple text format
    Column(modifier = modifier) {
        Text(text = "Old Board:")
        oldBoard.forEach { row ->
            Text(text = row.joinToString(" "))
        }
        Text(text = "\nNew Board:")
        newBoard.forEach { row ->
            Text(text = row.joinToString(" "))
        }
    }
}

// Function to count live neighbors
fun countLiveNeighbors(board: Array<Array<Int>>, row: Int, col: Int): Int {
    var liveCount = 0
    for (i in -1..1) {
        for (j in -1..1) {
            if (i == 0 && j == 0) continue
            val r = row + i
            val c = col + j
            if (r in board.indices && c in board[0].indices && board[r][c] == 1) {
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
        GameOfLife(rows = 5, cols = 5)
    }
}
