package com.badjack.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var currTurn: Int = 1
    private val player1Symbol: String = "✔️"
    private val player2Symbol: String = "❌"
    private var status: Int = -1
    private var gameMatrix = arrayOf(arrayOf(0, 0, 0), arrayOf(0, 0, 0), arrayOf(0, 0, 0))
    var vsComputer = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt_turn.text = "Player 1 Turn"
        switch_player.setOnCheckedChangeListener { compoundButton, b ->  playerSwitchHandler(compoundButton)}
    }

    fun checkGameStatus() {
        for (row in gameMatrix) {
            var count1 = 0;
            var count2 = 0;
            for (tile in row) {
                when (tile) {
                    1 -> count1++
                    2 -> count2++
                }
            }
            if (count1 == 3) {
                status = 1
            }
            if (count2 == 3) {
                status = 2
            }
        }
        for (i in 0..2) {
            var count1 = 0;
            var count2 = 0;
            for (j in 0..2) {
                when (gameMatrix[j][i]) {
                    1 -> count1++
                    2 -> count2++
                }
            }
            if (count1 == 3) {
                status = 1
            }
            if (count2 == 3) {
                status = 2
            }
        }
        if (gameMatrix[0][0] == gameMatrix[1][1] && gameMatrix[1][1] == gameMatrix[2][2]) {
            if (gameMatrix[0][0] == 1) {
                status = 1
            } else if (gameMatrix[0][0] == 2) {
                status = 2
            }
        }
        if (gameMatrix[0][2] == gameMatrix[1][1] && gameMatrix[1][1] == gameMatrix[2][0]) {
            if (gameMatrix[0][2] == 1) {
                status = 1
            } else if (gameMatrix[0][2] == 2) {
                status = 2
            }
        }
        if(status == -1) {
            var count = 0
            for (i in 0..2) {
                for (j in 0..2) {
                    if (gameMatrix[i][j] == 1 || gameMatrix[i][j] == 2) {
                        count++
                    }
                }
            }
            if (count == 9) {
                status = 0
            }
        }
    }

    fun gameTileClickHandler(view: View) {
        val selectedButton = view as Button
        Log.d("Clicked Button", selectedButton.tag.toString())
        if ((selectedButton.text != player1Symbol && selectedButton.text != player2Symbol) && status == -1) {
            val buttonID = selectedButton.tag.toString().split('_')
            val row = Integer.parseInt(buttonID[1])
            val col = Integer.parseInt(buttonID[2])
            gameMatrix[row][col] = currTurn
            when (currTurn) {
                1 -> {
                    selectedButton.text = player1Symbol
                }
                else -> {
                    selectedButton.text = player2Symbol
                }
            }
            checkGameStatus()
            if (status == -1) {
                when (currTurn) {
                    1 -> {
                        txt_turn.text = "Player 2 Turn"
                        currTurn = 2
                    }
                    else -> {
                        txt_turn.text = "Player 1 Turn"
                        currTurn = 1
                    }
                }
            } else if (status == 0) {
                txt_turn.text = "Draw!"
            } else {
                txt_turn.text = "Player $status won!"
            }
        }
        if (currTurn == 2 && vsComputer && status == -1) {
            performComputerTurn(selectedButton)
            checkGameStatus()
            if (status == -1) {
                when (currTurn) {
                    1 -> {
                        txt_turn.text = "Player 2 Turn"
                        currTurn = 2
                    }
                    else -> {
                        txt_turn.text = "Player 1 Turn"
                        currTurn = 1
                    }
                }
            } else if (status == 0) {
                txt_turn.text = "Draw!"
            } else {
                txt_turn.text = "Player $status won!"
            }
        }
    }

    private fun performComputerTurn(selectedButton :Button) {
        val freeIndexes = ArrayList<String>()
        for (i in 0..2){
            for (j in 0..2){
                if (gameMatrix[i][j] != 1 && gameMatrix[i][j] != 2){
                    freeIndexes.add("${i.toString()}${j.toString()}")
                }
            }
        }
        val randMove = (0 until freeIndexes.size).random()
        val row = freeIndexes[randMove][0].digitToInt()
        val col = freeIndexes[randMove][1].digitToInt()
        gameMatrix[row][col] = 2
        when(freeIndexes[randMove]){
            "00"->{
                button_0_0.text= player2Symbol
            }
            "01"->{
                button_0_1.text= player2Symbol
            }
            "02"->{
                button_0_2.text= player2Symbol
            }
            "10"->{
                button_1_0.text= player2Symbol
            }
            "11"->{
                button_1_1.text= player2Symbol
            }
            "12"->{
                button_1_2.text= player2Symbol
            }
            "20"->{
                button_2_0.text= player2Symbol
            }
            "21"->{
                button_2_1.text= player2Symbol
            }
            "22"->{
                button_2_2.text= player2Symbol
            }
        }
    }

    fun btnResetClickHandler(view: View) {
        button_0_0.text = ""
        button_0_1.text = ""
        button_0_2.text = ""
        button_1_0.text = ""
        button_1_1.text = ""
        button_1_2.text = ""
        button_2_0.text = ""
        button_2_1.text = ""
        button_2_2.text = ""
        txt_turn.text = "Player 1 Turn"
        currTurn = 1
        for (i in 0..2) {
            for (j in 0..2) {
                gameMatrix[i][j] = 0
            }
        }
        status = -1
    }

    fun playerSwitchHandler(view: View) {
        btnResetClickHandler(button_reset)
        when (vsComputer) {
            true -> {
                vsComputer = false
                switch_player.text = "Play With Computer"
            }
            else -> {
                vsComputer = true
                switch_player.text = "Play With Human"
            }
        }
    }
}