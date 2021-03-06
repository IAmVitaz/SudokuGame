package com.vitaz.sudoku.view

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.vitaz.sudoku.R
import com.vitaz.sudoku.game.Cell
import com.vitaz.sudoku.viewmodel.PlaySudokuViewmodel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SudokuBoardView.OnTouchListener {

    private lateinit var viewModel: PlaySudokuViewmodel
    private lateinit var numberButtons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sudokuBoardView.registerListener(this)

        viewModel = ViewModelProviders.of(this).get(PlaySudokuViewmodel::class.java)

        viewModel.sudokuGame.selectedCellLiveData.observe(this, Observer { updateSelectedCellUI(it) })
        viewModel.sudokuGame.cellsLiveData.observe(this, Observer { updateCells(it) })
        viewModel.sudokuGame.isTakingNotesLiveData.observe(this, Observer { updateNoteTakingUI(it) })
        viewModel.sudokuGame.highlightedKeysLiveData.observe(this, Observer { updateHighlightedKeys(it) })

        numberButtons = listOf(oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton)
        numberButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                viewModel.sudokuGame.handleInput(index + 1)
            }
        }

        notesButton.setOnClickListener {
            viewModel.sudokuGame.changeNoteTakingState()
        }
        deleteButton.setOnClickListener {
            viewModel.sudokuGame.delete()
        }
    }

    private fun updateCells(cells: List<Cell>?) {
        cells?.let {
            sudokuBoardView.updateCells(cells)
        }
    }

    private fun updateSelectedCellUI(cell: Pair<Int, Int>?) {
        cell?.let {
            sudokuBoardView.updateSelectedCellUI(cell.first, cell.second)
        }
    }

    private fun updateNoteTakingUI(isNoteTaking: Boolean?) {
        isNoteTaking?.let {
            val color = if (it) ContextCompat.getColor(this, R.color.colorPrimary) else Color.LTGRAY
            notesButton.background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        }
    }

    private fun updateHighlightedKeys(set: Set<Int>?) {
        set?.let {
            numberButtons.forEachIndexed() {index, button ->
                val color = if (set.contains(index + 1)) ContextCompat.getColor(this, R.color.colorPrimary) else Color.LTGRAY
                button.setBackgroundColor(color)
                button.background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
            }
        }
    }

    override fun onCellTouched(row: Int, col: Int) {
        viewModel.sudokuGame.updateSelectedCell(row, col)
    }
}