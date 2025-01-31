package ru.itis.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itis.Client;
import ru.itis.Message;
import ru.itis.ProtocolMessageType;

import java.util.List;

public class ReversiController {
    private static final Logger logger = LogManager.getLogger(ReversiController.class);

    private Client client;
    @FXML
    private Label move;
    @FXML
    private ImageView imageView;
    @FXML
    private Label infoGame;
    @FXML
    private StackPane stackPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label roomLabel;
    @FXML
    private Label colorLabel;
    @FXML
    private Button readyButton = new Button();
    @FXML
    private Button readyButtonTurnMove = new Button();
    @FXML
    public Button availableMove = new Button();
    @FXML
    private List<Circle> circles;

    private boolean isReady = false;
    private boolean isMyTurn;
    private boolean flagWhoIsFirstReady = false;


    private static final int SIZE = 8;
    private final int[][] board = new int[SIZE][SIZE];
    private byte color;

    private int rowPrev,colPrev;


    @FXML
    public void initialize() {
        Platform.runLater(() ->{
            Stage stage = (Stage) stackPane.getScene().getWindow();

            stage.setOnCloseRequest(event -> {
                Message message = new Message(ProtocolMessageType.EXIT);
                client.sendTurnMove(message.toJson());
            });
        });

        stackPane.widthProperty().addListener((obs, oldVal, newVal) -> adjustSizes(newVal.doubleValue(), stackPane.getHeight()));
        stackPane.heightProperty().addListener((obs, oldVal, newVal) -> adjustSizes(stackPane.getWidth(), newVal.doubleValue()));

        readyButtonTurnMove.setDisable(true);
        availableMove.setDisable(true);


        imageView.setImage(new Image("bell_with_gray_background.gif"));
        imageView.setOpacity(0);

        board[3][3] = 1;
        board[4][4] = 1;
        board[4][3] = 2;
        board[3][4] = 2;

        rowPrev = -1;
        colPrev = -1;
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
        readyButton.setStyle("-fx-font-size: " + buttonFontSize + "px; -fx-font-weight: bold");
        readyButtonTurnMove.setStyle("-fx-font-size: " + buttonFontSize + "px; -fx-font-weight: bold");
        availableMove.setStyle("-fx-font-size: " + buttonFontSize + "px; -fx-font-weight: bold");

        double labelFontSize = gridSize / 20;
        usernameLabel.setStyle("-fx-font-size: " + labelFontSize + "px; -fx-font-weight: bold");
        move.setStyle("-fx-font-size: " + labelFontSize + "px; -fx-font-weight: bold");
        roomLabel.setStyle("-fx-font-size: " + labelFontSize + "px; -fx-font-weight: bold");
        infoGame.setStyle("-fx-font-size: " + labelFontSize + "px; -fx-font-weight: bold");
        imageView.setFitWidth(labelFontSize * 3);
        imageView.setFitHeight(labelFontSize * 3);
        if (color == 1) colorLabel.setStyle("-fx-font-size: " + labelFontSize + "px; -fx-font-weight: bold; -fx-text-fill : red");
        else colorLabel.setStyle("-fx-font-size: " + labelFontSize + "px; -fx-font-weight: bold; -fx-text-fill : black");

    }

    public void setUsernameAndClient(String username, String room, Client client) {
        usernameLabel.setText("Имя: " + username);
        roomLabel.setText("Комната: " + room);
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
            infoGame.setText("Подождите, пока оба игрока не будут готовы.");
            return;
        }

        int currentPlayer = color;
        logger.info(currentPlayer);

        Circle clickedCircle = (Circle) event.getSource();
        Integer row = GridPane.getRowIndex(clickedCircle);
        Integer col = GridPane.getColumnIndex(clickedCircle);


        if (isValidMove(row, col, currentPlayer)) {
            if ((rowPrev != -1) && (colPrev != -1)){
                placeDisc(rowPrev,colPrev,0,false);
            }
            allValidMoves(color,0);
            placeDisc(row, col, currentPlayer,false);

            rowPrev = row;
            colPrev = col;

        }
        else{
            infoGame.setText("Недопустимый ход");

        }


    }

    private void placeDisc(int row, int col, int player, boolean needColor) {
        if (needColor) board[row][col] = player;
        Circle circle = (Circle) gridPane.getChildren().stream()
                .filter(node -> GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col)
                .findFirst()
                .orElse(null);

        if (circle != null)
            circle.setFill(player == 1 ? Color.RED :
                                player == 0 ? Color.WHITE :
                                    player == 3 ? Color.GREEN : Color.BLACK);
    }

    private boolean isValidMove(int row, int col, int player) {
        if (board[row][col] != 0) return false;

        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if (dRow == 0 && dCol == 0) continue;
                if (canFlip(row, col, dRow, dCol, player)) return true;
            }
        }
        return false;
    }
    private boolean allValidMoves(int player, int availableMoveTrue){
        boolean flagExistAvailableMoves = false;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (isValidMove(row, col, player)) {
                    placeDisc(row,col,availableMoveTrue,false);
                    flagExistAvailableMoves = true;
                }
            }
        }
        return flagExistAvailableMoves;
    }

    private boolean canFlip(int row, int col, int dRow, int dCol, int player) {
        int opponent = player == 1 ? 2 : 1;
        int r = row + dRow, c = col + dCol;
        boolean foundOpponent = false;

        while (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c] == opponent) {
            foundOpponent = true;
            r += dRow;
            c += dCol;
        }

        return foundOpponent && r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c] == player;
    }

    private void flipDiscs(int row, int col, int player) {
        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if (dRow == 0 && dCol == 0) continue;
                if (canFlip(row, col, dRow, dCol, player)) {
                    int r = row + dRow, c = col + dCol;
                    while (board[r][c] != player) {
                        placeDisc(r, c, player,true);

                        r += dRow;
                        c += dCol;
                    }
                }
            }
        }
    }


    public void updateBoard(String move) {
        String[] parts = move.split(",");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);
        int currentPlayer = Integer.parseInt(parts[2]);

        if (isValidMove(row, col, currentPlayer)) {
            placeDisc(row, col, currentPlayer,true);
            flipDiscs(row, col, currentPlayer);
        }
    }

    public void enableBoard() {
        isReady = true;
        if (flagWhoIsFirstReady){
            color = 2;
            colorLabel.setText("Чёрный");

        }
        else {
            color = 1;
            colorLabel.setText("Красный");
            colorLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill : red");
            Message message = new Message(ProtocolMessageType.NO_MY_MOVE);
            client.sendTurnMove(message.toJson());

        }
        infoGame.setText("Игра началась!");
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
            move.setText("Твой ход");
            imageView.setOpacity(1);
        }
        else{
            move.setText("Ход соперника");
            imageView.setOpacity(0);
        }
        // Например, блокируем все ячейки на поле
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Circle) {
                node.setDisable(!isTurn);
            }
        }
        // Блокируем или разблокируем кнопку "Сделать ход"
        readyButtonTurnMove.setDisable(!isTurn);
        availableMove.setDisable(!isTurn);

    }

    @FXML
    public void handleReadyTurnMoveClick() {
        if (allValidMoves(color,0)) {
            if ((rowPrev != -1) && (colPrev != -1)) {
                placeDisc(rowPrev, colPrev, color,true);
                flipDiscs(rowPrev, colPrev, color);
                if (isMyTurn) {
                    isMyTurn = false;
                    Message message = new Message(ProtocolMessageType.NO_MY_MOVE);
                    client.sendTurnMove(message.toJson());

                    message = new Message(ProtocolMessageType.MOVE,"%s,%s,%s".formatted(rowPrev,colPrev,color));
                    client.sendMove(message.toJson());
                }
                rowPrev = -1;
                colPrev = -1;
            }
            else{
                infoGame.setText("Необходимо сделать ход");

            }
        }
        else {
            Message message = new Message(ProtocolMessageType.NOT_AVAILABLE_MOVES);
            client.sendTurnMove(message.toJson());

        }
    }

    public void checkBoard(){
        if (allValidMoves(color,0)) {
            setMyTurn(true);
        }
        else {
            setMyTurn(false);
            Message message = new Message(ProtocolMessageType.WIN);
            client.sendTurnMove(message.toJson());

        }

    }
    public void setFlagWhoIsFirstReady(boolean flagWhoIsFirstReady) {
        this.flagWhoIsFirstReady = flagWhoIsFirstReady;
    }

    @FXML
    public void handleAvailableMoveClick(ActionEvent actionEvent) {
        allValidMoves(color,3);
    }

    public void checkWin() {
        int myChip = 0;
        int countWhite = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == color) myChip++;
                if (board[row][col] == 0) countWhite++;
            }
        }

        int chipOpponent = 64 - countWhite - myChip;
        if (myChip > chipOpponent) {
            infoGame.setText("Ты победил! У тебя %s фишек, а у соперника %s".formatted(myChip, chipOpponent));

        }
        else if (myChip < chipOpponent) {
            infoGame.setText("Ты проиграл( У тебя %s фишек, а у соперника %s".formatted(myChip, chipOpponent));
        }
        else{
            infoGame.setText("Вы сыграли в ничью! У тебя %s фишек, а у соперника %s".formatted(myChip, chipOpponent));
        }
        Message message = new Message(ProtocolMessageType.GAME_OVER);
        client.sendTurnMove(message.toJson());

    }
    public void close() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }
}






