package Main;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    public static JFrame window;


    public static void main (String[] args ) {


        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Schwimmen");

        GamePanel gamePanel = new GamePanel();

        window.add(gamePanel);

        gamePanel.config.loadConfig();

        window.pack();

        window.setLocale(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

}