package com.omkarmoghe.androidgamedev;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private Random random = new Random();
    private float randomX = random.nextInt(600);
    private float randomY = random.nextInt(1000);

    private RectFP rectFP = new RectFP(randomX, randomY, randomX + 500, randomY + 500);
    //private ArrayList<RectFP> circles = new ArrayList<RectFP>(); // list of circles.

    //private boolean canAdd = true;

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
                if(rectFP.get_right()-rectFP.get_left()<10) {

                    }
                }
        });

        makePaints(); // Adds our paints to the arraylist.
        rectFP.setPaint(paints.get(random.nextInt(4)));
    }


    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK); // Black background
        //canAdd = false;
        //for (RectFP r : circles) {
            rectFP.set_right((float) (rectFP.get_right() - (count+1)));
            rectFP.set_left((float) (rectFP.get_left() + (count+1)));
            rectFP.set_bottom((float) (rectFP.get_bottom() - (count+1)));
            rectFP.set_top((float) (rectFP.get_top() + (count+1)));
            canvas.drawOval(rectFP, rectFP.getPaint());
            //}
        if(rectFP.get_right()-rectFP.get_left()<10){
            rectFP.set_left(0);
            rectFP.set_right(0);
            gameLoopThread.setRunning(false);
            //setVisibility(this.INVISIBLE);
           ((Activity)this.getContext()).finish();

            //Intent intent = new Intent(this, MainActivity.class);
            //rectFP.set_top(rectFP.get_bottom());
        }
        //canAdd = true;
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (x < rectFP.get_right() && x > rectFP.get_left()
                && y > rectFP.get_top() && y < rectFP.get_bottom()/*canAdd*/) {

           // Random random = new Random();
            randomX = random.nextInt(500);
            randomY = random.nextInt(900);

            do{
                rectFP = new RectFP(randomX, randomY, randomX + 500, randomY + 500);
            }while(rectFP.get_left()>600 || rectFP.get_top()>900);
            rectFP.setPaint(paints.get(random.nextInt(4)));
            //circles.clear();
            //circles.add(rectFP);
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
