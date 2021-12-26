package com.example.youwillnevergetamacbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView timeText,scoreText,startCounter;
    ImageView moneyImage;
    Button startButton;

    int score;
    int screenX,screenY;

    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeText = findViewById(R.id.timeText);
        scoreText = findViewById(R.id.scoreText);
        moneyImage = findViewById(R.id.moneyImage);
        startCounter = findViewById(R.id.startingCounter);
        startButton = findViewById(R.id.startButton);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);
        screenX = size.x;
        screenY = size.y;

        score = 0;

        timeText.setVisibility(View.INVISIBLE);
        scoreText.setVisibility(View.INVISIBLE);
        moneyImage.setVisibility(View.INVISIBLE);

        imageRandomizer();

    }

    public void increaseScore(View view){
        score+=11;
        scoreText.setText("Your Money : "+score);
    }

    public void StartGame(View view){

        new CountDownTimer(3000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                startCounter.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

                startCounter.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.INVISIBLE);

                timeText.setVisibility(View.VISIBLE);
                scoreText.setVisibility(View.VISIBLE);
                moneyImage.setVisibility(View.VISIBLE);


                new CountDownTimer(10000,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {
                        timeText.setText("Time: "+millisUntilFinished/1000);
                    }

                    @Override
                    public void onFinish() {

                        handler.removeCallbacks(runnable);

                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                        alert.setTitle("Time is Up");
                        alert.setMessage("Want to try again?");

                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);

                            }
                        });

                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Toast.makeText(MainActivity.this,"You will never get a macbook looser",Toast.LENGTH_LONG).show();

                            }
                        });

                        alert.show();


                    }
                }.start();


            }
        }.start();



    }

    public void imageRandomizer(){

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Random random = new Random();//Randomizing images position
                float randomX = random.nextInt(screenX-moneyImage.getMeasuredWidth());
                float randomY = random.nextInt( ((screenY-100)-moneyImage.getMeasuredHeight()))+100; // added 10 and subtracted 300 because of labels

                moneyImage.setX(randomX);
                moneyImage.setY(randomY);
                handler.postDelayed(runnable,500);
            }

        };
            handler.post(runnable);


    }



}