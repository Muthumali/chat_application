package controller;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static controller.loginFormController.username;
public class clientController extends Thread {
    public JFXTextField txtClientMessage;
    public AnchorPane clientContext;
    public  Label txtClientName;
    public AnchorPane emojiContext;
    public ImageView emoBtn;
    public VBox vboxMessageFlow;
    public ImageView emoji1;
    public ImageView emoji3;
    public ImageView emoji10;
    public ImageView emoji6;
    public ImageView emoji2;
    public ImageView emoji9;
    public ImageView emoji4;
    public ImageView emoji5;
    public ImageView emoji15;
    public ImageView emoji13;
    public ImageView emoji12;
    public ImageView emoji7;
    public ImageView emoji8;
    public ImageView emoji14;
    public ImageView emoji11;
    public ImageView emoji20;
    public ImageView emoji19;
    public ImageView emoji18;
    public ImageView emoji17;
    public ImageView emoji16;
    Socket socket=null;
    PrintWriter printWriter;
    URL url;
    private BufferedReader bufferedReader;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    FileChooser fileChooser;
    File filePath;
    String msg;


    public void initialize(){
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
        } catch (IOException e) {}
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
                if (cmd.equalsIgnoreCase(loginFormController.username + ":")) {
                    continue;
                } else if (fulmsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        HBox hBox = new HBox();
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
                            vboxMessageFlow.getChildren().add(hBox);
                        }else {
                            hBox.setAlignment(Pos.CENTER_LEFT);
                            hBox.setPadding(new Insets(5, 10, 5, 5));
                            Text text = new Text(msg);
                            text.setStyle("-fx-font-size: 15px");
                            TextFlow textFlow = new TextFlow(text);
                            textFlow.setStyle("-fx-color:rgb(239,242,255);"
                                    + "-fx-background-color: rgb(51,217,178);" +
                                    "-fx-background-radius: 10px");
                            textFlow.setPadding(new Insets(5, 0, 5, 5));
                            text.setFill(Color.color(0, 0, 0));
                            hBox.getChildren().add(textFlow);
                            vboxMessageFlow.getChildren().add(hBox);
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
    public void sendOnMsgOnaction(MouseEvent mouseEvent) {
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
                + "-fx-background-color: rgb(33,140,116);" +
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        //text.setFill(Color.color(0.934, 0.945, 0.996));
        text.setFill(Color.color(0, 0, 0));
        hBox.getChildren().add(textFlow);
        vboxMessageFlow.getChildren().add(hBox);
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
            vboxMessageFlow.getChildren().add(vBox);
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
    public void sendEmoji(){
       txtClientMessage.appendText("\uD83D\uDE42");
    }

    public void allSendEmojiOnAction(MouseEvent mouseEvent) throws MalformedURLException {
        if (mouseEvent.getSource ( ) instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            switch (icon.getId()){
                case "emoji1":
                    byte[] emojiBytes1 = new byte[]{/*(byte) 0xF0,*/ (byte) 0xE2, (byte) 0x9D, (byte) 0xA4};
                    String emojiAsString1= new String(emojiBytes1, StandardCharsets.UTF_8);
                    txtClientMessage.appendText(emojiAsString1);
                    // heart
                    break;
                case "emoji2":
                    byte[] emojiBytes2 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xA1};
                    String emojiAsString2 = new String(emojiBytes2, StandardCharsets.UTF_8);
                    txtClientMessage.appendText(emojiAsString2);
                    //angree
                    break;
                case "emoji3":
                    byte[] emojiBytes3 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x8D};
                    String emojiAsString3 = new String(emojiBytes3, StandardCharsets.UTF_8);
                    txtClientMessage.appendText(emojiAsString3);
                    //\xF0\x9F\x98\x8D hearteye
                    break;
                case "emoji4":
                    byte[] emojiBytes4 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x82};
                    String emojiAsString4 = new String(emojiBytes4, StandardCharsets.UTF_8);
                    txtClientMessage.appendText(emojiAsString4);
                    //\xF0\x9F\x98\x82 haha
                    break;
                case "emoji5":
                    byte[] emojiBytes5 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x98};
                    String emojiAsString5 = new String(emojiBytes5, StandardCharsets.UTF_8);
                    txtClientMessage.appendText(emojiAsString5);
                    //\xF0\x9F\x98\x98 kiss
                    break;
                case "emoji6":
                    byte[] emojiBytes6 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xB3};
                    String emojiAsString6 = new String(emojiBytes6, StandardCharsets.UTF_8);
                    txtClientMessage.appendText(emojiAsString6);
                    //\xF0\x9F\x98\xB3 suprice
                    break;
                case "emoji7":
                    byte[] emojiBytes7 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x93};
                    String emojiAsString7 = new String(emojiBytes7, StandardCharsets.UTF_8);
                    txtClientMessage.appendText(emojiAsString7);
                    //\xF0\x9F\x98\x93 sad
                    break;
                case "emoji8":
                    byte[] emojiBytes8 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xB7};
                    String emojiAsString8 = new String(emojiBytes8, StandardCharsets.UTF_8);
                    txtClientMessage.appendText(emojiAsString8);
                    //\xF0\x9F\x98\xB7 maskface
                    break;
                case "emoji9":
                    byte[] emojiBytes9 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xB1};
                    String emojiAsString9 = new String(emojiBytes9, StandardCharsets.UTF_8);
                    txtClientMessage.appendText(emojiAsString9);
                    //\xF0\x9F\x98\xB1	wow
                    break;
                case "emoji10":
                    byte[] emojiBytes10 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xBB};
                    String emojiAsString10 = new String(emojiBytes10, StandardCharsets.UTF_8);
                    txtClientMessage.appendText(emojiAsString10);
                    //\xF0\x9F\x98\xBB catHeart
                    break;
            }
        }
    }
}
