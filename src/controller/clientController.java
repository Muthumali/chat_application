package controller;

import com.jfoenix.controls.JFXTextField;
import com.sun.glass.ui.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import static controller.loginFormController.username;
public class clientController extends Thread {
    public TextArea txtClientPane;
    public JFXTextField txtClientMessage;
    public AnchorPane clientContext;
    public  Label txtClientName;
    public AnchorPane emojiContext;
    public ImageView emoBtn;
    public VBox vBoxPane;
    Socket socket=null;
    PrintWriter printWriter;
    URL url;
    private BufferedReader bufferedReader;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    FileChooser fileChooser;
    File filePath;
    String msg;


    public void initialize() throws IOException {
        emojiContext.setVisible(false);
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
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        HBox hBox = new HBox();
                        if (!fulmsg.toString().endsWith(".png") || !fulmsg.toString().endsWith(".jpg") || !fulmsg.toString().endsWith(".jpeg") || !fulmsg.toString().endsWith(".gif")) {
                            hBox.setAlignment(Pos.CENTER_LEFT);
                            hBox.setPadding(new Insets(5, 10, 5, 5));
                            Text text = new Text(msg);
                            text.setStyle("-fx-font-size: 15px");
                            TextFlow textFlow = new TextFlow(text);
                            textFlow.setStyle("-fx-color:rgb(239,242,255);"
                                    + "-fx-background-color: rgb(182,182,182);" +
                                    "-fx-background-radius: 10px");
                            textFlow.setPadding(new Insets(5, 0, 5, 5));
                            text.setFill(Color.color(0, 0, 0));
                            hBox.getChildren().add(textFlow);
                            vBoxPane.getChildren().add(hBox);
                        }

                        if (fulmsg.toString().endsWith(".png") || fulmsg.toString().endsWith(".jpg") || fulmsg.toString().endsWith(".jpeg") || fulmsg.toString().endsWith(".gif")) {
                            System.out.println(fulmsg);
                            hBox.setAlignment(Pos.TOP_LEFT);
                            hBox.setPadding(new Insets(5, 10, 5, 5));
                            Text text = new Text(cmd + " ");
                            text.setStyle("-fx-font-size: 15px");
                            ImageView imageView = new ImageView();
                            Image image = new Image(String.valueOf(fulmsg));
                            imageView.setImage(image);
                            imageView.setFitWidth(100);
                            imageView.setFitHeight(100);
                            TextFlow textFlow = new TextFlow(text, imageView);
                            textFlow.setStyle("-fx-color:rgb(239,242,255);"
                                    + "-fx-background-color: rgb(182,182,182);" +
                                    "-fx-background-radius: 10px");
                            textFlow.setPadding(new Insets(5, 0, 5, 5));

                            hBox.getChildren().add(textFlow);
                            vBoxPane.getChildren().add(hBox);
                        }
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
            emojiContext.setVisible(false);
    }
    public void send() {
        String msg = txtClientMessage.getText();
        printWriter.println(loginFormController.username + ": " + msg);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 5, 10));
        Text text = new Text("Me : "+msg);
        text.setStyle("-fx-font-size: 15px");
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-color:rgb(239,242,255);"
                + "-fx-background-color: rgb(15,125,242);" +
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.color(0.934, 0.945, 0.996));
        hBox.getChildren().add(textFlow);
        vBoxPane.getChildren().add(hBox);
        printWriter.flush();
        txtClientMessage.setText("");
        if (msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }
    }
    public void sendImageAction(MouseEvent mouseEvent) throws IOException, InterruptedException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Image");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            printWriter.println(username + ": " + file.toURI().toURL());
        }
        if (file != null) {
            System.out.println("File Was Selected");
            url = file.toURI().toURL();
            System.out.println(url);
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 10, 5, 5));
            ImageView imageView = new ImageView();
            Image image = new Image(String.valueOf(url));
            imageView.setImage(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            VBox vBox = new VBox(imageView);
            vBox.setAlignment(Pos.CENTER_RIGHT);
            vBox.setPadding(new Insets(5, 10, 5, 5));
            vBoxPane.getChildren().add(vBox);
        }

    }
    public void SendEmojiAction(MouseEvent e) throws IOException {
      /*  Parent root = FXMLLoader.load(this.getClass().getResource("../view/emojiPaneF.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(mainScene);
        primaryStage.centerOnScreen();
        primaryStage.show();*/
        emojiContext.setVisible(!emojiContext.isVisible());

    }
    public void Send3EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void SendOneEmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send10EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send6EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Sen2EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send9EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send4EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send5EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send15EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send12EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send7EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send8EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send11EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send14EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send20emojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send18EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send19EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send17EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send16EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }

    public void Send13EmojiOnActoion(MouseEvent mouseEvent) {
        sendEmoji();
    }
    public void sendEmoji(){
       txtClientMessage.appendText("\uD83D\uDE42");
    }

}
