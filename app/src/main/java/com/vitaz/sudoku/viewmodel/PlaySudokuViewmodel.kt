package com.vitaz.sudoku.viewmodel

import androidx.lifecycle.ViewModel
import com.vitaz.sudoku.game.SudokuGame

class PlaySudokuViewmodel: ViewModel() {

    val sudokuGame = SudokuGame()

}