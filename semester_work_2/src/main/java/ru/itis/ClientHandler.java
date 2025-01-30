package ru.itis;

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

            out.println("Enter room name:");
            String roomName = in.readLine();

            currentRoom = rooms.computeIfAbsent(roomName, GameRoom::new);
            logger.info("Player joined room: " + roomName);

            if (!currentRoom.addPlayer(this)) {
                out.println("Room is full. Please try another room.");
                socket.close();
                return;
            } else {
                out.println("addPlayer" + roomName);
            }

            out.println("Waiting for another player...");



            logger.info("{} {}", rooms, currentRoom);
            while (true) {
                String input = in.readLine();

                if ("exit".equals(input)) {
                    if (currentRoom != null) {
                        rooms.remove(roomName);
                        logger.info("{} {}", rooms, currentRoom);
                        currentRoom.exit(this);

                    }
                }
                else currentRoom.waitForPlayers();
                if (input == null) break;

                if ("READY".equals(input)) {
                    currentRoom.setPlayerReady(this);
                } else if ("NoMyMove".equals(input)) {
                    if (currentRoom != null) {
                        currentRoom.processMoveTurn(this);
                    }
                } else if ("notAvailableMoves".equals(input)) {
                    if (currentRoom != null) {
                        currentRoom.checkAvailableMoves(this);
                    }
                } else if ("win".equals(input)) {
                    if (currentRoom != null) {
                        currentRoom.checkWin();
                    }
                }else if ("gameOver".equals(input)) {
                    if (currentRoom != null) {
                        rooms.remove(roomName);
                        socket.close();
                    }
                }else if (input.startsWith("MOVE")) {
                    if (currentRoom != null) {
                        currentRoom.processMove(input);
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
        out.println("START");
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public void sendReady() {
        out.println("READY");
    }

    public void sendTurn(String turn) {
        out.println(turn);
    }
    public Socket getSocket(){
        return socket;
    }
}
