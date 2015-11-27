package com.example.hypo.neurobotballcontroller;

import android.content.Context;

import android.graphics.Canvas;

import android.graphics.drawable.BitmapDrawable;

import android.os.Handler;

import android.util.AttributeSet;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import org.json.JSONObject;
/**
 * Created by hypo on 02.11.15.
 */
public class AnimatedView extends ImageView {
    private Context mContext;

    int x = -1;

    int y = -1;

    private int xVelocity = 10;

    private int yVelocity = 5;

    private Handler h;

    private final int FRAME_RATE = 30;


    public AnimatedView(Context context, AttributeSet attrs)  {

        super(context, attrs);

        mContext = context;

        h = new Handler();

    }

    private Runnable r = new Runnable() {

        @Override

        public void run() {

            invalidate();

        }

    };

    protected void onDraw(Canvas c) {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                //JSONObject packet = new JSONObject();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d("  main ", " value of x  down" + event.getX());
                    x = (int)event.getX();
                    y = (int)event.getY();
                    //packet.put <- wedlug protokolu rafala kozika
                    //run Client Thread new Client(packet).start();
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.d("  main ", " value of x  move " + event.getX());
                    x = (int)event.getX();
                    y = (int)event.getY();
                    //packet.put <- wedlug protokolu rafala kozika
                    //run Client Thread
                    return true;
                }
                return false;
            }
        });

        BitmapDrawable ball = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.ball);
/*
        if (x<0 && y <0) {

            x = this.getWidth()/2;

            y = this.getHeight()/2;

        } else {

            x += xVelocity;

            y += yVelocity;

            if ((x > this.getWidth() - ball.getBitmap().getWidth()) || (x < 0)) {

                xVelocity = xVelocity*-1;

            }

            if ((y > this.getHeight() - ball.getBitmap().getHeight()) || (y < 0)) {

                yVelocity = yVelocity*-1;

            }

        }
*/
        c.drawBitmap(ball.getBitmap(), x-100, y-100, null);

        h.postDelayed(r, FRAME_RATE);

    }


}
