/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author faizura
 */
public class client2 {
    public static void main(String[] args) throws IOException{
        Socket s1 = null;
        String line = null;
        DataInputStream br = null;
        DataInputStream is = null;
        PrintWriter os = null;
        
        try {
            s1 = new Socket("localhost",2000);
            br = new DataInputStream(System.in);
            is = new DataInputStream(s1.getInputStream());
            os = new PrintWriter(s1.getOutputStream());
        } catch (Exception e) {
            System.out.println("IO Exception");
        }
        System.out.println("Enter Data to Echo Server(Enter QUIT to END");
        String response = null;
        
        try {
            line = br.readLine();
            while(line.compareTo("Quit")!=0){
                os.println(line);
                os.flush();
                response = is.readLine();
                System.out.println("Server Respone :"+response);
                line = br.readLine();
            }
            is.close();
            os.close();
            br.close();
            s1.close();
            System.out.println("Connection Closed...");
        } catch (Exception e) {
            System.out.println("Socket Read ERROR");
        }
    }
 
}
