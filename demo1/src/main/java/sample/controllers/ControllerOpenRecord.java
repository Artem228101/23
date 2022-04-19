package sample.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.database.FilmsData;
import sample.database.DatabaseHandler;
import sample.database.User;
import java.io.IOException;
import java.sql.SQLException;

public class ControllerOpenRecord {
    @FXML private Label idLabel;
    @FXML private Label typeLabel;
    @FXML private Label priceLabel;
    @FXML private Label addressLabel;
    @FXML private Label descriptionLabel;
    @FXML private Button backButton;
    @FXML private Button bookButton;
    DatabaseHandler databaseHandler = new DatabaseHandler();
    @FXML void initialize() throws SQLException, ClassNotFoundException {
        ObservableList<FilmsData> filmsObservableList = databaseHandler.returnFilms(
                "SELECT * FROM films WHERE id=" + User.getEditedRecordId()
        );
        FilmsData filmsData = filmsObservableList.get(0);

        idLabel.setText(String.valueOf(filmsData.getId()));
        typeLabel.setText(filmsData.getType());
        priceLabel.setText(filmsData.getPrice());
        addressLabel.setText(filmsData.getAddress());
        descriptionLabel.setText(filmsData.getDescription());

        bookButton.setOnAction(actionEvent -> {
            try {databaseHandler.addRequests(User.getEditedRecordId(), User.getUsername());}
            catch (SQLException | ClassNotFoundException throwables){throwables.printStackTrace();}
        });

        backButton.setOnAction(actionEvent -> openDesktopWindow());
    }

    private void openDesktopWindow(){
        backButton.getScene().getWindow().hide();
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
