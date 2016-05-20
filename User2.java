package com.afonina;

import java.io.*;
import java.net.Socket;

public class User2 {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 3030);
            Thread t = new Thread(){
                public void run(){
                    try {

                        while (true) {

                            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                            System.out.println(br.readLine());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            };
            t.start();
            while (true) {

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String str = br.readLine();
                bw.write(str, 0, str.length());
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}