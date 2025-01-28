package ru.itis.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import ru.itis.Client;

import java.io.IOException;

public class RoomController {
    private String username;
    private Client client;

    @FXML
    private TextField roomNameField;

    // Метод для установки имени пользователя и клиента
    public void setUsername(String username, Client client) {
        this.username = username;
        this.client = client;
    }

    @FXML
    private void handleJoinRoomButtonAction() {
        String roomName = roomNameField.getText();
        if (roomName.isEmpty()) {
            showAlert("Error", "Room name cannot be empty", AlertType.ERROR);
        } else {
            try {
                // Подключаемся к серверу
                client.connectToServer("localhost", 12345);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/game.fxml"));
                StackPane root = loader.load();
                ReversiController reversiController = loader.getController();
                reversiController.setUsernameAndClient(username,roomName,client);
                client.sendRoomName(roomName);

                roomNameField.getScene().setRoot(root);


            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Unable to connect to the server", AlertType.ERROR);
            }
        }
    }

    // Метод для отображения сообщений об ошибке
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
