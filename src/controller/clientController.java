package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.net.Socket;

public class clientController {
    public TextArea txtClientPane;
    public JFXTextField txtClientMessage;
    public AnchorPane clientContext;
    public Label txtClientName;
    public VBox vBoxPaneClinet;

    Socket socket=null;
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
        txtClientPane.appendText(txtClientMessage.getText()+"\n");
       /* txtClientPane.setStyle("-fx-background-radius: 20px");
        txtClientPane.setStyle("-fx-color: rgb(15,125,242)");*/
        printWriter.flush();
       /* DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(txtClientMessage.getText());
        txtClientPane.appendText();*/

        txtClientMessage.clear();


       /* PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        String messageToSend = txtClientMessage.getText();
        printWriter.println(txtClientMessage.getText());
        if (!txtClientMessage.getText().equals("exit")&& !txtClientMessage.getText().isEmpty()){
            HBox hBox=new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5,5,5,10));
            Text text=new Text(messageToSend);
            TextFlow textFlow=new TextFlow(text);
            textFlow.setStyle("-fx-color:rgb(239,242,255);"
                    +"-fx-background-color: rgb(15,125,242);"+
                    "-fx-background-radius: 20px");
            textFlow.setPadding(new Insets(5,10,5,10));
            text.setFill(Color.color(0.934,0.945,0.996));
            hBox.getChildren().add(textFlow);
            vBoxPaneClinet.getChildren().add(hBox);
            // txtClientPane.appendText(String.valueOf(text));
            printWriter.flush();
            txtClientMessage.clear();
        }*/
    }
}
