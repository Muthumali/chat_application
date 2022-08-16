package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import static controller.loginFormController.username;
public class clientController extends Thread {
    public TextArea txtClientPane;
    public JFXTextField txtClientMessage;
    public AnchorPane clientContext;
    public  Label txtClientName;
    public VBox vBoxPane;
    Socket socket=null;
    PrintWriter printWriter;

    private BufferedReader bufferedReader;
    FileChooser fileChooser;
    File filePath;


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

    String cmd;
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
                if (cmd.equalsIgnoreCase(loginFormController.username + ":")) {
                    continue;
                } else if (fulmsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }
                txtClientPane.setStyle("-fx-text-fill: green;-fx-font-size : 20px;");
                txtClientPane.appendText(msg + "\n");
            }
            bufferedReader.close();
            printWriter.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendOnMsgOnaction(MouseEvent mouseEvent) throws IOException {
        send();
    }
    public void send() {
        String msg = txtClientMessage.getText();
        printWriter.println(loginFormController.username + ": " + msg);
        txtClientPane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        txtClientPane.setStyle("-fx-text-fill: blue;-fx-font-size : 20px;");
        txtClientPane.appendText("Me: " + msg + "\n");
        txtClientMessage.setText("");
        if (msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }

    }

    public void sendImageAction(MouseEvent mouseEvent) throws IOException, InterruptedException {
        OutputStream outputStream = socket.getOutputStream();
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        System.out.println("Load Image Button Pressed");
        fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        BufferedImage image = ImageIO.read(new File(file.toURI()));

    }

    public void SendEmojiAction(MouseEvent mouseEvent) {
    }

    public void sendImage() throws IOException, InterruptedException {





    }
}
