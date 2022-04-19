package sample.controllers;

import javafx.collections.ObservableList;
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
import sample.database.User;
import java.io.IOException;
import java.sql.SQLException;

public class ControllerEditRecord {
    @FXML private Label errorLabel;

    @FXML private TextField typeTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField descriptionTextField;

    @FXML private Button applyButton;
    @FXML private Button cancelButton;

    DatabaseHandler databaseHandler = new DatabaseHandler();

    @FXML void initialize() throws SQLException, ClassNotFoundException {
        ObservableList<FilmsData> filmsObservableList = databaseHandler.returnFilms(
                "SELECT * FROM films WHERE id=" + User.getEditedRecordId()
        );
        FilmsData filmsData = filmsObservableList.get(0);

        typeTextField.setText(filmsData.getType());
        priceTextField.setText(filmsData.getPrice());
        addressTextField.setText(filmsData.getAddress());
        descriptionTextField.setText(filmsData.getDescription());

        applyButton.setOnAction(actionEvent -> {
            String type = typeTextField.getText();
            String price = priceTextField.getText();
            String address = addressTextField.getText();
            String description = descriptionTextField.getText();

            if (!type.equals("")){
                filmsData.setType(type);
            }
            if (!price.equals("")){
                filmsData.setPrice(price);
            }
            if (!address.equals("")){
                filmsData.setAddress(address);
            }
            if (!description.equals("")){
                filmsData.setDescription(description);
            }

            try {databaseHandler.updateRecord(filmsData);}
            catch (SQLException | ClassNotFoundException throwables) {
                errorLabel.setText("Не все поля заполнены");
                throwables.printStackTrace();
            }
            openDesktopWindow();
        });

        cancelButton.setOnAction(actionEvent -> openDesktopWindow());
    }

    private void openDesktopWindow(){
        cancelButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("resources/com/example/demo1/desktop.fxml"));
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