package com.purnendu.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

class MainActivityJava extends AppCompatActivity implements View.OnClickListener {


    MaterialButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, reset;
    TextView playerTurn;
    MaterialButton[][] board;
    int[][] boardStatus;
    int count = 0, turnCount = 0;
    boolean player = true;
    View vm, ht, hb, vr, vl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setFindViewById();

        board = new MaterialButton[3][3];
        boardStatus = new int[3][3];

        initializeBoard();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnCount = 0;
                count = 0;
                player = true;
                playerTurn.setText("Player 1 turn");
                initializeBoard();
                vm.setVisibility(View.INVISIBLE);
                ht.setVisibility(View.INVISIBLE);
                hb.setVisibility(View.INVISIBLE);
                vr.setVisibility(View.INVISIBLE);
                vl.setVisibility(View.INVISIBLE);
                setMiddleVerticalLineToDefaultState();
            }
        });


    }

    private void setFindViewById() {


        playerTurn = findViewById(R.id.playerTurn);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        reset = findViewById(R.id.reset);
        vm = findViewById(R.id.vm);
        ht = findViewById(R.id.ht);
        hb = findViewById(R.id.hb);
        vr = findViewById(R.id.vr);
        vl = findViewById(R.id.vl);

    }

    private MaterialButton getButton(int btnNo) {
        switch (btnNo) {
            case (1):
                return btn1;
            case (2):
                return btn2;
            case (3):
                return btn3;
            case (4):
                return btn4;
            case (5):
                return btn5;
            case (6):
                return btn6;
            case (7):
                return btn7;
            case (8):
                return btn8;
            case (9):
                return btn9;

        }
        return null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.btn1): {
                updateValue(0, 0, player);
                break;
            }
            case (R.id.btn2): {
                updateValue(0, 1, player);
                break;
            }
            case (R.id.btn3): {
                updateValue(0, 2, player);
                break;
            }
            case (R.id.btn4): {
                updateValue(1, 0, player);
                break;
            }
            case (R.id.btn5): {
                updateValue(1, 1, player);
                break;
            }
            case (R.id.btn6): {
                updateValue(1, 2, player);
                break;
            }
            case (R.id.btn7): {
                updateValue(2, 0, player);
                break;
            }
            case (R.id.btn8): {
                updateValue(2, 1, player);
                break;
            }
            case (R.id.btn9): {
                updateValue(2, 2, player);
                break;
            }
        }
        turnCount++;
        player = !player;
        if (player)
            playerTurn.setText("Player X turn");
        else
            playerTurn.setText("Player 0 turn");

        if (turnCount == 9)
            playerTurn.setText("Game Draw");

        checkWinner();

    }


    private void initializeBoard() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                count++;
                MaterialButton btn = getButton(count);
                if (btn != null) {
                    board[i][j] = btn;
                    board[i][j].setOnClickListener(this);
                    boardStatus[i][j] = -1;
                    board[i][j].setEnabled(true);
                    board[i][j].setText("");
                }

            }
        }
    }

    private void updateValue(int row, int column, boolean player) {
        String temp = player ? "X" : "0";
        int tempInt = player ? 1 : 0;
        board[row][column].setText(temp);
        boardStatus[row][column] = tempInt;
        board[row][column].setEnabled(false);
    }

    private void checkWinner() {

        String line = "";

        //Horizontally Check
        for (int i = 0; i < 3; i++) {
            if ((boardStatus[i][0] == boardStatus[i][1]) && (boardStatus[i][0] == boardStatus[i][2])) {
                if (boardStatus[i][0] == 1) {
                    playerTurn.setText("Player 1 win");
                    line = "row" + ":" + i;
                    break;
                } else if (boardStatus[i][0] == 0) {
                    playerTurn.setText("Player 2 win");
                    line = "row" + ":" + i;
                    break;
                }

            }
        }

        //Vertically Check
        for (int i = 0; i < 3; i++) {
            if ((boardStatus[0][i] == boardStatus[1][i]) && (boardStatus[0][i] == boardStatus[2][i])) {
                if (boardStatus[0][i] == 1) {
                    playerTurn.setText("Player 1 win");
                    line = "column" + ":" + i;
                    break;
                } else if (boardStatus[0][i] == 0) {
                    playerTurn.setText("Player 2 win");
                    line = "column" + ":" + i;
                    break;
                }
            }
        }

        //Diagonally Check
        if ((boardStatus[0][0] == boardStatus[1][1]) && (boardStatus[0][0] == boardStatus[2][2])) {
            if (boardStatus[0][0] == 1) {
                line = "corner:l";
                playerTurn.setText("Player 1 win");
            } else if (boardStatus[0][0] == 0) {
                line = "corner:l";
                playerTurn.setText("Player 2 win");
            }


        }

        if ((boardStatus[0][2] == boardStatus[1][1]) && (boardStatus[0][2] == boardStatus[2][0])) {
            if (boardStatus[0][2] == 1) {
                playerTurn.setText("Player 1 win");
                line = "corner:r";
            } else if (boardStatus[0][2] == 0) {
                playerTurn.setText("Player 2 win");
                line = "corner:r";
            }
        }
        lineDraw(line);
        disableButton();
    }

    private void lineDraw(String line) {
        if (!line.isEmpty()) {
            switch (line) {
                case "row:0":
                    ht.setVisibility(View.VISIBLE);
                    break;
                case "row:1": {
                    int buttonWidth = btn3.getWidth();
                    int width = (buttonWidth * 3);
                    vm.setRotation(90);
                    vm.getLayoutParams().height = width;
                    vm.setVisibility(View.VISIBLE);
                    break;
                }
                case "row:2":
                    hb.setVisibility(View.VISIBLE);
                    break;
                case "corner:l":
                    vm.setRotation(-33);
                    vm.setVisibility(View.VISIBLE);
                    break;
                case "corner:r":
                    vm.setRotation(33);
                    vm.setVisibility(View.VISIBLE);
                    break;
                case "column:0": {
                    int buttonWidth = btn1.getWidth();
                    int width = (buttonWidth / 2) + 20;
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vr.getLayoutParams();
                    params.setMargins(width, 200, 0, 200); //left, top, right, bottom
                    vr.setLayoutParams(params);
                    vr.setVisibility(View.VISIBLE);
                    break;
                }
                case "column:1": {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vm.getLayoutParams();
                    params.setMargins(0, 200, 0, 200); //left, top, right, bottom
                    vm.setLayoutParams(params);
                    vm.setVisibility(View.VISIBLE);
                    break;
                }
                case "column:2": {
                    int buttonWidth = btn3.getWidth();
                    int width = (buttonWidth / 2) + 20;
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vl.getLayoutParams();
                    params.setMargins(0, 200, width, 200); //left, top, right, bottom
                    vl.setLayoutParams(params);
                    vl.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    }

    private void disableButton() {

        if (playerTurn.getText().toString().contains("win")) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j].setEnabled(false);
                }
            }
        }
    }

    private void setMiddleVerticalLineToDefaultState() {
        vm.setRotation(0);
        int buttonWidth = btn2.getHeight();
        int height = (buttonWidth * 3);
        vm.getLayoutParams().height = height;
    }

}