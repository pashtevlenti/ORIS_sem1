package ru.itis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameSession implements Runnable {
        private final Socket player1;
        private final Socket player2;

        private static final Logger logger = LogManager.getLogger(GameSession.class);
        public GameSession(Socket player1, Socket player2) {
            this.player1 = player1;
            this.player2 = player2;
        }

        @Override
        public void run() {
            try (
                    BufferedReader in1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
                    PrintWriter out1 = new PrintWriter(player1.getOutputStream(), true);
                    BufferedReader in2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));
                    PrintWriter out2 = new PrintWriter(player2.getOutputStream(), true)
            ) {

                logger.info("startGame!");
                out1.println("Your turn!");
                out2.println("Waiting for opponent's move...");

                while (true) {
                    // Считываем ход первого игрока
                    String move = in1.readLine();
                    if (move == null) break;
                    out2.println("Opponent moved: " + move);
                    out2.println("Your turn!");

                    // Считываем ход второго игрока
                    move = in2.readLine();
                    if (move == null) break;
                    out1.println("Opponent moved: " + move);
                    out1.println("Your turn!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }