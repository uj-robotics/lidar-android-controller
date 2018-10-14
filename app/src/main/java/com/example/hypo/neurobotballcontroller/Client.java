package com.example.hypo.neurobotballcontroller;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by hypo on 25.11.15.
 */

public class Client implements Runnable {
    JSONObject packet;
    public static final String SERVER_IP = "192.168.0.109";
    public static final int SERVER_PORT = 5678;
    private String mServerMessage;
    // while this is true, the server will continue running
    private boolean mRun = false;
    private PrintWriter mBufferOut;
    private BufferedReader mBufferIn;
    Thread backgroundThread;

    public void sendMessage(String message) {
        if (mBufferOut != null && !mBufferOut.checkError()) {
            mBufferOut.println(message);
            mBufferOut.flush();
        }
    }

    public void stopClient() {
        Log.i("Debug", "stopClient");
        mRun = false;

        if (mBufferOut != null) {
            mBufferOut.flush();
            mBufferOut.close();
        }
        mBufferIn = null;
        mBufferOut = null;
        mServerMessage = null;
    }

    public Client(JSONObject packet) {
        this.packet = packet;
    }

    @Override
    public void run() {
        mRun = true;
        
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

            Log.e("TCP Client", "C: Connecting...");

            //create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, SERVER_PORT);
            try {
                Log.i("Debug", "inside try catch");
                mBufferOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                Log.e("TCP", "GOING TO SEND: " + packet.toString());
                sendMessage("!" + packet.toString().length() + "!" + packet.toString());
                Thread.sleep(500);

            } catch (Exception e) {

                Log.e("TCP", "S: Error", e);

            } finally {
                socket.close();
            }

        } catch (Exception e) {

            Log.e("TCP", "C: Error", e);

        }

    }


}
