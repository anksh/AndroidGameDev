package com.omkarmoghe.androidgamedev;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Omkar Moghe on 2/4/2015.
 *
 * Class where all the drawing and game logic goes.
 */
public class GameView extends SurfaceView {
    private int count = 0;
    private SurfaceHolder  holder;
    private GameLoopThread gameLoopThread;

    private ArrayList<Paint> paints; // list of paints.
    private ArrayList<RectFP> circles = new ArrayList<RectFP>(); // list of circles.

    private boolean canAdd = true;

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @SuppressLint("WrongCall")
            @Override
            public void surfaceCreated (SurfaceHolder holder) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged (SurfaceHolder holder, int format, int width, int height) {
            }
        });

        makePaints(); // Adds our paints to the arraylist.
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE); // Black background
        TextView tv = new TextView(getContext());
        tv.setText("Circles Clicked: " + Integer.toString(count));
        tv.setVisibility(VISIBLE);
            canAdd = false;
            for (RectFP r : circles) {
                r.set_right(r.get_right() - 1);
                r.set_bottom(r.get_bottom() - 1);

                canvas.drawOval(r, r.getPaint());
            }
            canAdd = true;
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (canAdd) {
            Random random = new Random();

            float randomX = random.nextInt(600);
            float randomY = random.nextInt(1000);

            RectFP rectFP = new RectFP(randomX, randomY, randomX + 50, randomY + 50);
            rectFP.setPaint(paints.get(random.nextInt(4)));
            circles.clear();
            circles.add(rectFP);
            count++;
        }

        return super.onTouchEvent(event);
    }

    private void makePaints() {
        paints = new ArrayList<Paint>();

        Paint red = new Paint();
        red.setColor(Color.RED);
        Paint blue = new Paint();
        blue.setColor(Color.BLUE);
        Paint green = new Paint();
        green.setColor(Color.GREEN);
        Paint yellow = new Paint();
        yellow.setColor(Color.YELLOW);

        paints.add(red);
        paints.add(blue);
        paints.add(green);
        paints.add(yellow);
    }
}
