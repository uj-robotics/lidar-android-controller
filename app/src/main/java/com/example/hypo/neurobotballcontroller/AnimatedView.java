package com.example.hypo.neurobotballcontroller;

import android.content.Context;

import android.graphics.Canvas;

import android.graphics.drawable.BitmapDrawable;

import android.os.Handler;

import android.util.AttributeSet;

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
                    x = (int)event.getX();
                    y = (int)event.getY();
                    //packet.put <- wedlug protokolu rafala kozika
                    //run Client Thread new Client(packet).start();
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_MOVE) {
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

        c.drawBitmap(ball.getBitmap(), x-100, y-100, null);

        h.postDelayed(r, FRAME_RATE);

    }


}
