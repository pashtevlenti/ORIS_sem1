package ru.itis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {

    private static final Logger logger = LogManager.getLogger(ClientHandler.class);

    private final Socket socket;
    private final Map<String, GameRoom> rooms;
    private GameRoom currentRoom;
    private PrintWriter out;
    private boolean isReady = false;
    private final Gson gson = new Gson();
    private boolean isMyTurn = true;


    public ClientHandler(Socket socket, Map<String, GameRoom> rooms) {
        this.socket = socket;
        this.rooms = rooms;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            this.out = out;
            Message message;
            String roomName = "";

            message = gson.fromJson(in.readLine(), Message.class);
            if (message.getType().equals(ProtocolMessageType.ROOM_NAME)){
                roomName = message.getContent();
            }

            currentRoom = rooms.computeIfAbsent(roomName, GameRoom::new);
            logger.info("Player joined room: " + roomName);


            if (!currentRoom.addPlayer(this)) {
                message = new Message(ProtocolMessageType.ROOM_FULL);
                out.println(message.toJson());
                socket.close();
                return;
            } else {
                message = new Message(ProtocolMessageType.ADD_PLAYER,roomName);
                out.println(message.toJson());
            }


            logger.info("{} {}", rooms, currentRoom);
            while (true) {
                String input = in.readLine();

                Message client = gson.fromJson(input, Message.class);
                if (client.getType().equals(ProtocolMessageType.EXIT)) {
                    if (currentRoom != null) {
                        rooms.remove(roomName);
                        logger.info("{} {}", rooms, currentRoom);
                        currentRoom.exit(this);

                    }
                }
                else currentRoom.waitForPlayers();
                if (input == null) break;

                if (client.getType().equals(ProtocolMessageType.READY)){
                    currentRoom.setPlayerReady(this);
                } else if (client.getType().equals(ProtocolMessageType.NO_MY_MOVE)) {
                    if (currentRoom != null) {
                        currentRoom.processMoveTurn(this);
                    }
                } else if (client.getType().equals(ProtocolMessageType.NOT_AVAILABLE_MOVES)) {
                    if (currentRoom != null) {
                        currentRoom.checkAvailableMoves(this);
                    }
                } else if (client.getType().equals(ProtocolMessageType.WIN)) {
                    if (currentRoom != null) {
                        currentRoom.checkWin();
                    }
                }else if (client.getType().equals(ProtocolMessageType.GAME_OVER)) {
                    if (currentRoom != null) {
                        rooms.remove(roomName);
                        socket.close();
                    }
                }else if (client.getType().equals(ProtocolMessageType.MOVE)) {
                    if (currentRoom != null) {
                        currentRoom.processMove(client.getContent());
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error in client handler", e);
        }
    }

    public void sendUpdate(String update) {
        out.println(update);
    }

    public void startGame() {
        Message message = new Message(ProtocolMessageType.START);
        out.println(message.toJson());
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public void sendReady() {
        Message message = new Message(ProtocolMessageType.READY);
        out.println(message.toJson());
    }

    public void sendTurn(String turn) {
        out.println(turn);
    }
    public Socket getSocket(){
        return socket;
    }
}
