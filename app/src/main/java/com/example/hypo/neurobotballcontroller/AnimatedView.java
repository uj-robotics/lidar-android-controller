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

import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by hypo on 02.11.15.
 */
public class AnimatedView extends ImageView {
    private Context mContext;

    int x = -1;
    int y = -1;

    int referenceX = 0, referenceY = -400;
    int phoneX = 480, phoneY = 800;
    int originX = phoneX / 2, originY = phoneY / 2;
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

    double norm(double x, double y) {
        if (x == 0 && y == 0) return 0;
        return Math.sqrt(x * x + y * y);
    }

    double dot(double x1, double y1, double x2, double y2) {
        return x1 * x2 + y1 * y2;

    }

    double angle(double x1, double y1, double x2, double y2) {
        return Math.acos(dot(x1, y1, x2, y2) / ((norm(x1, y1) * norm(x2, y2))));
    }


    protected void onDraw(Canvas c) {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                /*
                    JSON:
                    {'left_motor': int; 'right_motor': int;}
                    left_motor - predkosc lewego silnika, od 255 do -255; 0 stop
                    right_motor - predkosc prawego silnika, od 255 do -255; 0 stop
                */
                JSONObject packet = new JSONObject();
                int left_motor = 0, right_motor = 0;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d("TURN", " value of x,y  down" + event.getX() + " " + event.getY());
                    x = (int)event.getX();
                    y = (int)event.getY();
                    left_motor = 0;
                    right_motor = 0;
                    double Angle = angle(referenceX, referenceY, event.getX() - originX, event.getY() - originY);
                    Log.d("TURN", "" + Angle * 180 / Math.PI);

                    if (Angle <= angle(referenceX, referenceY, referenceX + 160, referenceY)) {
                        //naprzod
                        Log.d("TURN", "FORWARD");
                        left_motor = 200;
                        right_motor = 200;

                    } else if (Angle > angle(referenceX, referenceY, -160, 400)) {
                        //do tylu
                        Log.d("TURN", "BACKWARDS");
                        left_motor = -200;
                        right_motor = -200;
                    } else if (Angle < angle(-240, 240, referenceX, referenceY) && Angle > angle(-240, -240, referenceX, referenceY)) {
                        //lewo-prawo
                        Log.d("TURN", "OBROT W");
                        if (x >= originX) {
                            //right
                            Log.d("TURN", "PRAWO");

                            left_motor = 255;
                            right_motor = -255;

                        } else {
                            //left
                            Log.d("TURN", "LEWO");
                            left_motor = -255;
                            right_motor = 255;
                        }
                    } else {
                        //jazda z zakretem
                        Log.d("TURN", "ZAKRET");
                        if (y <= phoneY / 2) {
                            //F
                            if (x >= phoneX / 2) {
                                //R
                                left_motor = 255;
                                right_motor = 127;
                            } else {
                                left_motor = 127;
                                right_motor = 255;
                            }
                        } else {
                            //B
                            if (x >= phoneX / 2) {
                                left_motor = -255;
                                right_motor = -127;


                            } else {
                                left_motor = -127;
                                right_motor = -255;
                            }
                        }
                    }
                    try {
                        packet.put("left_motor", left_motor);
                        packet.put("right_motor", right_motor);
                        Client client = new Client(packet);
                        client.run();// excutor.execute(client)
                        new Thread(client).start();
                    } catch (JSONException e) {
                    }

                    //packet.put <- wedlug protokolu rafala kozika
                    //run Client Thread new Client(packet).start();

                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.d("  main ", " value of x,y  down" + event.getX() + " " + event.getY());
                    x = (int)event.getX();
                    y = (int)event.getY();
                    //packet.put <- wedlug protokolu rafala kozika
                    //run Client Thread
                    double Angle = angle(referenceX, referenceY, event.getX() - originX, event.getY() - originY);

                    if (Angle <= angle(referenceX, referenceY, referenceX + 160, referenceY)) {
                        //naprzod
                        Log.d("TURN", "FORWARD");
                        left_motor = 255;
                        right_motor = 255;

                    } else if (Angle > angle(referenceX, referenceY, -160, 400)) {
                        //do tylu
                        Log.d("TURN", "BACKWARDS");
                        left_motor = -255;
                        right_motor = -255;
                    } else if (Angle < angle(-240, 240, referenceX, referenceY) && Angle > angle(-240, -240, referenceX, referenceY)) {
                        //lewo-prawo
                        Log.d("TURN", "OBROT W");
                        if (x >= originX) {
                            //right
                            Log.d("TURN", "PRAWO");

                            left_motor = 255;
                            right_motor = -255;

                        } else {
                            //left
                            Log.d("TURN", "LEWO");
                            left_motor = -255;
                            right_motor = 255;
                        }
                    } else {
                        //jazda z zakretem
                        Log.d("TURN", "ZAKRET");
                        if (y <= phoneY / 2) {
                            //F
                            if (x >= phoneX / 2) {
                                //R
                                left_motor = 255;
                                right_motor = 127;
                            } else {
                                left_motor = 127;
                                right_motor = 255;
                            }
                        } else {
                            //B
                            if (x >= phoneX / 2) {
                                left_motor = -255;
                                right_motor = -127;


                            } else {
                                left_motor = -127;
                                right_motor = -255;
                            }
                        }
                    }
                    try {
                        packet.put("left_motor", left_motor);
                        packet.put("right_motor", right_motor);
                        Client client = new Client(packet);
                        client.run();// excutor.execute(client)
                        new Thread(client).start();
                    } catch (JSONException e) {
                    }

                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.d("  main ", "STOPPED");
                    left_motor = 0;
                    right_motor = 0;
                    try {
                        packet.put("left_motor", left_motor);
                        packet.put("right_motor", right_motor);
                        Client client = new Client(packet);
                        new Thread(client).start();
                    } catch (JSONException e) {
                    }

                    return true;
                }
                return false;
            }
        });

        BitmapDrawable ball = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.ball_64x64);

        c.drawBitmap(ball.getBitmap(), x - 50, y - 50, null);

        h.postDelayed(r, FRAME_RATE);

    }


}
