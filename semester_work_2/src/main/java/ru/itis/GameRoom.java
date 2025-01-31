package ru.itis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

public class GameRoom {
    private static final Logger logger = LogManager.getLogger(GameRoom.class);

    private final String roomName;
    private final List<ClientHandler> players = new ArrayList<>();
    private final Object lock = new Object();
    private boolean gameStarted = false;

    public GameRoom(String roomName) {
        this.roomName = roomName;
    }

    public boolean addPlayer(ClientHandler player) {
        synchronized (lock) {
            if (players.size() < 2) {
                logger.info("Player added to room: " + roomName);
                players.add(player);
                if (players.size() == 2) {
                    lock.notifyAll(); // Оповещаем, что оба игрока подключились
                }
                return true;
            }
            return false;
        }
    }

    public void waitForPlayers() {
        synchronized (lock) {
            while (players.size() < 2) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void setPlayerReady(ClientHandler player) {
        synchronized (lock) {
            player.setReady(true);
            if (players.stream().allMatch(ClientHandler::isReady)) {
                gameStarted = true;
                players.forEach(ClientHandler::startGame); // Уведомляем игроков о начале игры
            } else {
                player.sendReady();
            }
        }
    }

    public synchronized void processMove(String move) {
        Message message;
        for (ClientHandler player : players) {
            message = new Message(ProtocolMessageType.MOVE,move);
            player.sendUpdate(message.toJson());
        }
    }
    public synchronized void processMoveTurn(ClientHandler sender) {
        Message message;
        for (ClientHandler player : players) {
            if (player != sender) {
                message = new Message(ProtocolMessageType.MY_MOVE);
                player.sendTurn(message.toJson());
            }
            else {
                message = new Message(ProtocolMessageType.NO_MY_MOVE);
                player.sendTurn(message.toJson());
            }
        }
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public synchronized void checkAvailableMoves(ClientHandler sender) {
        Message message;
        for (ClientHandler player : players) {
            if (player != sender) {
                message = new Message(ProtocolMessageType.CHECK_AVAILABLE_MOVES_OPPONENT);
                player.sendTurn(message.toJson());
            }
            else {
                message = new Message(ProtocolMessageType.NO_MY_MOVE);
                player.sendTurn(message.toJson());
            }
        }
    }

    public void checkWin() {
        for (ClientHandler player : players) {
            Message message = new Message(ProtocolMessageType.WIN);
            player.sendTurn(message.toJson());
        }
    }

    public void exit(ClientHandler sender) {
        for (ClientHandler player : players) {
            try {
                if (player != sender) {
                    Message message = new Message(ProtocolMessageType.EXIT);
                    player.sendTurn(message.toJson());
                }
                player.getSocket().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }
}

