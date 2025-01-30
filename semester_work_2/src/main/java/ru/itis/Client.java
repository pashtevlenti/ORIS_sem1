package ru.itis;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itis.controller.ReversiController;
import ru.itis.controller.RoomController;

import java.io.*;
import java.net.Socket;

public class Client extends Application {
    private static final Logger logger = LogManager.getLogger(Client.class);


    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ReversiController reversiController;
    private RoomController roomController;



    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/name.fxml"));
        StackPane root = loader.load();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Reversi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void connectToServer(String serverAddress, int port) throws IOException {
        socket = new Socket(serverAddress, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Поток для получения данных от сервера
        new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    logger.info(message);
                    final String receivedMessage = message;
                    if (message.startsWith("MOVE")) {
                        Platform.runLater(() -> receiveUpdate(receivedMessage));
                    } else if (message.equals("START")) {
                        Platform.runLater(() -> reversiController.enableBoard());
                    }else if (message.equals("MyMove")) {
                        Platform.runLater(() -> reversiController.setMyTurn(true)); // Разрешаем ход.
                    } else if (message.equals("NoMyMove")) {
                        Platform.runLater(() -> reversiController.setMyTurn(false));
                    } else if (message.equals("READY")) {
                        Platform.runLater(() -> reversiController.showMessage("Ты готов, ждем пока будет готов соперник"));
                        Platform.runLater(() -> reversiController.setFlagWhoIsFirstReady(true));
                    } else if (message.equals("checkAvailableMovesOpponent")) {
                        Platform.runLater(() -> reversiController.checkBoard());
                    }else if (message.equals("win")) {
                        Platform.runLater(() -> reversiController.checkWin());
                    }else if (message.equals("exit")) {
                        Platform.runLater(() -> reversiController.showMessage("Соперник вышел, игра окончена"));
                        Platform.runLater(() -> reversiController.close());
                    }else if (message.equals("Room is full. Please try another room.")) {
                        Platform.runLater(() -> roomController.showAlert("Error","Комната заполнена", Alert.AlertType.ERROR));
                    } else if (message.startsWith("addPlayer")) {
                        Platform.runLater(() -> roomController.addPlayerToRoom(receivedMessage.substring(9)));
                    }else {
                        System.out.println(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMove(String move) {
        out.println("MOVE " + move); // Отправляем ход на сервер
    }

    public void sendReady() {
        out.println("READY"); // Уведомляем сервер, что игрок готов
    }

    public void sendTurnMove(String turnMove) {
        out.println(turnMove);
    }

    public void receiveUpdate(String message) {
        if (reversiController != null) {
            String move = message.substring(5); // Убираем "MOVE "
            reversiController.updateBoard(move);
        }
    }

    public void setReversiController(ReversiController reversiController) {
        this.reversiController = reversiController;
    }

    public void close() throws IOException {
        socket.close();
    }

    public void sendRoomName(String roomName) {
        out.println(roomName);
    }


    public void setRoomController(RoomController roomController) {
        this.roomController = roomController;
    }
}
