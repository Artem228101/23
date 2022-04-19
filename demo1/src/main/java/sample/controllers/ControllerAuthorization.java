package sample.controllers;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.database.User;

public class ControllerAuthorization {
    @FXML private Button buttonAuthorization;
    @FXML private Button buttonRegistration;
    @FXML private Label errorLabel;
    @FXML private TextField loginTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField passwordTextField;
    private final DatabaseHandler databaseHandler = new DatabaseHandler();
    @FXML
    void initialize() {
        buttonAuthorization.setOnAction(actionEvent -> {
            try {loginUser();}
            catch (SQLException | ClassNotFoundException throwables) {throwables.printStackTrace();}
        });
        buttonRegistration.setOnAction(actionEvent -> openOtherWindow("com/example/demo1/hello-view.fxml"));
    }

    private void loginUser() throws SQLException, ClassNotFoundException {
        String username = loginTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String password = passwordTextField.getText().trim();
        if (databaseHandler.signInUser(username, email, password)) {
            boolean role = databaseHandler.returnRole(username);
            User.setUsername(username);
            User.setRole(role);
            openOtherWindow("resources/com/example/demo1/desktop.fxml");
        } else {
            errorLabel.setText("Логин или пароль неверен");
        }
    }

    private void openOtherWindow(String path){
        buttonAuthorization.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        try {loader.load();}
        catch (IOException ioException) {ioException.printStackTrace();}
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
