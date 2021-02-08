package com.vitaz.sudoku.game

import androidx.lifecycle.MutableLiveData

class SudokuGame {

    var selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()
    var cellsLiveData = MutableLiveData<List<Cell>>()

    private var selectedRow = -1
    private var selectedColumn = -1

    private val board: Board

    init {
        val cells = List(9 * 9) {i -> Cell(i / 9, i % 9, i % 9)}
        board = Board(9, cells)

        selectedCellLiveData.postValue(Pair(selectedRow, selectedColumn))
        cellsLiveData.postValue(board.cells)
    }

    fun handleInput(number: Int) {
        if (selectedRow == -1 || selectedColumn == -1) return

        board.getCell(selectedRow, selectedColumn).value = number
        cellsLiveData.postValue(board.cells)
    }

    fun updateSelectedCell (row: Int, col: Int) {
        selectedRow = row
        selectedColumn = col
        selectedCellLiveData.postValue(Pair(row, col))
    }

}