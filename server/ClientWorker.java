package cloud.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.lang.*;
import cloud.server.Protocol;

public class ClientWorker implements Runnable{
    final static String CRLF = "\r\n";
    private Socket socket;
    ClientWorker(Socket socket){
        this.socket = socket;
    }
    public void run(){
        process();
    }

    private void process(){
        Protocol protocol = new Protocol();
        StringBuffer requestStr = new StringBuffer();
        StringBuffer responseStr = new StringBuffer();
        String temp;
        try(BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);){
            while((temp=in.readLine()) != null){
                if(temp.isEmpty()){
                    out.println(protocol.process(requestStr.toString()));
                    System.out.println(requestStr.toString());
                    if(protocol.isClosed())
                        break;
                    requestStr.setLength(0);
                }else{
                    requestStr.append(temp+CRLF);
                }
            }
            System.out.println("closed");
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}