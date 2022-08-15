package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;

public class loginFormController {
    public AnchorPane loginContext;
    public  TextField txtUserName;
    public static String username;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        username = txtUserName.getText();
        URL resource = getClass().getResource("../view/clientForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loginContext.getChildren().clear();
        loginContext.getChildren().add(load);
        txtUserName.clear();

    }

}
