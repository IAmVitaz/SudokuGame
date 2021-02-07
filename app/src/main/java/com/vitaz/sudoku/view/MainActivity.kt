package com.vitaz.sudoku.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.vitaz.sudoku.R
import com.vitaz.sudoku.viewmodel.PlaySudokuViewmodel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SudokuBoardView.OnTouchListener {

    private lateinit var viewModel: PlaySudokuViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sudokuBoardView.registerListener(this)

        viewModel = ViewModelProviders.of(this).get(PlaySudokuViewmodel::class.java)

        viewModel.sudokuGame.selectedCellLiveData.observe(this, Observer { updateSelectedCellUI(it) })

    }

    private fun updateSelectedCellUI(cell: Pair<Int, Int>?) {
        cell?.let {
            sudokuBoardView.updateSelectedCellUI(cell.first, cell.second)
        }
    }

    override fun onCellTouched(row: Int, col: Int) {
        viewModel.sudokuGame.updateSelectedCell(row, col)
    }
}