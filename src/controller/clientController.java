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
public class clientController extends Thread{
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
                cmd = tokens[0];
                System.out.println("cmd : " + cmd);
                StringBuilder fulmsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fulmsg.append(tokens[i]);
                }
                System.out.println("fullmsg : " + fulmsg);

                System.out.println();
                if (cmd.equalsIgnoreCase(loginFormController.username + " : ")) {
                    continue;
                } else if (fulmsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        HBox hBox = new HBox();
                        hBox.setAlignment(Pos.CENTER_RIGHT);
                        hBox.setPadding(new Insets(5, 10, 5, 5));
                        Text text = new Text(msg);
                        TextFlow textFlow = new TextFlow(text);
                        textFlow.setStyle("-fx-color:rgb(239,242,255);"
                                + "-fx-background-color: rgb(72,80,70);" +
                                "-fx-background-radius: 10px");
                        textFlow.setPadding(new Insets(5, 0, 5, 5));
                        text.setFill(Color.color(0.934, 0.945, 0.996));
                        hBox.getChildren().add(textFlow);
                        vBoxPane.getChildren().add(hBox);

                    }
                });
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
        if(!txtClientMessage.getText().equalsIgnoreCase("")) {
            String msg = txtClientMessage.getText();
            printWriter.println(username + ": " + msg);
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 5, 5, 10));
            Text text = new Text("Me : "+msg);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-color:rgb(239,242,255);"
                    + "-fx-background-color: rgb(15,125,242);" +
                    "-fx-background-radius: 20px");
            textFlow.setPadding(new Insets(5, 10, 5, 10));
            text.setFill(Color.color(0.934, 0.945, 0.996));
            hBox.getChildren().add(textFlow);
            vBoxPane.getChildren().add(hBox);
            printWriter.flush();
            if (msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
                System.exit(0);
            }
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
