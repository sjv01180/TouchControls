package com.example.touchcontrols;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

public class LetterTile extends android.support.v7.widget.AppCompatTextView {
    /**
     * Makes a new LetterTile object
     * @param context
     * @param c - what character should appear on the tile
     * @param tileSize - length and width of tile in pixels
     * @param backgroundColor - color of tile
     */
    public LetterTile(Context context, char c, int tileSize, int backgroundColor)
    {
        super(context);
        setText(Character.toString(c));
        setWidth(tileSize);
        setHeight(tileSize);
        setBackgroundColor(backgroundColor);
        setGravity(Gravity.CENTER);
        setTextSize(tileSize / 5.0f);
    }
}
