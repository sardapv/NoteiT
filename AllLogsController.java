package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by pranav on 10/04/17.
 */
public class AllLogsController implements Initializable {

    Connection connection;
    @FXML
    public ListView listView;
    public ObservableList<String> list = FXCollections.observableArrayList();;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = SqliteConnection.Connector();
            if (connection == null) {
                System.out.print("No connection");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM DATA WHERE id = ?";
        String mediapath = null;
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,Controller.id_logged_in);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                mediapath = resultSet.getString("dateofday") + " | " + resultSet.getString("title");
                list.add(mediapath);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                listView.setItems(list);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
