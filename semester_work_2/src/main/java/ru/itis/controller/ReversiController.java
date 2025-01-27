package ru.itis.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ru.itis.Client;

public class ReversiController {

    private Client client;
    private String username;


    @FXML
    private Button readyButton = new Button();;
    private boolean isReady = false;

    @FXML
    private Circle[][] circle = new Circle[8][8];
    @FXML
    private GridPane gridPane;


    public void initialize() {
        circle[3][3] = new Circle();
        circle[4][4] = new Circle();

        circle[3][4] = new Circle();
        circle[4][3] = new Circle();

        circle[3][3].setFill(Color.RED);
        circle[4][4].setFill(Color.RED);

        circle[3][4].setFill(Color.BLACK);
        circle[4][3].setFill(Color.BLACK);
    }

    public void setUsernameAndClient(String username,Client client) {
        this.username = username;
        this.client = client;
        client.setReversiController(this); // Устанавливаем связь с клиентом
    }

    @FXML
    public void handleReadyClick() {
        readyButton.setDisable(true);
        client.sendReady();
    }

    @FXML
    public void handleCircleClick(MouseEvent event) {
        if (!isReady) {
            showMessage("Wait for both players to be ready.");
            return;
        }

        Circle clickedCircle = (Circle) event.getSource();
        Integer row = GridPane.getRowIndex(clickedCircle);
        Integer col = GridPane.getColumnIndex(clickedCircle);

        if (row == null || col == null) return; // Защита от некорректного клика

        if (clickedCircle.getFill() == Color.WHITE) {
            clickedCircle.setFill(Color.RED);
        } else {
            clickedCircle.setFill(Color.BLACK);
        }

        // Отправляем ход на сервер
        client.sendMove(row + "," + col);
    }

    public void updateBoard(String move) {
        String[] parts = move.split(",");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);

        // Находим круг по координатам
        Circle circle = (Circle) gridPane.getChildren().stream()
                .filter(node -> GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col)
                .findFirst()
                .orElse(null);

        if (circle != null) {
            circle.setFill(Color.BLUE); // Например, синий цвет для хода противника
        }
    }

    public void enableBoard() {
        isReady = true;
        showMessage("Game started!");
    }

    public void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}


