
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyClass
{
   // private static List<Socket> connections = Collections.synchronizedList(new ArrayList<Socket>());

    static ServerSocket ss;
    static Socket s;

    static void readMes()
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            System.out.println(br.readLine());
        }
        catch (Exception e){System.out.println("error");}
    }

    static void writeMes(String message)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter((s.getOutputStream())));
            bw.write(message, 0, message.length());
            bw.newLine();
            bw.flush();
        }
        catch (Exception e){}
    }

    public static void main(String[] args)
    {
        try
        {
            ss = new ServerSocket(1022);
            //while(true){
                Socket s = ss.accept();
                //connections.add(s);
            //}
            //s = ss.accept();
        }
        catch (Exception e){}

        Thread t = new Thread() {
            public void run() {
                System.out.println("i'm heeereee");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                readMes();
                try
                {
                    while (true) {
                        writeMes(br.readLine());
                       // readMes();
                    }
                } catch (Exception e){}
            }
        };
        Thread t2 = new Thread(){
            public void run(){
                System.out.println("tututu");
                while (true){
                    readMes();}
            }
        };
        t.start();
        t2.start();
    }
}