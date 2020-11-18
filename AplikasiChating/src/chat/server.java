/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author faizura
 */
public class server {
    public static void main(String[] args) throws IOException {
        Socket s=null;
        ServerSocket ss2 = null;
        System.out.println("Server Listening...");
        ss2=new ServerSocket(2000);
        
        while (true) {            
            try {
                s=ss2.accept();
                System.out.println("Connection Established");
                ServerThread st = new ServerThread(s);
                st.start();
            } catch (IOException e) {
                System.err.println("Connection error");
            }
        }
    }
} 

class ServerThread extends Thread{
    String line = null;
    DataInputStream is = null;
    DataInputStream br = null;
    PrintWriter os = null;
    Socket sl = null;
    public ServerThread(Socket s){
        sl = s;
    }
    
    @Override
    public void run(){
        try {
            is = new DataInputStream(sl.getInputStream());
            os = new PrintWriter(sl.getOutputStream());
            line = is.readLine();
            while(line.compareTo("QUIT")!=0){
                os.println(line);
                os.flush();
                System.out.println("Response of client : "+line);
                line = is.readLine();
            }
            is.close();
            os.close();
            sl.close(); 
        } catch (IOException e) {
        }
    }
}