package com.example.touchcontrols;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import com.example.shuffle.WordList;


public class MainActivity extends AppCompatActivity {
    private static int MAX_WORD_LENGTH = 5;
    private static int SCORE = 0;
    private WordList list;
    private String word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView score = findViewById(R.id.score);
        score.setText("Score: " + SCORE);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        try{
            list = WordList.load(this);
            list.contains("words"); //if the list contains this word.
        } catch(IOException e) {
            Toast.makeText(this, "Error!", Toast.LENGTH_LONG).show();
        }

    }

    public void placeTile(View view) {

        RelativeLayout layout = findViewById(R.id.tile_pane);
        int tileSize = 150;
        int color = ContextCompat.getColor(this, R.color.tile_word);
        int colorAnswer = ContextCompat.getColor(this, R.color.tile_answer);
        String word = list.randomWordOfLength(5);
        int counter = 0;
        for(char c : word.toCharArray()) {

            LetterTile tile = new LetterTile(this, c, tileSize, color);
            tile.setX(0);
            tile.setY(500);

            LetterTile answerPlacement = new LetterTile(this, c, tileSize, colorAnswer);
            answerPlacement.setX(0);
            answerPlacement.setY(700);

            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            int screenWidthPx = layout.getWidth();
            int screenHeightPx = layout.getHeight();

            tile.setX(screenWidthPx / 10 - tileSize / 2 + counter * 200);
            tile.setY(screenHeightPx / 2 - tileSize / 2);

            answerPlacement.setX(screenWidthPx / 10 - tileSize / 2 + counter * 200);
            answerPlacement.setY(screenHeightPx / 2 - tileSize / 2);

            layout.addView(tile);

            tile.setOnTouchListener(new View.OnTouchListener() {
                float dx;
                float dy;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    v.bringToFront();
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //record the offset between where the touch occured and the reference point of the view
                            dx = v.getX() - event.getRawX();
                            dy = v.getY() - event.getRawY();
                            return true;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_MOVE:
                            //move the tile around with the gesture
                            float newX = event.getRawX() + dx;
                            float newY = event.getRawY() + dy;
                            v.setX(newX);
                            v.setY(newY);
                            return true;
                        default:
                            return true;
                    }
                }
            });
            counter++;
        }
    }
}
