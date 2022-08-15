package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import static controller.loginFormController.username;
public class clientController extends Thread{
    public TextArea txtClientPane;
    public JFXTextField txtClientMessage;
    public AnchorPane clientContext;
    public  Label txtClientName;
    public VBox vBoxPaneClinet;
    Socket socket=null;
    PrintWriter printWriter;

    private BufferedReader bufferedReader;

    public void initialize(){
        connectSocket();
        txtClientName.setText(username);
    }

    private void connectSocket() {
        try {
            socket = new Socket("localhost", 5001);
            System.out.println("Connect With Server");

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            printWriter = new PrintWriter(socket.getOutputStream(), true);

            this.start();

        } catch (IOException e) {

        }
    }

    public void run() {
        try {
            while (true) {
                String msg = bufferedReader.readLine();
                System.out.println("Message : " + msg);
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println("cmd : " + cmd);
                StringBuilder fulmsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fulmsg.append(tokens[i]);
                }
                System.out.println("fulmsg : " + fulmsg);
                System.out.println();
                if (cmd.equalsIgnoreCase(username + ":")) {
                    continue;
                } else if (fulmsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }
                txtClientPane.appendText(msg + "\n");
            }
            bufferedReader.close();
            printWriter.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addImageOnAction(MouseEvent mouseEvent) {

    }

    public void sendEmojiOnAction(MouseEvent mouseEvent) {

    }

    public void sendOnMsgOnaction(MouseEvent mouseEvent) throws IOException {
        send();
    }
    public void send() {
        String msg = txtClientMessage.getText();
        printWriter.println(username + ": " + msg);
        txtClientPane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        txtClientPane.appendText("Me: " + msg + "\n");
        txtClientMessage.setText("");
        if (msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }
    }

}
