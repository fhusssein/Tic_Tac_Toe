/*
    Program Name: Tic_Tac_Toe
    ManinActivity.Java

    Purpose: To allow user to play a two player Tic Tac Toe game.

    Revision History:

    Written by Faisal Hussein Oct 2021
*/

package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore;
    //Button array
    private Button aButtons[][] = new Button[3][3];
    //Reset Button
    private Button resetGame;

    //Score values for player and roundCount = number of tiles
    private int playerOneScoreValue, playerTwoScoreValue, roundCount;
    boolean turnPlayer;

    //p1 => 0
    //p2 => 1
    //empty => 2
    int [] gameState = {2,2,2,2,2,2,2,2,2};

    //Victory Conditions
    int [][] victoryConditions = {
            {0,1,2}, {3,4,5}, {6,7,8}, // rows
            {0,3,6}, {1,4,7}, {2,5,8}, // columns
            {0,4,8}, {2,4,6} // diagonal
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main
        );

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);

        resetGame = (Button) findViewById(R.id.resetButton);

        //Assigns Buttons to array
        aButtons[0][0] = findViewById(R.id.btn_1);
        aButtons[0][1] = findViewById(R.id.btn_2);
        aButtons[0][2] = findViewById(R.id.btn_3);
        aButtons[1][0] = findViewById(R.id.btn_4);
        aButtons[1][1] = findViewById(R.id.btn_5);
        aButtons[1][2] = findViewById(R.id.btn_6);
        aButtons[2][0] = findViewById(R.id.btn_7);
        aButtons[2][1] = findViewById(R.id.btn_8);
        aButtons[2][2] = findViewById(R.id.btn_9);

        //Sets on click
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                aButtons[i][j].setOnClickListener(this);
            }
        }

        playerOneScoreValue = 0;
        playerTwoScoreValue = 0;
        roundCount = 0;
        turnPlayer = true;
    }


    @Override
    public void onClick(View view) {



        //If a button has been pressed (not empty) it cannot be selected again
        if(!((Button)view).getText().toString().equals("")){
            Toast.makeText(this, "Used Tile! select another", Toast.LENGTH_SHORT).show();
            return;
        }

        //If a button has been pressed get its ID and store it
        String buttonID = view.getResources().getResourceEntryName(view.getId());

        //Gets the last character of the string and stores as index
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        //If statements to check which player is active
        if(turnPlayer) {
            //Adds X to tile
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#FFFFFF"));
            gameState[gameStatePointer-1] = 0;

        }else{
            //Adds O if turnPlayer is false
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#FFFFFF"));
            gameState[gameStatePointer-1] = 1;

        }

        roundCount++;
        //If a player has won declare te winner and restart the game
        if(checkForVictor()){
            if(turnPlayer){
                playerOneScoreValue++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
                newGame();
            }else{
                playerTwoScoreValue++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
                newGame();
            }
        } else if(roundCount == 9){
            //If all tiles are filled and no one has won restart game
            newGame();
            Toast.makeText(this,"Draw!",Toast.LENGTH_SHORT).show();
        }else{
            turnPlayer = !turnPlayer;
        }

        //reset button is pressed
        resetGame.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                newGame();
                playerOneScoreValue = 0;
                playerTwoScoreValue = 0;
                updatePlayerScore();
            }
        });

    }

    // checks if victory conditions were achieved
    public boolean checkForVictor() {
        boolean victor = false;
        for (int[] victoryCondition: victoryConditions){
            if(gameState[victoryCondition[0]] == gameState[victoryCondition[1]] &&
                    gameState[victoryCondition[1]] == gameState[victoryCondition[2]]
                    && gameState[victoryCondition[0]] != 2)
            {
                victor = true;
            }
        }
        return victor;
    }

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreValue));
        playerTwoScore.setText(Integer.toString(playerTwoScoreValue));
    }

    public void newGame(){
        for (int i = 0; i < 3; i++) {
            //gameState 2 is meant to be blank
            for (int j = 0; j < 3; j++) {
                aButtons[i][j].setText("");
                gameState[j] = 2;
            }
        }
        roundCount = 0;
        turnPlayer = !turnPlayer;

    }
}