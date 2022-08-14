package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.*;
import java.net.Socket;

public class clientController {
    public TextArea txtClientPane;
    public JFXTextField txtClientMessage;
    public AnchorPane clientContext;
    public Label txtClientName;

    Socket socket=null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private String username = "";
    public void initialize() throws IOException {
    new Thread(() -> {
        try {
                socket = new Socket("localhost", 5000);
                InputStreamReader inputStreamReader =
                        new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String record = bufferedReader.readLine();
                System.out.println(record);
                txtClientPane.appendText(record);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }).start();

    }


    public void addImageOnAction(MouseEvent mouseEvent) {

    }

    public void sendEmojiOnAction(MouseEvent mouseEvent) {

    }

    public void sendOnMsgOnaction(MouseEvent mouseEvent) throws IOException {
        PrintWriter printWriter= new PrintWriter(socket.getOutputStream());
        printWriter.println(txtClientMessage.getText());
        printWriter.flush();
        txtClientMessage.clear();
    }
}
