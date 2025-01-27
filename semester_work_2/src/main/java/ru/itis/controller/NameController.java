package ru.itis.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import ru.itis.Client;

import java.io.IOException;

public class NameController {
    @FXML
    private TextField usernameField;

    private Client client;

    @FXML
    private void handleNameButtonAction() {
        String username = usernameField.getText();
        if (username.isEmpty()) {
            showAlert("Error", "Username cannot be empty", AlertType.ERROR);
        } else {
            try {
                // Создаем новый клиент
                client = new Client();
                // Переходим в окно выбора комнаты
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/room.fxml"));
                StackPane root = loader.load();
                RoomController roomController = loader.getController();
                roomController.setUsername(username, client); // Передаем имя и клиента
                // Закрываем текущее окно и показываем новое
                usernameField.getScene().setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
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
