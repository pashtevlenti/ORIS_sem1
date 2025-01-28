package ru.itis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public synchronized void processMove(String move, ClientHandler sender) {
        // Отправляем ход всем игрокам, кроме отправителя
        for (ClientHandler player : players) {
            if (player != sender) {
                player.sendUpdate(move);
            }
        }
    }
    public synchronized void processTurn(ClientHandler sender) {
        for (ClientHandler player : players) {
            if (player != sender) {
                player.sendTurn("YourTurn");
            }
            else player.sendTurn("NoYourTurn");
        }
    }
}

