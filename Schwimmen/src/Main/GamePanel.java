package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {

    //Screen Settings
    final int originalTileSize = 16; //16x16 Tiles
    public int scale;
    public int scaleneu;

    public int tileSize;
    public int maxScreenCol = 26;
    public int maxScreenRow = 18;
    public int screenWidth;
    public int screenHeight;

    BufferedImage tempScreen;
    Graphics2D g2;

    //FPS
    int FPS = 60;

    //System
    public KeyHandler keyH = new KeyHandler(this);
    public MouseHandler mouseH = new MouseHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public UI ui = new UI(this);
    public Spieler spieler = new Spieler(this);
    public Bot bot = new Bot(this);
    public Karten karten = new Karten(this);
    public Timer timer = new Timer(this);
    Config config = new Config(this);
    Thread gameThread;


    // game state
    public int gameState;
    public final int titleState = 0;
    public final int backroundState = 1;
    public final int optionsState = 2;
    public final int regelState = 3;
    public final int startState = 4;
    public final int spielerState = 5;
    public final int steuerung = 6;
    public final int botState = 7;
    public final int schwierigkeitState = 8;
    public final int endState = 9;
    public final int zwischenState = 10;
    public final int warningState = 11;
    double nextDrawTime;
    double remainingTime;

    public GamePanel() {

        config.loadConfig();

        tileSize = originalTileSize * scale; //64x64 Tiles
        screenWidth = tileSize * maxScreenCol; // 1408 pixels
        screenHeight = tileSize * maxScreenRow; // 1024 pixels


        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.setFocusable(true);
    }


    public void setupGame() {



        gameState = titleState;

        ui.herzenZurücksetzen();

        karten.sortieren();

        playMusic(7);

        tempScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        drawToTempScreen();
        drawToScreen();

    }


    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {

        double drawIntervall = 1000000000/FPS; //0.01666 seconds


            nextDrawTime = System.nanoTime() + drawIntervall;


         while (gameThread != null) {

             update();


             drawToTempScreen(); //draw buffered image (not on screen)
             drawToScreen(); // draw on screen (resized)

             try {

                     remainingTime = nextDrawTime - System.nanoTime();
                     remainingTime = remainingTime / 1000000;


                 if (remainingTime < 0) {
                     remainingTime = 0;
                 }

                 Thread.sleep((long)remainingTime);

                 nextDrawTime = nextDrawTime + drawIntervall;


             } catch (InterruptedException e) {

                 throw new RuntimeException(e);
             }

         }

    }


    public void update() {

        if (gameState == spielerState) {

            spieler.run();

        }

        if (gameState == botState) {


                bot.computer();

        }
    }


    public void drawToTempScreen() {

        if (gameState == titleState) {

            ui.draw(g2);

        }

        else {

            ui.draw(g2);
            mouseH.draw(g2);
            spieler.draw(g2);
            bot.draw(g2);
            karten.draw(g2);
            keyH.draw(g2);

        }
    }


    public void drawToScreen() {

        Graphics g = getGraphics();

        g.drawImage(tempScreen,0,0,screenWidth,screenHeight,null);
        g.dispose();

    }


    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();

    }


    public void stopMusic(int i) {

        music.stop();

    }


    public void playSE(int i) {

        se.setFile(i);
        se.play();

    }


    public void checkScale() {

        switch (ui.pixelscale) {
            case 0: scaleneu = 2; break;
            case 1: scaleneu = 2; break;
            case 2: scaleneu = 3; break;
            case 3: scaleneu = 4; break;
            case 4: scaleneu = 5; break;
            case 5: scaleneu = 6; break;
        }
    }

}
