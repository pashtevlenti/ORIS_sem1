package ru.itis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    private final Gson gson = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .create();


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

        new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    Message msg = gson.fromJson(message, Message.class);
                    logger.info(message);
                    final String receivedMessage = msg.getContent();
                    if (msg.getType().equals(ProtocolMessageType.MOVE)) {
                        Platform.runLater(() -> receiveUpdate(receivedMessage));
                    } else if (msg.getType().equals(ProtocolMessageType.START)) {
                        Platform.runLater(() -> reversiController.enableBoard());
                    }else if (msg.getType().equals(ProtocolMessageType.MY_MOVE)) {
                        Platform.runLater(() -> reversiController.setMyTurn(true)); // Разрешаем ход.
                    } else if (msg.getType().equals(ProtocolMessageType.NO_MY_MOVE)) {
                        Platform.runLater(() -> reversiController.setMyTurn(false));
                    } else if (msg.getType().equals(ProtocolMessageType.READY)) {
                        Platform.runLater(() -> reversiController.showMessage("Ты готов, ждем пока будет готов соперник"));
                        Platform.runLater(() -> reversiController.setFlagWhoIsFirstReady(true));
                    } else if (msg.getType().equals(ProtocolMessageType.CHECK_AVAILABLE_MOVES_OPPONENT)) {
                        Platform.runLater(() -> reversiController.checkBoard());
                    }else if (msg.getType().equals(ProtocolMessageType.WIN)) {
                        Platform.runLater(() -> reversiController.checkWin());
                    }else if (msg.getType().equals(ProtocolMessageType.EXIT)) {
                        Platform.runLater(() -> reversiController.showMessage("Соперник вышел, игра окончена"));
                        Platform.runLater(() -> reversiController.close());
                    }else if (msg.getType().equals(ProtocolMessageType.ROOM_FULL)) {
                        Platform.runLater(() -> roomController.showAlert("Error","Комната заполнена", Alert.AlertType.ERROR));
                    } else if (msg.getType().equals(ProtocolMessageType.ADD_PLAYER)) {
                        Platform.runLater(() -> roomController.addPlayerToRoom(receivedMessage));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMove(String move) {
        out.println(move);
    }

    public void sendReady() {
        Message message = new Message(ProtocolMessageType.READY);
        out.println(message.toJson());
    }

    public void sendTurnMove(String turnMove) {
        out.println(turnMove);
    }

    public void receiveUpdate(String message) {
        if (reversiController != null) {
            reversiController.updateBoard(message);
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
