package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.FilmsData;
import sample.database.DatabaseHandler;
import sample.database.FilmsData;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerAddRecord {
    @FXML private Label errorLabel;

    @FXML private TextField typeTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField descriptionTextField;

    @FXML private Button applyButton;
    @FXML private Button cancelButton;

    DatabaseHandler databaseHandler = new DatabaseHandler();
    FilmsData filmsData = new FilmsData();

    boolean nameSet;
    boolean institutionSet;
    boolean documentsSet;
    boolean statusSet;

    @FXML void initialize() {
        applyButton.setOnAction(actionEvent -> {
            try {
                errorLabel.setText("");

                String type = typeTextField.getText();
                String price = priceTextField.getText();
                String address = addressTextField.getText();
                String description = descriptionTextField.getText();

                if (!type.equals("")) {
                    filmsData.setType(type);
                    nameSet = true;
                }
                if (!price.equals("")) {
                    filmsData.setPrice(price);
                    institutionSet = true;
                }
                if (!address.equals("")) {
                    filmsData.setAddress(address);
                    documentsSet = true;
                }
                if (!description.equals("")) {
                    filmsData.setDescription(description);
                    statusSet = true;
                }

                if (nameSet && institutionSet && documentsSet && statusSet) {

                    databaseHandler.addRecord(filmsData);
                    openDesktopWindow();
                }
                else {
                    errorLabel.setText("Не все поля заполнены");
                }
            }
            catch(SQLException | ClassNotFoundException throwables){
                throwables.printStackTrace();
            }
        });

        cancelButton.setOnAction(actionEvent -> openDesktopWindow());
    }

    private void openDesktopWindow(){
        cancelButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/layout/desktop.fxml"));
        try {
            loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
