package controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class loginFormController {
    public AnchorPane loginContext;
    public TextField txtUserName;
   public ArrayList<String> Users= new ArrayList<String>();
    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        String user=txtUserName.getText();
        //txtClientName.setText("user");
        System.out.println(Users);
        if(Users.contains(user)){
            System.out.println("already");
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/clientForm.fxml"));
            Parent parent=loader.load();
            Scene scene=new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        }
        Users.add(user);


    }

}
