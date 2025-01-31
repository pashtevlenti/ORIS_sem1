package ru.itis.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itis.Client;



import java.io.IOException;



public class RoomController {
    Logger logger = LogManager.getLogger(RoomController.class);
    private String username;
    private Client client;
    private Parent previousRoot;
    private Stage primaryStage;

    @FXML
    private TextField roomNameField;

    public void setUsername(String username, Client client) {
        this.username = username;
        this.client = client;
        client.setRoomController(this);
    }

    @FXML
    private void handleJoinRoomButtonAction() {
        String roomName = roomNameField.getText();
        if (roomName.isEmpty()) {
            showAlert("Error", "Room name cannot be empty", AlertType.ERROR);
        } else{
            try {
                client.connectToServer("localhost", 12345);
                client.sendRoomName(roomName);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addPlayerToRoom(String roomName) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/game.fxml"));
            StackPane root = loader.load();
            ReversiController reversiController = loader.getController();
            reversiController.setUsernameAndClient(username,roomName,client);

            roomNameField.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to connect to the server", AlertType.ERROR);
        }
    }


    // Метод для отображения сообщений об ошибке
    public void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
