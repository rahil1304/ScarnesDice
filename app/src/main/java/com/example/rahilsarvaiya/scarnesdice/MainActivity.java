package com.example.rahilsarvaiya.scarnesdice;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int winning_score=50,overall_score=0,turn_score=0,computerScore=0;
    private int MAX_COMP_TURN_SCORE=20;

    boolean userTurn;
    boolean gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userTurn = true;
        final TextView overallscore = (TextView) findViewById(R.id.overallScore);
        final TextView score = (TextView) findViewById(R.id.textView);
        final TextView compScore=(TextView)findViewById(R.id.textView2);
        Button b1 = (Button) findViewById(R.id.button);
        Button b2 = (Button) findViewById(R.id.button2);
        Button b3 = (Button) findViewById(R.id.button3);
        final ImageView dice = (ImageView) findViewById(R.id.imageView);
       final Random r;



        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (userTurn && !gameOver) {
                    roll();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              hold();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reset();
            }
        });
    }
        public void switchTurn(){

            TextView turnView = (TextView) findViewById(R.id.turnView);

            if (userTurn) turnView.setText("Computer's Turn");
            else turnView.setText("Your Turn");

            userTurn = !userTurn;


            if (userTurn) {
                Button rollButton = (Button) findViewById(R.id.button);
                rollButton.setEnabled(true);
                Button holdButton = (Button) findViewById(R.id.button2);
                holdButton.setEnabled(true);
            }

            if (!userTurn) computerTurn();
        }

    public void computerTurn() {

        final Handler handler = new Handler();

        // disable roll & hold buttons
        Button rollButton = (Button) findViewById(R.id.button);
        rollButton.setEnabled(false);
        Button holdButton = (Button) findViewById(R.id.button2);
        holdButton.setEnabled(false);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if ((turn_score + computerScore >= winning_score) || (turn_score >= MAX_COMP_TURN_SCORE)) hold();
                else {
                    roll();
                }

                if (!userTurn) handler.postDelayed(this, 1000);
                else updateRoundScore(0);
            }
        }, 1000);

        updateScore();

        turn_score = 0;
        updateRoundScore(0);

//        rollButton.setEnabled(true);
//        holdButton.setEnabled(true);
    }
    public void roll() {
        Random r=new Random();
        int randomNumber = r.nextInt(6);
        final TextView score = (TextView) findViewById(R.id.textView);
        ImageView dice = (ImageView)findViewById(R.id.imageView);

        if (randomNumber == 1) {
            dice.setImageDrawable(getResources().getDrawable(R.drawable.dice1));
            turn_score = 0;
            updateRoundScore(turn_score);
            switchTurn();

        } else if (randomNumber == 2) {
            dice.setImageDrawable(getResources().getDrawable(R.drawable.dice2));
            turn_score = turn_score + 2;
            updateRoundScore(turn_score);


        } else if (randomNumber == 3) {

            dice.setImageDrawable(getResources().getDrawable(R.drawable.dice3));
            turn_score = turn_score + 3;
            updateRoundScore(turn_score);

        } else if (randomNumber == 4) {
            dice.setImageDrawable(getResources().getDrawable(R.drawable.dice4));
            turn_score = turn_score + 4;
            updateRoundScore(turn_score);
        } else if (randomNumber == 5) {
            dice.setImageDrawable(getResources().getDrawable(R.drawable.dice5));
            turn_score = turn_score + 5;
            updateRoundScore(turn_score);

        } else if (randomNumber == 6) {
            dice.setImageDrawable(getResources().getDrawable(R.drawable.dice6));
            turn_score = turn_score + 6;
            updateRoundScore(turn_score);

        }


    }

    public void hold() {

        if (userTurn) overall_score += turn_score;
        else computerScore += turn_score;

        updateScore();

       turn_score = 0;
        updateRoundScore(0);

        if ((overall_score > winning_score) || (computerScore > winning_score)) gameOver();

        if (!gameOver) switchTurn();
    }
    public void gameOver() {
        // disable roll & hold buttons
        Button rollButton = (Button) findViewById(R.id.button);
        rollButton.setEnabled(false);
        Button holdButton = (Button) findViewById(R.id.button2);
        holdButton.setEnabled(false);

        gameOver = true;
        updateScore();
    }

    public void updateScore() {


                TextView ys = (TextView) findViewById(R.id.overallScore);
                TextView cs=(TextView)findViewById(R.id.textView2);
                TextView tv=(TextView)findViewById(R.id.turnView);
                if (gameOver) {
                    if (overall_score >= winning_score) {
                        ys.setText("Your Score: " + overall_score);
                        tv.setText("YOU WIN!");
                    }
                    else {
                        ys.setText("Computer's Score: " + computerScore);
                        tv.setText("COMPUTER WINS!");

                    }
                } else {
                    ys.setText("Your Score: " + overall_score);
                    cs.setText("Computer Score: " + computerScore);
                }
    }
    private void updateRoundScore(int score) {
        TextView turnScoreView = (TextView)findViewById(R.id.textView);
        turnScoreView.setText("Turn score: " + score);
    }

    public void reset() {
        overall_score = 0;
        computerScore = 0;

        turn_score = 0;
        updateRoundScore(0);

        userTurn = true;
        gameOver = false;

        TextView ys = (TextView)findViewById(R.id.textView);
        TextView cs = (TextView)findViewById(R.id.textView2);

        ys.setText("Your Score: 0");
        cs.setText("Computer Score: 0");


        TextView turnView = (TextView)findViewById(R.id.turnView);
        turnView.setText("Your turn");

        ImageView dice = (ImageView)findViewById(R.id.imageView);
        dice.setImageDrawable(getResources().getDrawable(R.drawable.dice3));

        Button rollButton = (Button) findViewById(R.id.button);
        rollButton.setEnabled(true);
        Button holdButton = (Button) findViewById(R.id.button2);
        holdButton.setEnabled(true);

    }


}



