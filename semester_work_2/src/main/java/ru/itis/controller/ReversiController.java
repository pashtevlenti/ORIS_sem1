package ru.itis.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ru.itis.Client;

import java.util.List;

public class ReversiController {


    private Client client;
    @FXML
    private Label turn;
    @FXML
    private StackPane stackPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label roomLabel;
    @FXML
    private Button readyButton = new Button();
    @FXML
    private Button readyButtonTurn = new Button();

    private boolean isReady = false;
    private boolean isMyTurn;
    @FXML
    private List<Circle> circles;

    @FXML
    public void initialize() {

        stackPane.widthProperty().addListener((obs, oldVal, newVal) -> adjustSizes(newVal.doubleValue(), stackPane.getHeight()));
        stackPane.heightProperty().addListener((obs, oldVal, newVal) -> adjustSizes(stackPane.getWidth(), newVal.doubleValue()));
    }

    private void adjustSizes(double width, double height) {
        double gridSize = Math.min(width, height) * 0.8; // 80% от меньшей стороны окна
        gridPane.setPrefSize(gridSize, gridSize);

        double circleRadius = gridSize / 16; // Пример: делим сетку на 8 столбцов и 8 строк
        gridPane.getChildren().filtered(node -> node instanceof Circle).forEach(node -> {
            Circle circle = (Circle) node;
            circle.setRadius(circleRadius); // Радиус половина от высоты ячейки
        });

        double buttonFontSize = gridSize / 20;
        readyButton.setStyle("-fx-font-size: " + buttonFontSize + "px;");
        readyButtonTurn.setStyle("-fx-font-size: " + buttonFontSize + "px;");

        double labelFontSize = gridSize / 20;
        usernameLabel.setStyle("-fx-font-size: " + labelFontSize + "px;");
        turn.setStyle("-fx-font-size: " + labelFontSize + "px;");
        roomLabel.setStyle("-fx-font-size: " + labelFontSize + "px;");
    }

    public void setUsernameAndClient(String username, String room, Client client) {
        usernameLabel.setText("Username: " + username);
        roomLabel.setText("Room: " + room);
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

    public void setMyTurn(boolean isTurn) {
        isMyTurn = isTurn;
        if (isMyTurn) {
            turn.setText("Your turn");
        }
        else{
            turn.setText("Turn opponent");
        }
        // Например, блокируем все ячейки на поле
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Circle) {
                node.setDisable(!isTurn);
            }
        }
        // Блокируем или разблокируем кнопку "Сделать ход"
        readyButtonTurn.setDisable(!isTurn);
    }

    @FXML
    public void handleReadyTurnClick() {
        if (isMyTurn) {
            isMyTurn = false;
            client.sendTurn("NoYourTurn");
        }
    }


    public void setIsMyTurn(boolean b) {
        this.isMyTurn = b;
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}


