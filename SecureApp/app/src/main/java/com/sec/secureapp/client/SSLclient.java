package com.sec.secureapp.client;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.sec.secureapp.general.T;

import org.json.JSONArray;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Arrays;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class SSLclient extends Thread {

    private boolean clientRunning;
    private Context context;
    private Socket connection = null; // socket to make the connection between Client-Server
    private ObjectInputStream in = null; //stream for message that come up
    private ObjectOutputStream out = null; //stream for messages that go to client
    private myHandler handler; //handler is used to handle the messages are coming


    public SSLclient(Context context) {
        this.clientRunning = true;
        this.context = context;
        handler = new myHandler();
        handler.start();
    }

    @Override
    public void run() {
        try {
            connectToServer();
            setupStreams();
            whileChatting();
        } catch (EOFException eofe) {
            System.out.println("TERMINATED!!!!!");
            eofe.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("2 TERMINATED!!!!!");
            ioe.printStackTrace();
        } finally {
            closeCrap();
        }
    }

    public void connectToServer() throws IOException {
        System.out.println("Attempting Connection");

        try {
            System.out.println("You are conected");
            connection = new Socket(InetAddress.getByName("zafeiratosv.ddns.net"), 54321);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setupStreams() throws IOException {
        out = new ObjectOutputStream(connection.getOutputStream());
        in = new ObjectInputStream(connection.getInputStream());
        System.out.println("Client: Streams are now good to go");
    }

    public void whileChatting() throws IOException {
        Object message;
        while (this.clientRunning) {
            try {
                message = in.readObject();
                Message message1 = new Message();
                message1.obj = message;
                handler.getHandler().sendMessage(message1);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                System.out.println("Class not found exception");
            }
        }
    }

    public void closeCrap() {
        this.clientRunning = false;
        System.out.println("Closing crap down");
        try {
            if (out != null) {
                System.out.println("close out");
                out.close();
            }
            if (in != null) {
                System.out.println("close in");
                in.close();
            }
            if (connection != null) {
                System.out.println("close connection");
                connection.close();
            }
        } catch (IOException ex) {
            System.out.println("closeCrap exception");
        }
    }

    public void sendMessage(Object object) {
        try {
            System.out.println("Message sent: " + object);
            int counter = 0;
            for (; ; ) {
                if (out != null) {
                    out.writeObject(object);
                    out.flush();
                    break;
                } else if (counter == 30) {
                    System.out.println("Timeout while trying to send message. Output stream probably is not open.");
                    break;
                }
                counter++;
                T.SLEEP(100);
            }
        } catch (Exception ex) {
            System.out.println("something went wrong with the host");
            ex.printStackTrace();
        }
    }

    private class myHandler extends Thread {

        private Handler handler;

        @Override
        public void run() {
            Looper.prepare();

            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Object obj = msg.obj;
                    System.out.println("Message came: " + obj);
                    if (obj instanceof String) {
                        String s = (String) obj;
                        if (s.startsWith(T.SIGN_UP_CONFIRM)) {
                            T.SIGN_UP_MESSAGE = s.substring(2);
                        } else if (s.startsWith(T.PRIVATE_KEY)) {
                            T.SIGN_UP_MESSAGE = s.substring(2);
                        } else if (s.startsWith(T.LOG_IN_CONFIRM)) {
                            T.LOG_IN_MESSAGE = s.substring(2);
                        } else if (s.startsWith(T.OTP_CONFIRM)) {
                            T.OTP_MESSAGE = s.substring(2);
                        } else if (s.startsWith(T.MAIN_CONFIRM)) {
                            T.MAIN_MESSAGE = s.substring(2);
                        }
                        else if (s.startsWith(T.AUCTIONS_LIST)) {
                            T.AUCTIONS_MESSAGE = s.substring(2);
                        }
                        else if (s.startsWith(T.AUCTIONS_NULL)) {
                            T.AUCTIONS_MESSAGE = "null";
                        }
                    }

                }
            };

            Looper.loop();
        }

        public Handler getHandler() {
            return this.handler;
        }

    }

}
