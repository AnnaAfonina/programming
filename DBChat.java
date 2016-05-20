

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBChat {
    
    public static Connection connection;
    public static String username;
    public static String remoteUsername;    
    

    private  void addStr(String username, String str){
            try {
                statement.executeQuery("INSERT INTO message(name, message) VALUES ( '" + username + "', '"+  str + "')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    private void loadHistory(){
            try {
                boolean execute = statement.execute("select * from message");
                if (execute) {
                    ResultSet resultSet = statement.getResultSet();
                    while (resultSet.next()) {
                        writer.println(resultSet.getString(2) + ": " + resultSet.getString(3));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public static synchronized void sendMessage(Socket socket, String message)throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write(message, 0, message.length());
        bw.newLine();
        bw.flush();
    }
    
    public static void main(String[] args) {
       
        try {
            
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "root");
			loadHistory();
            Socket up1 = null;
            Socket down1 = null;

            if (!args[0].equals("0")) {
                ServerSocket ss = new ServerSocket(Integer.parseInt(args[0]));
                up1 = ss.accept();
                username = "ServerUser";
                remoteUsername = "ClientUser";
            }

            if (!args[1].equals("0")) {
                down1 = new Socket("localhost", Integer.parseInt(args[1]));
                username = "ClientUser";
                remoteUsername = "ServerUser";
            }

            final Socket up = up1;
            final Socket down = down1;

            if (up != null) {
                Thread listenUp = new Thread() {
                    public void run() {
                        try {
                            while (true) {
                                BufferedReader br = new BufferedReader(new InputStreamReader(up.getInputStream()));
                                String strUp = br.readLine();
                                System.out.println("["+ remoteUsername +"]: "+ strUp);

                                if (down != null)
                                    sendMessage(down, strUp);
                            }
                        } catch (Exception e) {
                            System.out.println("1" + e);
                        }
                    }
                };
                listenUp.start();
            }


            if (down != null) {
                Thread listenDown = new Thread() {
                    public void run() {
                        try {
                            while (true) {
                                BufferedReader br = new BufferedReader(new InputStreamReader(down.getInputStream()));
                                String strDown = br.readLine();
                                System.out.println("["+ remoteUsername +"]: "+strDown);

                                if (up != null)
                                    sendMessage(up, strDown);
                            }
                        } catch (Exception e) {
                            System.out.println("2" + e);
                        }
                    }
                };
                listenDown.start();
            }


            Thread send = new Thread() {
                public void run() {
                    try {
                        while (true) {
                            BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
                            String str = br2.readLine();

                            if (up != null)
                                sendMessage(up, str);

                            if (down != null)
                                sendMessage(down, str);
                            
                            addStr(username, str);
                        }
                    } catch (Exception e) {
                        System.out.println("3" + e);
                    }
                }
            };
            send.start();


        } catch (Exception e) {
            System.out.println("4" + e);
        }
    }




}
