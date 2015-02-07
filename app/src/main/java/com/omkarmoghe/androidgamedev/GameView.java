package com.omkarmoghe.androidgamedev;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Omkar Moghe on 2/4/2015.
 *
 * Class where all the drawing and game logic goes.
 */
public class GameView extends SurfaceView {

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
        canvas.drawColor(Color.BLACK); // Black background

            canAdd = false;
            for (RectFP r : circles) {
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

            float randomX = random.nextInt(200);
            float randomY = random.nextInt(200);

            RectFP rectFP = new RectFP(randomX, randomY + 50, randomX + 50, randomY);
            rectFP.setPaint(paints.get(random.nextInt(4)));
            circles.add(rectFP);
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
