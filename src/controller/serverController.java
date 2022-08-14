package controller;
import com.jfoenix.controls.JFXTextArea;
import javafx.scene.layout.AnchorPane;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class serverController{
    public AnchorPane serverContext;
    public JFXTextArea txtAreaPane;


    Socket accept=null;

    public void initialize() {
        new Thread(() -> {
            try {

                ServerSocket serverSocket= new ServerSocket(5000);
                System.out.println("Server started!");
                txtAreaPane.appendText("server Started!"+"\n");
                while (!serverSocket.isClosed()) {
                accept= serverSocket.accept();
                System.out.println("Client Connected!");
                txtAreaPane.appendText("Client Connected!"+ "\n");


                        InputStreamReader inputStreamReader =
                                new InputStreamReader(accept.getInputStream());
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String record = bufferedReader.readLine();
                        System.out.println(record);
                        txtAreaPane.appendText(record+"\n");
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


}
