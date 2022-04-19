package sample.controllers;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.database.FilmsData;
import sample.database.DatabaseHandler;
import sample.database.FilmsData;
import sample.database.User;

public class ControllerDesktop {
    @FXML private TableView<FilmsData> filmsTable;
    @FXML private TableColumn<FilmsData, Integer> idTC;
    @FXML private TableColumn<FilmsData, String> typeTC;
    @FXML private TableColumn<FilmsData, String> priceTC;
    @FXML private TableColumn<FilmsData, String> addressTC;

    @FXML private TextField idTextField;

    @FXML private Button openButton;
    @FXML private Button deleteButton;
    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button exitButton;
    @FXML private Button requestsButton;

    DatabaseHandler databaseHandler = new DatabaseHandler();

    @FXML void initialize() {
        idTC.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeTC.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceTC.setCellValueFactory(new PropertyValueFactory<>("price"));
        addressTC.setCellValueFactory(new PropertyValueFactory<>("address"));

        updateTable();

        deleteButton.setVisible(User.getRole());
        addButton.setVisible(User.getRole());
        editButton.setVisible(User.getRole());
        requestsButton.setVisible(User.getRole());

        openButton.setOnAction(actionEvent -> {
            try {
                User.setEditedRecordId(Integer.parseInt(idTextField.getText()));
                openOtherWindow("/sample/layout/open_record.fxml");
            }
            catch (Exception ignored){}
        });

        editButton.setOnAction(actionEvent -> {
            try {
                User.setEditedRecordId(Integer.parseInt(idTextField.getText()));
                openOtherWindow("/sample/layout/edit_record.fxml");
            } catch (Exception ignored) {
            }
        });

        deleteButton.setOnAction(actionEvent -> {
            try {
                databaseHandler.deleteRecord(Integer.parseInt(idTextField.getText()));
                updateTable();
            }catch (Exception exception){exception.printStackTrace();}
        });

        addButton.setOnAction(actionEvent -> {
            try {openOtherWindow("resources/com/example/demo1/add_record.fxml");}
            catch (Exception ignored) {}
        });

        requestsButton.setOnAction(actionEvent -> openOtherWindow("resources/com/example/demo1/requests.fxml"));

        exitButton.setOnAction(actionEvent -> openOtherWindow("resources/com/example/demo1/hello-view.fxml"));
    }

    private void updateTable(){
        try {
            filmsTable.setItems(databaseHandler.returnFilms("SELECT * FROM films"));
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }

    private void openOtherWindow(String window){
        exitButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
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
