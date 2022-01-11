package com.purnendu.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.google.android.material.button.MaterialButton
import com.purnendu.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var binding: ActivityMainBinding
    private lateinit var board: Array<Array<MaterialButton>>
    private lateinit var boardStatus: Array<Array<Int>>


    private var count: Int = 0
    private var turnCount: Int = 0
    private var player: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //Set default night mode -> no
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        //Initialization of Arrays
        board = arrayOf(
            arrayOf(binding.btn1, binding.btn2, binding.btn3),
            arrayOf(binding.btn4, binding.btn5, binding.btn6),
            arrayOf(binding.btn7, binding.btn8, binding.btn9)
        )

        boardStatus = arrayOf(
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0)
        )

        initializeBoard()


        binding.reset.setOnClickListener {

            turnCount = 0
            count = 0
            player = true
            binding.playerTurn.text = "Player X turn"
            initializeBoard()
            binding.vm.visibility = View.INVISIBLE
            binding.ht.visibility = View.INVISIBLE
            binding.hb.visibility = View.INVISIBLE
            binding.vr.visibility = View.INVISIBLE
            binding.vl.visibility = View.INVISIBLE
            setMiddleVerticalLineToDefaultState()
        }

    }

    private fun initializeBoard() {

        for (i in 0..2) {
            for (j in 0..2) {
                count++
                board[i][j].setOnClickListener(this)
                boardStatus[i][j] = -1
                board[i][j].isEnabled = true
                board[i][j].text = ""

            }
        }
    }

    private fun updateValue(row: Int, column: Int, player: Boolean) {
        val temp: String = if (player) "X" else "0"
        val tempInt: Int = if (player) 1 else 0
        board[row][column].text = temp
        boardStatus[row][column] = tempInt
        board[row][column].isEnabled = false
    }

    private fun checkWinner() {
        var line = ""

        //Horizontally Check
        for (i in 0..2) {
            if ((boardStatus[i][0] == boardStatus[i][1]) && (boardStatus[i][0] == boardStatus[i][2])) {
                if (boardStatus[i][0] == 1) {
                    binding.playerTurn.text = "Player X win"
                    line = "row:$i"
                    break
                } else if (boardStatus[i][0] == 0) {
                    binding.playerTurn.text = "Player 0 win"
                    line = "row:$i"
                    break
                }

            }
        }

        //Vertically Check
        for (i in 0..2) {
            if ((boardStatus[0][i] == boardStatus[1][i]) && (boardStatus[0][i] == boardStatus[2][i])) {
                if (boardStatus[0][i] == 1) {
                    binding.playerTurn.text = "Player X win"
                    line = "column:$i"
                    break
                } else if (boardStatus[0][i] == 0) {
                    binding.playerTurn.text = "Player 0 win"
                    line = "column:$i"
                    break
                }
            }
        }

        //Diagonally Check
        if ((boardStatus[0][0] == boardStatus[1][1]) && (boardStatus[0][0] == boardStatus[2][2])) {
            if (boardStatus[0][0] == 1) {
                line = "corner:l"
                binding.playerTurn.text = "Player X win"
            } else if (boardStatus[0][0] == 0) {
                line = "corner:l"
                binding.playerTurn.text = "Player 0 win"
            }


        }

        if ((boardStatus[0][2] == boardStatus[1][1]) && (boardStatus[0][2] == boardStatus[2][0])) {
            if (boardStatus[0][2] == 1) {
                binding.playerTurn.text = "Player X win"
                line = "corner:r"
            } else if (boardStatus[0][2] == 0) {
                binding.playerTurn.text = "Player 0 win"
                line = "corner:r"
            }
        }
        lineDraw(line)
        disableButton()

    }

    private fun lineDraw(line: String) {
        if (line.isNotEmpty()) {

            when (line) {
                "row:0" -> binding.ht.visibility = View.VISIBLE
                "row:1" -> {
                    val buttonWidth: Int = binding.btn3.width
                    val width: Int = (buttonWidth * 3)
                    binding.vm.apply {
                        rotation = 90F
                        layoutParams.height = width
                        visibility = View.VISIBLE
                    }
                }

                "row:2" -> binding.hb.visibility = View.VISIBLE
                "corner:l" -> {
                    binding.vm.apply {
                        rotation = -33F
                        visibility = View.VISIBLE
                    }
                }
                "corner:r" -> {
                    binding.vm.apply {
                        rotation = 33F
                        visibility = View.VISIBLE
                    }
                }
                "column:0" -> {
                    val buttonWidth: Int = binding.btn1.width
                    val width: Int = (buttonWidth / 2) + 20
                    val params: RelativeLayout.LayoutParams =
                        binding.vr.layoutParams as RelativeLayout.LayoutParams
                    params.setMargins(width, 200, 0, 200)//left, top, right, bottom
                    binding.vr.apply {
                        layoutParams = params
                        visibility = View.VISIBLE
                    }
                }
                "column:1" -> {
                    val params: RelativeLayout.LayoutParams =
                        binding.vm.layoutParams as RelativeLayout.LayoutParams
                    params.setMargins(0, 200, 0, 200) //left, top, right, bottom
                    binding.vm.apply {
                        layoutParams = params
                        visibility = View.VISIBLE
                    }
                }
                "column:2" -> {
                    val buttonWidth: Int = binding.btn3.width
                    val width: Int = (buttonWidth / 2) + 20
                    val params: RelativeLayout.LayoutParams =
                        binding.vl.layoutParams as RelativeLayout.LayoutParams
                    params.setMargins(0, 200, width, 200)//left, top, right, bottom
                    binding.vl.apply {
                        layoutParams = params
                        visibility = View.VISIBLE
                    }
                }
            }

        }

    }

    private fun disableButton() {

        if (binding.playerTurn.text.toString().contains("win")) {
            for (i in 0..2) {
                for (j in 0..2) {
                    board[i][j].isEnabled = false
                }
            }
        }

    }

    private fun setMiddleVerticalLineToDefaultState() {

        binding.vm.rotation = 0F
        val buttonWidth: Int = binding.btn2.height
        val height: Int = (buttonWidth * 3)
        binding.vm.layoutParams.height = height
    }


    override fun onClick(view: View) {

        when (view.id) {

            (R.id.btn1) -> updateValue(0, 0, player)
            (R.id.btn2) -> updateValue(0, 1, player)
            (R.id.btn3) -> updateValue(0, 2, player)
            (R.id.btn4) -> updateValue(1, 0, player)
            (R.id.btn5) -> updateValue(1, 1, player)
            (R.id.btn6) -> updateValue(1, 2, player)
            (R.id.btn7) -> updateValue(2, 0, player)
            (R.id.btn8) -> updateValue(2, 1, player)
            (R.id.btn9) -> updateValue(2, 2, player)
        }

        turnCount++
        player = !player

        if (player) binding.playerTurn.text = "Player X turn" else binding.playerTurn.text =
            "Player 0 turn"

        if (turnCount == 9) binding.playerTurn.text = "Game Draw"

        checkWinner()
    }
}