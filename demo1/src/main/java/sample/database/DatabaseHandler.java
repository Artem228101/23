package sample.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

//Этот класс отвечает за взаимодействует с базой данных
public class DatabaseHandler extends Configs{
    Connection dbConnection;


    //В этом методе происходит подключение к БД
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbEmail + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString);
        return dbConnection;
    }

    //Этот метод авторизирует пользователя
    public boolean signInUser(String username, String password, String email) throws SQLException, ClassNotFoundException {
        String select = "SELECT * FROM users WHERE username=\"" + username +  "\"AND email=\"" + email + "\" AND password=\"" + password + "\"";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        try {
            //executeQuery() получает из БД значения и помещает их в resultSet
            //Если происходит ошибка - значит пользователя с введёнными логином или паролем нет
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (Exception exception){
            return false;
        }
    }

    public boolean signUpUser(String username,String email, String password){
        try {
            String insert = "INSERT INTO users (username, email, password, role) VALUES(?,?,?,?)";

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setBoolean(4, false);

            preparedStatement.executeUpdate();
            return true;
        }
        catch (Exception exception) {return false;}
    }

    public ObservableList<FilmsData> returnFilms(String select) throws SQLException, ClassNotFoundException {
        ObservableList<FilmsData> filmsObservableList = FXCollections.observableArrayList();
        Statement statement = getDbConnection().createStatement();

        ResultSet resultSet = statement.executeQuery(select);
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String type = resultSet.getString(2);
            String price = resultSet.getString(3);
            String address = resultSet.getString(4);
            String description = resultSet.getString(5);

            filmsObservableList.add(new FilmsData(id, type, price, address, description));
        }
        return filmsObservableList;
    }

    public void updateRecord(FilmsData filmsData) throws SQLException, ClassNotFoundException {
        String update = "UPDATE films set " +
                "type = \"" + filmsData.getType() +
                "\", price = \"" + filmsData.getPrice() +
                "\", address = \"" + filmsData.getAddress() +
                "\", description = \"" + filmsData.getDescription() +
                "\" WHERE (id = " + User.getEditedRecordId() + ")";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
        preparedStatement.executeUpdate();
    }

    public void addRecord(FilmsData filmsData) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO films " +
                "(type, price, address, description) " +
                "VALUES ('" +
                filmsData.getType() + "', '" + filmsData.getPrice() + "', '" +
                filmsData.getAddress() + "', '" + filmsData.getDescription() + "');";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.executeUpdate();
    }

    public Boolean returnRole(String username) throws SQLException, ClassNotFoundException {
        String select = "SELECT role FROM users WHERE username =\"" + username + "\"";

        Statement statement = getDbConnection().createStatement();

        ResultSet resultSet = statement.executeQuery(select);
        resultSet.next();

        return resultSet.getBoolean(1);
    }

    public void deleteRecord(int id){
        try {
            String select = "DELETE FROM apartments WHERE id = \"" + id + "\"";
            getDbConnection().prepareStatement(select).executeUpdate();
        }
        catch (Exception ignored){}
    }

    public void addRequests(int idFilm, String username) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO requests " +
                "(id_apartments, username) " +
                "VALUES ('" + idFilm + "', '" + username + "');";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.executeUpdate();
    }

    public ObservableList<RequestsData> returnRequests() throws SQLException, ClassNotFoundException {
        String select = "SELECT * FROM requests";

        ObservableList<RequestsData> requestsObservableList = FXCollections.observableArrayList();
        Statement statement = getDbConnection().createStatement();

        ResultSet resultSet = statement.executeQuery(select);
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            int idFilm = resultSet.getInt(2);
            String username = resultSet.getString(3);

            requestsObservableList.add(new RequestsData(id, idFilm, username));
        }
        return requestsObservableList;
    }

    public void deleteRequest(int id){
        try {
            String select = "DELETE FROM requests WHERE id = \"" + id + "\"";
            getDbConnection().prepareStatement(select).executeUpdate();
        }
        catch (Exception ignored){}
    }
}
