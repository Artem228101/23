package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import java.io.IOException;
import java.sql.SQLException;

public class ControllerRegistration {
    @FXML private Button buttonRegistration;
    @FXML private Button backButton;
    @FXML private Label errorLabel;
    @FXML private TextField loginTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField passwordTextField;
    private final DatabaseHandler databaseHandler = new DatabaseHandler();
    @FXML
    void initialize() {
        buttonRegistration.setOnAction(actionEvent -> {
            try {signUp();}
            catch (SQLException | ClassNotFoundException throwables) {throwables.printStackTrace();}
        });
        backButton.setOnAction(actionEvent -> openAuthorizationWindow());
    }

    private void signUp() throws SQLException, ClassNotFoundException {
        String username = loginTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String password = passwordTextField.getText().trim();
        if (databaseHandler.signUpUser(username, email, password)) {
            openAuthorizationWindow();
        } else {
            errorLabel.setText("Логин или пароль не введён");
        }
    }

    private void openAuthorizationWindow(){
        buttonRegistration.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("resources/com/example/demo1/registration.fxml"));
        try {loader.load();}
        catch (IOException ioException) {ioException.printStackTrace();}
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
