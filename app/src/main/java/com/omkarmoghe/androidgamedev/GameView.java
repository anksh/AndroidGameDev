package com.omkarmoghe.androidgamedev;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

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


    private boolean firstRun = true;
    /*
    Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.
    int width = size.x;
    int height = size.y;
    */
    final WindowManager w = (WindowManager)((Activity)getContext()).getSystemService(Context.WINDOW_SERVICE);
    Display display = w.getDefaultDisplay();
    final DisplayMetrics metrics = new DisplayMetrics();



    private float height = metrics.heightPixels;
    private float width = metrics.widthPixels;
    private RectFP rectFP = new RectFP(500,700,200,300);
    private Paint text = new Paint(1);
    private Paint background = new Paint();
    /*
    display.getMetrics(metrics);
    height = metrics.heightPixels;
    width = metrics.widthPixels;
    rectFP = new RectFP(height / 2 - 100, width / 2 - 100, height / 2 + 100, width / 2 + 100);
    */

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

                }
        });

        makePaints(); // Adds our paints to the arraylist.
        rectFP.setPaint(paints.get(random.nextInt(4)));
    }


    @Override
    public void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.BLACK); // Black background
        background.setColor(Color.CYAN);
        background.setStyle(Paint.Style.FILL);
        canvas.drawPaint(background);

        text.setColor(Color.BLACK);
        text.setTextSize(55);
        height = metrics.heightPixels;
        width = metrics.widthPixels;

        //canAdd = false;
        //for (RectFP r : circles) {
        if (!firstRun) {
            rectFP.set_right((float) (rectFP.get_right() - (9 + (float)count / 20)));
            rectFP.set_left((float) (rectFP.get_left() + (9 + (float)count / 20)));
            rectFP.set_bottom((float) (rectFP.get_bottom() - (9 + (float)count / 20)));
            rectFP.set_top((float) (rectFP.get_top() + (9 + (float)count / 20)));

        } else {
            display.getMetrics(metrics);
            height = metrics.heightPixels;
            width = metrics.widthPixels;
            rectFP.set_right(width / 2 + 275);
            rectFP.set_left(width / 2 - 275);
            rectFP.set_top(height / 2 - 275);
            rectFP.set_bottom(height / 2 + 275);
        }
        canvas.drawOval(rectFP, rectFP.getPaint());
            //}
        if(rectFP.get_right()-rectFP.get_left()<10){
            rectFP.set_left(0);
            rectFP.set_right(0);
            gameLoopThread.setRunning(false);
            Bundle b = new Bundle();
            b.putString("count",Integer.toString(count));
            Intent i = new Intent();
            i.putExtras(b);
            ((Activity)this.getContext()).setResult(Activity.RESULT_OK, i);
            ((Activity)this.getContext()).finish();
        }
        //canAdd = true;
        float txtWidth = text.measureText(Integer.toString(count));
        canvas.drawText(/*"Circles clicked: " +*/ Integer.toString(count), width / 2 /*- txtWidth*/, height / 10, text);
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (x < rectFP.get_right() && x > rectFP.get_left()
                && y > rectFP.get_top() && y < rectFP.get_bottom()/*canAdd*/) {

            firstRun = false;
            randomX = random.nextInt((int)(width - 100)) + 50;
            randomY = random.nextInt((int)(height - 100)) + 50;
            System.out.println(randomX);
            System.out.println(randomY);

            do{
                rectFP = new RectFP(randomX - 275, randomY - 275, randomX + 275, randomY + 275);
            }while(rectFP.get_left()>width || rectFP.get_top()>height);
            rectFP.setPaint(paints.get(random.nextInt(4)));
            //circles.clear();
            //circles.add(rectFP);
            count++;
        }

        return super.onTouchEvent(event);
    }

    private void makePaints() {
        paints = new ArrayList<Paint>();
        float blurRadius = 25;
        float dx = 10;
        float dy = 10;
        int color = Color.GRAY;
        Paint red = new Paint(1);
        red.setColor(Color.RED);
        red.setShadowLayer(blurRadius, dx, dy, color);
        Paint blue = new Paint(1);
        blue.setColor(Color.BLUE);
        blue.setShadowLayer(blurRadius, dx, dy, color);
        Paint green = new Paint(1);
        green.setColor(Color.GREEN);
        green.setShadowLayer(blurRadius, dx, dy, color);
        Paint yellow = new Paint(1);
        yellow.setColor(Color.YELLOW);
        yellow.setShadowLayer(blurRadius, dx, dy, color);

        paints.add(red);
        paints.add(blue);
        paints.add(green);
        paints.add(yellow);
    }
}
