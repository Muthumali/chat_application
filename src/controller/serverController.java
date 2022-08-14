package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class serverController{
    public AnchorPane serverContext;
    public JFXTextArea txtAreaPane;


    Socket accept=null;
    private ServerSocket serverSocket;

 /* public serverController(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }*/

    public void startServer(){
        try {
            while (!serverSocket.isClosed()){
                Socket socket=serverSocket.accept();
                System.out.println("a new client Connected");
               ClientHandler clientHandler=new ClientHandler(socket);
                Thread thread=new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket= new ServerSocket(5000);
                System.out.println("Server started!");
                txtAreaPane.setText("Server started!");
                accept= serverSocket.accept();
                System.out.println("Client Connected!");
               /* ServerSocket serverSocket=new ServerSocket(5000);
                server server=new server(serverSocket);
                server.startServer();*/
                startServer();
                while (true) {
                    InputStreamReader inputStreamReader =
                            new InputStreamReader(accept.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String record = bufferedReader.readLine();
                    System.out.println(record);
                    txtAreaPane.appendText(record);
                }
                /* while(true){if(!message.equals("exit"))}*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


}
