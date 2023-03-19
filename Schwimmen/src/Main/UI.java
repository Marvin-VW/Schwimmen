package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    GamePanel gp;
    Font MaruMonica, arial;
    Graphics2D g2;
    BufferedImage image = null;
    Rectangle mittelinks,mittemitte,mitterechts,spielerlinks,spielermitte,spielerrechts,sound,settings,schieben,klopfen;


    public int commandNum = 0;
    boolean ersterStart = true;
    boolean regeln = false;
    boolean title = true;
    boolean mute = false;
    boolean gezeichnet = false;
    public int pixelscale;
    boolean leicht, mittel, schwer = false;
    boolean update = false;
    boolean sperre = false;
    boolean endsperre = false;
    double punktebot1,punktebot2,punktebot3,punktespieler;
    String gewinner,zwischenstand,zwischenstandgrund = "";
    int [][] herzen = new int[4][4];
    int dialougestate = 0;
    String[] texte = new String[12];
    boolean pause = false;
    int seite = 0;
    boolean plus = false;

    //
    public UI(GamePanel gp) {

        this.gp = gp;
        arial = new Font("Arial",Font.PLAIN,gp.scale*10);

        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            MaruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    //
    public void draw (Graphics2D g2) {

        this.g2 = g2;


        //titlestate
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }


        //ersterstart
        if (gp.gameState == gp.startState) {
            drawGameScreen();
            gameStartScreen();
        }

        if (gp.gameState == gp.schwierigkeitState) {

            gameStartScreenSchwierigkeit();

        }

        //backround
        if (gp.gameState == gp.backroundState) {
            drawGameScreen();
            System.out.println("Backround malen");
        }


        //optionsstae
        if (gp.gameState == gp.optionsState) {

                drawOptionsScreen();

        }


        //regelstate
        if (gp.gameState == gp.regelState) {

            drawRegelScreen();

        }

        //regelstate
        if (gp.gameState == gp.endState) {

            drawEndScreen(0);

        }

        //spielanleitung
        if (gp.gameState == gp.steuerung) {

            steuerung();

        }

        //warning
        if (gp.gameState == gp.warningState) {

            warning();

        }

    }


    //extra fenster malen
    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0,0,0, 250);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);

        x = x + gp.tileSize;
        y = y + gp.tileSize;

    }


    //mittlere koordinate für text
    public int getXforCenteredText (String text) {

        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;

        return x;
    }


    //titleScreen
    public void drawTitleScreen() {

            //titlename
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            g2.setFont(MaruMonica);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.scale * 50F));
            String text = "Schwimmen";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 5;


            Color c = new Color(0, 0, 0, 170);
            g2.setColor(c);
            g2.fillRoundRect(gp.tileSize * 7 + 21, gp.tileSize * 6 + 5, (int) (gp.tileSize * 3.5), gp.tileSize * 5, 25, 25);
            image = bildmalen("queen_of_diamonds2");
            g2.drawImage(image, gp.tileSize * 7 + 16, gp.tileSize * 6, (int) (gp.tileSize * 3.5), gp.tileSize * 5, null);



            c = new Color(0, 0, 0, 170);
            g2.setColor(c);
            g2.fillRoundRect(gp.tileSize * 11 + 21, gp.tileSize * 6 + 5, (int) (gp.tileSize * 3.5), gp.tileSize * 5, 25, 25);
            image = bildmalen("ace_of_diamonds");
            g2.drawImage(image, gp.tileSize * 11 + 16, gp.tileSize * 6, (int) (gp.tileSize * 3.5), gp.tileSize * 5, null);



            c = new Color(0, 0, 0, 170);
            g2.setColor(c);
            g2.fillRoundRect(gp.tileSize * 15 + 21, gp.tileSize * 6 + 5, (int) (gp.tileSize * 3.5), gp.tileSize * 5, 25, 25);
            image = bildmalen("king_of_diamonds2");
            g2.drawImage(image, gp.tileSize * 15 + 16, gp.tileSize * 6, (int) (gp.tileSize * 3.5), gp.tileSize * 5, null);




            //shadow
            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 5, y + 5);

            //main color
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);


            y = y + (gp.tileSize * 2);

            //menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.scale * 17.5F));

            text = "PLAY";
            x = getXforCenteredText(text);
            y = y + (int) (gp.tileSize * 6.5);

            c = new Color(0, 0, 0, 170);
            g2.setColor(c);
            g2.drawString(text, x + 5, y + 5);

            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            if (gp.gameState == gp.titleState) {

                if (commandNum == 0 && update == false) {
                    g2.drawString(">", (int) (x - gp.tileSize*0.6), y);
                }

            }


            text = "OPTIONS";
            x = getXforCenteredText(text);
            y = (int) (y + (gp.tileSize * 1.1));

            c = new Color(0, 0, 0, 170);
            g2.setColor(c);
            g2.drawString(text, x + 5, y + 5);

            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            if (gp.gameState == gp.titleState) {

                if (commandNum == 1 && update == false) {
                    g2.drawString(">", (int) (x - gp.tileSize*0.6), y);
                }
            }


            text = "LOAD CONFIG";
            x = getXforCenteredText(text);
            y = (int) (y + (gp.tileSize * 1.1));

            c = new Color(0, 0, 0, 170);
            g2.setColor(c);
            g2.drawString(text, x + 5, y + 5);

            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            if (gp.gameState == gp.titleState) {

            if (commandNum == 2 && update == false) {
                    g2.drawString(">", (int) (x - gp.tileSize*0.6), y);
                }
            }


            text = "QUIT";
            x = getXforCenteredText(text);
            y = (int) (y + (gp.tileSize * 1.1));

            c = new Color(0, 0, 0, 170);
            g2.setColor(c);
            g2.drawString(text, x + 5, y + 5);

            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            if (gp.gameState == gp.titleState) {

                if (commandNum == 3 && update == false) {
                    g2.drawString(">", (int) (x - gp.tileSize*0.6), y);
                }

            }

    }


    //startscreen "game starten"
    public void gameStartScreen()  {

        Color c = new Color(0,0,0, 248);
        g2.setColor(c);
        g2.fillRoundRect(gp.tileSize*6,gp.tileSize*5,gp.tileSize*14,gp.tileSize*8,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(gp.tileSize*6+5,gp.tileSize*5+5,gp.tileSize*14-10,gp.tileSize*8-10,25,25);


        //Texte
        g2.setFont(MaruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*10F));
        String text = "Willkommen im Spiel Schwimmen!";
        int x = getXforCenteredText(text);
        double y = gp.tileSize * 6.5;

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        y = y+gp.tileSize;

        //
        text = "Bitte wähle eine untenstehende Möglichkeit.";
        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        y = y+gp.tileSize*1.8;

        //text spielanleitung
        text = "Steuerung";
        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        if (commandNum == 0) {
            g2.drawString(">", x-25, (float) y);
        }


        y = y+gp.tileSize;

        //text regeln
        text = "Regeln";
        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        if (commandNum == 1) {
            g2.drawString(">", x-25, (float) y);
        }


        y = y+gp.tileSize*1.5;

        //text spielanleitung
        text = "Start";
        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        if (commandNum == 2) {
            g2.drawString(">", x-25, (float) y);
        }



    }


    //Schwierigkeitsgrad
    public void gameStartScreenSchwierigkeit() {

        System.out.println(leicht);
        System.out.println(mittel);
        System.out.println(schwer);


       drawGameScreen();


       Color c = new Color(0,0,0, 248);
       g2.setColor(c);
       g2.fillRoundRect(gp.tileSize*6,gp.tileSize*5,gp.tileSize*14,gp.tileSize*8,35,35);

       c = new Color(255,255,255);
       g2.setColor(c);
       g2.setStroke(new BasicStroke(5));
       g2.drawRoundRect(gp.tileSize*6+5,gp.tileSize*5+5,gp.tileSize*14-10,gp.tileSize*8-10,25,25);

       if (gezeichnet == false) {

               if (gp.ui.leicht == true) {

                   gp.ui.commandNum = 0;

               }
               if (gp.ui.mittel == true) {

                   gp.ui.commandNum = 1;

               }
               if (gp.ui.schwer == true) {

                   gp.ui.commandNum = 2;

               }

           gezeichnet = true;

       }

       if (leicht == true) {

           g2.drawRoundRect((int) (gp.tileSize*7.9), (int) (gp.tileSize * 7.3), (int) (gp.tileSize*1.8), gp.tileSize, 25, 25);

       }

       if (mittel == true) {

           g2.drawRoundRect( (int) (gp.tileSize*12.2), (int) (gp.tileSize * 7.3), (int) (gp.tileSize*1.7), gp.tileSize, 25, 25);


       }

       if (schwer == true) {

           g2.drawRoundRect((int) (gp.tileSize*15.9), (int) (gp.tileSize * 7.3), (int) (gp.tileSize*2.2), gp.tileSize, 25, 25);


       }


        //Texte
        g2.setFont(MaruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*10F));
        String text = "Bitte wähle einen Schwierigkeitsgrad";
        int x = getXforCenteredText(text);
        double y = gp.tileSize * 6.5;

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        y = y+gp.tileSize*1.5;

        //
        text = "Leicht";
        x = gp.tileSize*8;

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        if (commandNum == 0) {

            g2.drawString(">", x-25, (float) y);

            y = y+ gp.tileSize*1.2;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*6.5F));
            text = "Geeignet für Anfänger, um die Prinzipien des Spiels kennen zu lernen";
            x = getXforCenteredText(text);
            g2.drawString(text,x+5, (float) (y+5));

            y = y + (gp.tileSize*0.9);
            text = "Bots handeln mit wenig Intelligenz und ihre Karten sind sichtbar.";
            x = getXforCenteredText(text);
            g2.drawString(text,x+5, (float) (y+5));

            g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*10F));
            y = gp.tileSize*8;

        }

        if (leicht == true) {

            y = y+ gp.tileSize*1.2;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*6.5F));
            text = "Geeignet für Anfänger, um die Prinzipien des Spiels kennen zu lernen";
            x = getXforCenteredText(text);
            g2.drawString(text,x+5, (float) (y+5));

            y = y + (gp.tileSize*0.9);
            text = "Bots handeln mit wenig Intelligenz und ihre Karten sind sichtbar.";
            x = getXforCenteredText(text);
            g2.drawString(text,x+5, (float) (y+5));

            g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*10F));
            y = gp.tileSize*8;

        }


        //text spielanleitung
        text = "Mittel";
        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        if (commandNum == 1) {
            g2.drawString(">", x-25, (float) y);

            y = y+ gp.tileSize*1.2;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*6.5F));
            text = "Geeignet für Fortgeschrittene, für eine größere Herausforderung.";
            x = getXforCenteredText(text);
            g2.drawString(text,x+5, (float) (y+5));

            y = y + (gp.tileSize*0.9);
            text = "Bots handeln mit höherer Intelligenz und ihre Karten sind nicht sichtbar.";
            x = getXforCenteredText(text);
            g2.drawString(text,x+5, (float) (y+5));

            g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*10F));
            y = gp.tileSize*8;

        }

        if (mittel == true) {

            y = y+ gp.tileSize*1.2;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*6.5F));
            text = "Geeignet für Fortgeschrittene, für eine größere Herausforderung.";
            x = getXforCenteredText(text);
            g2.drawString(text,x+5, (float) (y+5));

            y = y + (gp.tileSize*0.9);
            text = "Bots handeln mit höherer Intelligenz und ihre Karten sind nicht sichtbar.";
            x = getXforCenteredText(text);
            g2.drawString(text,x+5, (float) (y+5));

            g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*10F));
            y = gp.tileSize*8;

        }

        x = gp.tileSize* 16;

        //text regeln
        text = "Schwer";

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        if (commandNum == 2) {
            g2.drawString(">", x-25, (float) y);

            y = y+ gp.tileSize*1.2;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*6.5F));
            text = "Geeignet für Profis, welche eine Herausforderung suchen.";
            x = getXforCenteredText(text);
            g2.drawString(text,x+5, (float) (y+5));

            y = y + (gp.tileSize*0.9);
            text = "Bots handeln mit vollständiger Intelligenz und ihre Karten sind nicht sichtbar.";
            x = getXforCenteredText(text);
            g2.drawString(text,x+5, (float) (y+5));

            g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*10F));
            y = gp.tileSize*8;
        }

        if (schwer == true) {

            y = y+ gp.tileSize*1.2;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*6.5F));
            text = "Geeignet für Profis, welche eine Herausforderung suchen.";
            x = getXforCenteredText(text);
            g2.drawString(text,x+5, (float) (y+5));

            y = y + (gp.tileSize*0.9);
            text = "Bots handeln mit vollständiger Intelligenz und ihre Karten sind nicht sichtbar.";
            x = getXforCenteredText(text);
            g2.drawString(text,x+5, (float) (y+5));

            g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*10F));
            y = gp.tileSize*8;
        }


        y = y+gp.tileSize*4;

        //text spielanleitung
        text = "Spiel starten";
        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        if (commandNum == 3) {
            g2.drawString(">", x-25, (float) y);
        }


    }


    //steurung
    public void steuerung() {


        if (gp.ui.title == true) {

            update = true;
            drawTitleScreen();
            update = false;

        } else {

            drawGameScreen();

        }


        g2.setColor(Color.white);
        g2.setFont(MaruMonica);

        //sub window
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize*2;
        int frameWidth =  gp.tileSize*14;
        int frameHeight = gp.tileSize* 13;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);


        //text pause
        g2.setFont(MaruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*12F));
        String text = "Steuerung";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 4;

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5,y+5);

        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);

        int textY = 0;
        int textX = 0;

        textX = frameX +gp.tileSize;
        textY = frameY + gp.tileSize*3;

        g2.setColor(Color.white);
        g2.setFont(MaruMonica);
        g2.setFont(g2.getFont().deriveFont(gp.scale*12F));


        //scale
        textY = textY + gp.tileSize;

        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(textX+gp.tileSize*2-6, textY-36, gp.tileSize, gp.tileSize, 10, 10);

        g2.drawRoundRect((int) (textX+gp.tileSize*3.23), textY-36, gp.tileSize, gp.tileSize, 10, 10);

        g2.drawRoundRect((int) (textX+gp.tileSize*4.66), textY-36, gp.tileSize, gp.tileSize, 10, 10);

        g2.drawRoundRect((int) (textX+gp.tileSize*6.09), textY-36, gp.tileSize, gp.tileSize, 10, 10);

        g2.drawRoundRect((int) (textX+gp.tileSize*8.88), textY-36, (int) (gp.tileSize*1.6), gp.tileSize, 10, 10);



        g2.drawString("-" ,textX-18,textY);
        g2.drawString("Menü:   W     A      S      D    und   Enter",textX,textY);


        textY = textY + gp.tileSize;

        textY = textY + gp.tileSize;
        g2.drawString("-" ,textX-18,textY);
        g2.drawString("Karten tauschen: Deine Karte und die " ,textX,textY);


        textY = textY + gp.tileSize;
        g2.drawString("in der Mitte anklicken." ,textX,textY);


        textY = textY + gp.tileSize;

        textY = textY + gp.tileSize;
        g2.drawString("-" ,textX-18,textY);
        g2.drawString("Um alle zu Tauschen: Alle auswählen und alle",textX,textY);

        textY = textY + gp.tileSize;
        g2.drawString("in der Mitte anklicken.",textX,textY);



        textY = (int) (textY + gp.tileSize*2.5);
        g2.drawString("Back",textX,textY);
        if (commandNum == 0) {
            g2.drawString(">", textX-25,textY);
        }


    }


    //playscreen
    public void drawGameScreen() {

        g2.clearRect(0,0,gp.tileSize*20,gp.tileSize*12);

        image = bildmalen("tisch");
        g2.drawImage(image, 0, 0, gp.scale*470, gp.scale*306, null);

        image = bildmalen("tisch2");
        g2.drawImage(image, gp.tileSize*6,(int) (gp.tileSize * 5.2),gp.scale*222,gp.scale*123, null);

        //spieler oben

        image = bildmalen("00");
        g2.drawImage(image, gp.tileSize*9,gp.tileSize*1,gp.tileSize*2,gp.tileSize*3, null);

        image = bildmalen("00");
        g2.drawImage(image, gp.tileSize*12,gp.tileSize*1,gp.tileSize*2,gp.tileSize*3, null);

        image = bildmalen("00");
        g2.drawImage(image, gp.tileSize*15,gp.tileSize*1,gp.tileSize*2,gp.tileSize*3, null);

        //spieler links

        image = bildmalen("00");
        g2.drawImage(image, gp.tileSize*3,gp.tileSize*3,gp.tileSize*2,gp.tileSize*3, null);

        image = bildmalen("00");
        g2.drawImage(image, gp.tileSize*3,gp.tileSize*7,gp.tileSize*2,gp.tileSize*3, null);

        image = bildmalen("00");
        g2.drawImage(image, gp.tileSize*3,gp.tileSize*11,gp.tileSize*2,gp.tileSize*3, null);

        //spieler rechts


        image = bildmalen("00");
        g2.drawImage(image, gp.tileSize*21,gp.tileSize*3,gp.tileSize*2,gp.tileSize*3, null);

        image = bildmalen("00");
        g2.drawImage(image, gp.tileSize*21,gp.tileSize*7,gp.tileSize*2,gp.tileSize*3, null);

        image = bildmalen("00");
        g2.drawImage(image, gp.tileSize*21,gp.tileSize*11,gp.tileSize*2,gp.tileSize*3, null);


        //spieler unten

        image = bildmalen("00");
        g2.drawImage(image, gp.tileSize*9,gp.tileSize*13,gp.tileSize*2,gp.tileSize*3, null);
        spielerlinks = new Rectangle(gp.tileSize*9, gp.tileSize*13, gp.tileSize*2, gp.tileSize*3);

        image = bildmalen("00");
        g2.drawImage(image, gp.tileSize*12,gp.tileSize*13,gp.tileSize*2,gp.tileSize*3, null);
        spielermitte = new Rectangle(gp.tileSize*12, gp.tileSize*13, gp.tileSize*2, gp.tileSize*3);

        image = bildmalen("00");
        g2.drawImage(image, gp.tileSize*15,gp.tileSize*13,gp.tileSize*2,gp.tileSize*3, null);
        spielerrechts = new Rectangle(gp.tileSize*15, gp.tileSize*13, gp.tileSize*2, gp.tileSize*3);

        //mitte
        image = bildmalen("00");
        g2.drawImage(image, gp.tileSize*9,(int) (gp.tileSize*7.64),gp.tileSize*2,gp.tileSize*3, null);
        mittelinks = new Rectangle(gp.tileSize*9, (int) (gp.tileSize*7.64), gp.tileSize*2, gp.tileSize*3);

        image = bildmalen("00");
        g2.drawImage(image, gp.tileSize*12,(int) (gp.tileSize*7.64),gp.tileSize*2,gp.tileSize*3, null);
        mittemitte = new Rectangle(gp.tileSize*12, (int) (gp.tileSize*7.64), gp.tileSize*2, gp.tileSize*3);

        image = bildmalen("00");
        g2.drawImage(image, gp.tileSize*15,(int) (gp.tileSize*7.64),gp.tileSize*2,gp.tileSize*3, null);
        mitterechts = new Rectangle(gp.tileSize*15, (int) (gp.tileSize*7.64), gp.tileSize*2, gp.tileSize*3);


        //einstellung rectangle
        settings = new Rectangle((gp.tileSize*24), (int) (gp.tileSize*0.8), (int)(gp.tileSize * 2.5), (int)(gp.tileSize * 1.5));

        //sound rectangle
        sound = new Rectangle((int) (gp.tileSize*0.8), (int) (gp.tileSize*0.8), (int) (gp.tileSize * 2.5), (int)(gp.tileSize * 1.5));

        //klopfen rectangle
        klopfen = new Rectangle((int) (gp.tileSize * 5.5), (int) (gp.tileSize * 15.5), (int) (gp.tileSize * 2.5), (int)(gp.tileSize * 1.5));

        //schieben rectangle
        schieben = new Rectangle((gp.tileSize * 18), (int) (gp.tileSize * 15.5), (int) (gp.tileSize * 2.5), (int)(gp.tileSize * 1.5));


        //einstellung
        image = bildmalen("setting");
        g2.drawImage(image, (gp.tileSize * 24), (int) (gp.tileSize * 0.8), gp.tileSize, gp.tileSize, null);

        //sound
        if (gp.ui.mute == false) {
            image = bildmalen("notmute");
            g2.drawImage(image, (int) (gp.tileSize * 0.8), (int) (gp.tileSize * 0.8), gp.tileSize, gp.tileSize, null);

        } else {
            image = bildmalen("mute");
            g2.drawImage(image, (int) (gp.tileSize * 0.8), (int) (gp.tileSize * 0.8), gp.tileSize, gp.tileSize, null);
        }


        //text spieler
        g2.setFont(MaruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*12F));
        String text = "Spieler";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 17;

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5,y+5);

        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);


        //text spieler links
        text = "Bot 1";
        x = gp.tileSize * 1;
        y = gp.tileSize * 9;

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5,y+5);

        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);



        //text spieler oben
        text = "Bot 2";
        x = getXforCenteredText(text);
        y = gp.tileSize * 5;

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5,y+5);

        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);



        //text spieler rechts
        text = "Bot 3";
        x = (int) (gp.tileSize * 23.5);
        y = gp.tileSize * 9;

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5,y+5);

        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);


    }


    //richtiger playscreen
    public void backround() {

        //provisorisch
        g2.clearRect(0, 0, gp.tileSize * 20, gp.tileSize * 12);
        BufferedImage image = null;

        image = bildmalen("tisch");
        g2.drawImage(image, 0, 0, gp.scale * 470, gp.scale * 306, null);

        image = bildmalen("tisch2");
        g2.drawImage(image, gp.tileSize * 6, (int) (gp.tileSize * 5.2), gp.scale * 222, gp.scale * 123, null);


        //einstellung
        image = bildmalen("setting");
        g2.drawImage(image, (gp.tileSize * 24), (int) (gp.tileSize * 0.8), gp.tileSize, gp.tileSize, null);

        //sound
        if (gp.ui.mute == false) {
            image = bildmalen("notmute");
            g2.drawImage(image, (int) (gp.tileSize * 0.8), (int) (gp.tileSize * 0.8), gp.tileSize, gp.tileSize, null);

        } else {
            image = bildmalen("mute");
            g2.drawImage(image, (int) (gp.tileSize * 0.8), (int) (gp.tileSize * 0.8), gp.tileSize, gp.tileSize, null);
        }


        int x;
        int y;
        int width;
        int height;
        String text = "";


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.scale * 8F));
        Color c = new Color(0, 0, 0, 0);
        g2.setColor(c);

        g2.drawString("Schieben", (int) (gp.tileSize*0.85), (int) (gp.tileSize*10.5));

        text = "Schieben";
        g2.drawString("Schieben", (int) (getXforCenteredText(text)+gp.tileSize*2.3), gp.tileSize*5);

        g2.drawString("Schieben",(int) (gp.tileSize * 23.4),(int) (gp.tileSize*10.5));





        g2.drawString("Klopfen", (int) (gp.tileSize), (int) (gp.tileSize*8));

        text = "Klopfen";
        g2.drawString("Klopfen", (int) (getXforCenteredText(text)-gp.tileSize*2.1), gp.tileSize*5);

        g2.drawString("Klopfen",(int) (gp.tileSize * 23.55),(int) (gp.tileSize*8));



        if (gp.gameState == gp.spielerState) {

            //klopfen
            x = (int) (gp.tileSize * 5.5);
            y = (int) (gp.tileSize * 15.5);
            width = (int) (gp.tileSize * 2.5);
            height = (int) (gp.tileSize * 1.5);

            //kern (backround)
            c = new Color(0, 0, 0, 170);
            g2.setColor(c);
            g2.fillRoundRect(x, y, width, height, 25, 25);

            //text
            g2.setFont(MaruMonica);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.scale * 9F));
            c = new Color(255, 255, 255, 200);
            g2.setColor(c);
            text = "Klopfen";
            g2.drawString(text, (int) (gp.tileSize * 5.95), (int) (gp.tileSize * 16.45));


            //schieben
            x = (int) (gp.tileSize * 18);
            y = (int) (gp.tileSize * 15.5);
            width = (int) (gp.tileSize * 2.5);
            height = (int) (gp.tileSize * 1.5);

            //kern (backround)
            c = new Color(0, 0, 0, 170);
            g2.setColor(c);
            g2.fillRoundRect(x, y, width, height, 25, 25);

            //text
            g2.setFont(MaruMonica);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.scale * 9F));
            c = new Color(255, 255, 255, 200);
            g2.setColor(c);
            text = "Schieben";
            g2.drawString(text, (int) (gp.tileSize * 18.28), (int) (gp.tileSize * 16.45));

        }


        //text spieler
        g2.setFont(MaruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.scale * 12F));
        text = "Spieler";
        x = getXforCenteredText(text);
        y = gp.tileSize * 17;


        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        if (herzen[3][0] == 0) {

            image = bildmalen("herz");

        } else {

            image = bildmalen("herzleer");

        }
        g2.drawImage(image, (int) (gp.tileSize * 12.2), y + (int) (gp.tileSize * 0.2), (int) (gp.tileSize * 0.5), (int) (gp.tileSize * 0.5), null);


        if (herzen[3][1] == 0) {

            image = bildmalen("herz");

        } else {

            image = bildmalen("herzleer");

        }
        g2.drawImage(image, (int) (gp.tileSize * 12.75), y + (int) (gp.tileSize * 0.2), (int) (gp.tileSize * 0.5), (int) (gp.tileSize * 0.5), null);


        if (herzen[3][2] == 0) {

            image = bildmalen("herz");

        } else {

            image = bildmalen("herzleer");

        }
        g2.drawImage(image, (int) (gp.tileSize * 13.3), y + (int) (gp.tileSize * 0.2), (int) (gp.tileSize * 0.5), (int) (gp.tileSize * 0.5), null);


        //text spieler links
        if (gp.bot.bot1raus == true) {

            float opacity = (float) 0.3;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        }
        text = "Bot 1";
        x = gp.tileSize * 1;
        y = gp.tileSize * 9;

        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        if (herzen[0][0] == 0) {

            image = bildmalen("herz");

        } else {

            image = bildmalen("herzleer");

        }
        g2.drawImage(image, (int) (gp.tileSize * 0.95), y + (int) (gp.tileSize * 0.2), (int) (gp.tileSize * 0.5), (int) (gp.tileSize * 0.5), null);


        if (herzen[0][1] == 0) {

            image = bildmalen("herz");

        } else {

            image = bildmalen("herzleer");

        }
        g2.drawImage(image, (int) (gp.tileSize * 1.5), y + (int) (gp.tileSize * 0.2), (int) (gp.tileSize * 0.5), (int) (gp.tileSize * 0.5), null);


        if (herzen[0][2] == 0) {

            image = bildmalen("herz");

        } else {

            image = bildmalen("herzleer");

        }
        g2.drawImage(image, (int) (gp.tileSize * 2.05), y + (int) (gp.tileSize * 0.2), (int) (gp.tileSize * 0.5), (int) (gp.tileSize * 0.5), null);


        float opacity = (float) 1;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));


        //text spieler oben
        if (gp.bot.bot2raus == true) {

            opacity = (float) 0.3;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        }

        text = "Bot 2";
        x = getXforCenteredText(text);
        y = gp.tileSize * 5;

        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);


        if (herzen[1][0] == 0) {

            image = bildmalen("herz");

        } else {

            image = bildmalen("herzleer");

        }
        g2.drawImage(image, (int) (gp.tileSize * 12.2), y + (int) (gp.tileSize * 0.2), (int) (gp.tileSize * 0.5), (int) (gp.tileSize * 0.5), null);


        if (herzen[1][1] == 0) {

            image = bildmalen("herz");

        } else {

            image = bildmalen("herzleer");

        }
        g2.drawImage(image, (int) (gp.tileSize * 12.75), y + (int) (gp.tileSize * 0.2), (int) (gp.tileSize * 0.5), (int) (gp.tileSize * 0.5), null);


        if (herzen[1][2] == 0) {

            image = bildmalen("herz");

        } else {

            image = bildmalen("herzleer");

        }
        g2.drawImage(image, (int) (gp.tileSize * 13.3), y + (int) (gp.tileSize * 0.2), (int) (gp.tileSize * 0.5), (int) (gp.tileSize * 0.5), null);

        opacity = (float) 1;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));



        //text spieler rechts
        if (gp.bot.bot3raus == true) {

            opacity = (float) 0.3;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        }
        text = "Bot 3";
        x = (int) (gp.tileSize * 23.5);
        y = gp.tileSize * 9;

        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);


        if (herzen[2][0] == 0) {

            image = bildmalen("herz");

        } else {

            image = bildmalen("herzleer");

        }
        g2.drawImage(image, (int) (gp.tileSize * 23.45), y + (int) (gp.tileSize * 0.2), (int) (gp.tileSize * 0.5), (int) (gp.tileSize * 0.5), null);


        if (herzen[2][1] == 0) {

            image = bildmalen("herz");

        } else {

            image = bildmalen("herzleer");

        }
        g2.drawImage(image, (int) (gp.tileSize * 24), y + (int) (gp.tileSize * 0.2), (int) (gp.tileSize * 0.5), (int) (gp.tileSize * 0.5), null);


        if (herzen[2][2] == 0) {

            image = bildmalen("herz");

        } else {

            image = bildmalen("herzleer");

        }
        g2.drawImage(image, (int) (gp.tileSize * 24.55), y + (int) (gp.tileSize * 0.2), (int) (gp.tileSize * 0.5), (int) (gp.tileSize * 0.5), null);

        opacity = (float) 1;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

    }


    //optionsscreen
    public void drawOptionsScreen() {


        if (gp.ui.title == true) {

            update = true;
            drawTitleScreen();
            update = false;

        } else {
            backround();
            gp.karten.kartenzuteilen();
        }


        g2.setColor(Color.white);
        g2.setFont(MaruMonica);
        g2.setFont(g2.getFont().deriveFont(gp.scale*8F));


        //sub window
        int frameX = gp.tileSize*8;
        int frameY = gp.tileSize*2;
        int frameWidth =  gp.tileSize*10;
        int frameHeight = gp.tileSize* 12;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);


        //text pause
        g2.setFont(MaruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*15F));
        String text = "Pause";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 4;

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5,y+5);

        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);

        drawRegelOptionen(frameX,frameY);
    }


    //optionen
    public void drawRegelOptionen(int frameX, int frameY) {

        int textX;
        int textY;


        textX = frameX +gp.tileSize;
        textY = frameY + gp.tileSize*3;


        //scale
        textY = textY + gp.tileSize;
        g2.drawString("Scale",textX,textY);
        if (commandNum == 0) {
            g2.drawString(">", textX-25,textY);
        }


        //Music
        textY = textY + gp.tileSize;
        g2.drawString("Musik",textX,textY);
        if (commandNum == 1) {
            g2.drawString(">", textX-25,textY);
        }

        //Sound
        textY = textY + gp.tileSize;
        g2.drawString("Sound Effekte",textX,textY);
        if (commandNum == 2) {
            g2.drawString(">", textX-25,textY);
        }

        //Regeln
        textY = textY + gp.tileSize;
        g2.drawString("Regeln",textX,textY);
        if (commandNum == 3) {
            g2.drawString(">", textX-25,textY);
        }


        if (title == true)  {


            //EndGame
            textY = textY + gp.tileSize*4;
            g2.drawString("Back",textX,textY);
            if (commandNum == 4) {
                g2.drawString(">", textX-25,textY);
            }


        } else {

            //Save Game
            textY = textY + gp.tileSize * 3;
            g2.drawString("Save Game", textX, textY);
            if (commandNum == 4) {
                g2.drawString(">", textX - 25, textY);

            }

            //EndGame
            textY = (int) (textY + gp.tileSize*1.2);
            g2.drawString("End Game", textX, textY);
            if (commandNum == 5) {
                g2.drawString(">", textX - 25, textY);

            }
        }


        textX = frameX +gp.tileSize*7;
        textY = frameY +gp.tileSize*2+(gp.scale*7);

        //scale
        textY = textY+gp.tileSize;
        g2.drawRect(textX,textY,gp.scale*30,gp.scale*6); //120/5 = 24
        int scale = (gp.scale*6)* pixelscale;
        g2.fillRect(textX,textY,scale,(gp.scale*6));


        //Music volume
        textY = textY+gp.tileSize;
        g2.drawRect(textX,textY,gp.scale*30,gp.scale*6); //120/5 = 24
        int volumeWidth = (gp.scale*6)* gp.music.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,(gp.scale*6));

        //SE volume
        textY = textY+gp.tileSize;
        g2.drawRect(textX,textY,gp.scale*30,gp.scale*6);
        volumeWidth = (gp.scale*6)* gp.se.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,(gp.scale*6));

    }


    //regelscreen
    public void drawRegelScreen() {

        g2.clearRect(0, 0, gp.tileSize * 20, gp.tileSize * 12);

        if (title == true) {

            drawTitleScreen();

        } else {

            if (ersterStart == true) {

                drawGameScreen();

            } else {

                backround();
                gp.karten.kartenzuteilen();

            }

        }

        //sub window
        int frameX = (int) (gp.tileSize * 3);
        int frameY = (int) (gp.tileSize * 2.8);
        int frameWidth = gp.tileSize * 20;
        int frameHeight = gp.tileSize * 12;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        g2.setFont(MaruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.scale * 10F));

        int textX;
        int textY;

        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize;


        textY = textY + gp.tileSize;


        if (seite == 0) {
            g2.drawString("Regeln", textX, textY);
            textY = textY + gp.tileSize;

            g2.drawString("1. Ziel des Spiels ist es möglichst viele Punkte zu sammeln.", textX, textY);

            textY = textY + gp.tileSize;
            g2.drawString("2. Es gibt unterschiedliche Möglichkeitne Punkte zu sammeln: ", textX, textY);


            textY = textY + gp.tileSize;
            g2.drawString("-> entweder durch gleiche Farben ♠♣♥♦", textX, textY);

            textY = textY + gp.tileSize;
            g2.drawString("-> oder man sammelt 3x die gleiche Karte [7,7,7]", textX, textY);

            textY = textY + gp.tileSize;
            g2.drawString("\t- Die höchstmögliche Punktzahl ist 33 [ASS,ASS,ASS]", textX, textY);

            textY = textY + gp.tileSize;
            g2.drawString("- Alle Zahlenkarten haben ihren Wert, Bildkarten = 10 und Asse = 11", textX, textY);

            textY = textY + gp.tileSize;
            g2.drawString("\t- Die Beste Hand ist ein Ass und zwei Karten mit dem Wert 10. ", textX, textY);


        }

        if (seite ==1){
            g2.drawString("Regeln", textX, textY);

            textY = textY + gp.tileSize;
            g2.drawString("-Du kannst entweder eine Karte oder alle drei in der Mitte tauschen.", textX, textY);

            textY = textY + gp.tileSize;
            g2.drawString("-Drücke auf \"KLopfen\" wenn du mit deiner Punktzahl zufrieden bist. ", textX, textY);

            textY = textY + gp.tileSize;
            g2.drawString("", textX, textY);


            textY = textY + gp.tileSize;
            g2.drawString("Zusätzliche Regeln:", textX, textY);

            textY = textY + gp.tileSize;
            g2.drawString("-Wenn jeder Spieler einmal schiebt kommen neue Karten in die Mitte.", textX, textY);

            textY = textY + gp.tileSize;
            g2.drawString("-Wenn ein Spieler 3 Asse gesammelt hat, verliert jeder andere  Spieler ", textX, textY);

            textY = textY + gp.tileSize;
            g2.drawString(" ein Leben und das Spiel ist sofort vorbei.", textX, textY);

        }


        // Weitere Regeln
            textY = textY + gp.tileSize * 2;
                g2.drawString("Back", textX, textY);
        if (commandNum == 0) {

            g2.drawString(">", textX - 25, textY);

        }

        textX = (int) (textX + gp.tileSize * 13.4);

        //Weiter
        if (seite == 0) {

            g2.drawString("Weiter", textX, textY);

            if (commandNum == 1) {

                g2.drawString(">", textX - 25, textY);

            }
        }


        if (seite==1) {

            g2.drawString("Beenden", textX, textY);

            if (commandNum == 1) {

                g2.drawString(">", textX - 25, textY);

            }
        }


    }


    //endScreen
    public void drawEndScreen(int auslöser) {

        drawGameScreen();

        Color c = new Color(0,0,0, 248);
        g2.setColor(c);
        g2.fillRoundRect(gp.tileSize*6,gp.tileSize*(int)(3.5),gp.tileSize*14,gp.tileSize*11,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(gp.tileSize*6+5,gp.tileSize*(int)(3.5)+5,gp.tileSize*14-10,gp.tileSize*11-10,25,25);

        if (endsperre == false) {

            endsperre = true;

            if (auslöser == 0) {

                gewinner = "Die Bots gewinnen das Spiel! 👑";

            }
            if (auslöser == 3) {

                gewinner = "Du gewinnst das Spiel! 👑";
            }

        }


        //Texte
        g2.setFont(MaruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*10F));
        String text = "♠♥♦ Das Spiel ist zu Ende! ♠♥♦ ";

        int x = getXforCenteredText(text);
        double y = gp.tileSize * 4.1;

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        y = y + gp.tileSize*0.8;


        text = ""+gewinner;
        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        y = y+gp.tileSize*1.5;


        //
        text = "Punkte Bot 1: " + punktebot1;
        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, (float) (y + 5));

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, (float) y);


        y = y + gp.tileSize;

        //
        text = "Punkte Bot 2: " + punktebot2;
        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, (float) (y + 5));

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, (float) y);


        y = y + gp.tileSize;

        //
        text = "Punkte Bot 3: " + punktebot3;
        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, (float) (y + 5));

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, (float) y);


        y = y + gp.tileSize;

        //
        text = "Punkte Spieler: " + punktespieler;
        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, (float) (y + 5));

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, (float) y);



        y = y+ gp.tileSize*2;

        //
        text = "Erneut spielen";
        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        if (commandNum == 0) {

            g2.drawString(">", x-25, (float) y);

        }

        y = y+gp.tileSize;


        //
        text = "Schwierigkeit ändern";
        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        if (commandNum == 1) {

            g2.drawString(">", x-25, (float) y);

        }

        y = y+gp.tileSize;

        //
        text = "Beenden";
        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        if (commandNum == 2) {

            g2.drawString(">", x - 25, (float) y);

        }



    }


    //zwischenstand
    public void drawZwischenScreen(int spieler, int punkte) {

        g2.clearRect(0, 0, gp.tileSize * 20, gp.tileSize * 12);

        drawGameScreen();

        Color c = new Color(0,0,0, 248);
        g2.setColor(c);
        g2.fillRoundRect(gp.tileSize*6,gp.tileSize*(int)(3.5),gp.tileSize*14,gp.tileSize*11,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(gp.tileSize*6+5,gp.tileSize*(int)(3.5)+5,gp.tileSize*14-10,gp.tileSize*11-10,25,25);


        if (sperre == false) {

            gp.ui.commandNum = 0;

            sperre = true;
            punktebot1 = gp.bot.finaleKartenwerte(0);
            punktebot2 = gp.bot.finaleKartenwerte(1);
            punktebot3 = gp.bot.finaleKartenwerte(2);
            punktespieler = gp.spieler.spielerKartenwerte();


            if(gp.spieler.startpunkt == 3)
            {
                gp.spieler.startpunkt=0;
            }

            else
                {
                gp.spieler.startpunkt++;
            }

            double[] arr = {99,99,99,99};

            if (gp.bot.bot1raus == true) {

            } else {

                arr[0] = punktebot1;
            }
            if (gp.bot.bot2raus == true) {

            } else {

                arr[1] = punktebot2;
            }
            if (gp.bot.bot3raus == true) {

            } else {

                arr[2] = punktebot3;
            }
            if (gp.spieler.spielerraus == true) {

            } else {

                arr[3] = punktespieler;

            }

            double curMax = arr[0];

            for (int h = 1; h < arr.length; h++) {
                if (arr[h] != 99) {

                    if (arr[h] > curMax) {
                        curMax = arr[h];
                    }
                }
            }

            double curMin = arr[0];

            for (int j = 1; j < arr.length; j++) {
                if (arr[j] < curMin) {
                    curMin = arr[j];
                }
            }

            zwischenstand = "";

            if (punkte == 31) {

                if (curMin == punktebot1) {

                    if (zwischenstand.equals("")) {
                        zwischenstand = "Bot 1";
                    } else {
                        zwischenstand = zwischenstand + " und Bot 1";
                    }
                    herzabziehen(0);
                }
                if (curMin == punktebot2) {

                    if (zwischenstand.equals("")) {
                        zwischenstand = "Bot 2";
                    } else {
                        zwischenstand = zwischenstand + " und Bot 2";
                    }
                    herzabziehen(1);
                }
                if (curMin == punktebot3) {

                    if (zwischenstand.equals("")) {
                        zwischenstand = "Bot 3";
                    } else {
                        zwischenstand = zwischenstand + " und Bot 3";
                    }
                    herzabziehen(2);
                }
                if (curMin == punktespieler) {

                    if (zwischenstand.equals("")) {
                        zwischenstand = "Du";
                    } else {
                        zwischenstand = zwischenstand + " und Du";
                    }
                    herzabziehen(3);
                }

                if (zwischenstand.length() > 6) {
                    zwischenstand = zwischenstand + " verlieren ein Leben.";
                } else if (zwischenstand.equals("Du")){
                    zwischenstand = zwischenstand + " verlierst ein Leben.";
                } else {
                    zwischenstand = zwischenstand + " verliert ein Leben.";
                }






                if (spieler == 0) {

                    zwischenstandgrund = "Da Bot1 31 Punkte hat.";
                }
                if (spieler == 1) {

                    zwischenstandgrund = "Da Bot2 31 Punkte hat.";
                }
                if (spieler == 2) {

                    zwischenstandgrund = "Da Bot3 31 Punkte hat.";
                }
                if (spieler == 3) {

                    zwischenstandgrund = "Da Du 31 Punkte hast.";
                }

            }


            if (punkte == 33) {

                if (spieler == 0) {

                    zwischenstand = "Bot 1, Bot 2 und du verlieren ein Leben!";
                    zwischenstandgrund = "Da Bot 1 einen Blitz ausgelöst hat!";
                    herzabziehen(0);
                    herzabziehen(1);
                    herzabziehen(3);

                }
                if (spieler == 1) {

                    zwischenstand = "Bot 1, Bot 3 und du verlieren ein Leben!";
                    zwischenstandgrund = "Da Bot 2 einen Blitz ausgelöst hat!";
                    herzabziehen(0);
                    herzabziehen(2);
                    herzabziehen(3);

                }
                if (spieler == 2) {

                    zwischenstand = "Bot 1, Bot 2 und du verlieren ein Leben!";
                    zwischenstandgrund = "Da Bot 3 einen Blitz ausgelöst hat!";
                    herzabziehen(0);
                    herzabziehen(1);
                    herzabziehen(3);

                }
                if (spieler == 3) {

                    zwischenstand = "Alle Bots verlieren ein Leben!";
                    zwischenstandgrund = "Da Du einen Blitz ausgelöst hast!";
                    herzabziehen(0);
                    herzabziehen(1);
                    herzabziehen(2);

                }

            }


            if (punkte == 0)  {

                if (curMin == punktebot1) {

                    if (zwischenstand.equals("")) {
                        zwischenstand = "Bot 1";
                    } else {
                        zwischenstand = zwischenstand + " und Bot 1";
                    }
                    herzabziehen(0);

                }
                if (curMin == punktebot2) {

                    if (zwischenstand.equals("")) {
                        zwischenstand = "Bot 2";
                    } else {
                        zwischenstand = zwischenstand + " und Bot 2";
                    }
                    herzabziehen(1);

                }
                if (curMin == punktebot3) {

                    if (zwischenstand.equals("")) {
                        zwischenstand = "Bot 3";
                    } else {
                        zwischenstand = zwischenstand + " und Bot 3";
                    }
                    herzabziehen(2);

                }
                if (curMin == punktespieler) {

                    if (zwischenstand.equals("")) {
                        zwischenstand = "Du";
                    } else {
                        zwischenstand = zwischenstand + " und Du";
                    }
                    herzabziehen(3);

                }

                if (zwischenstand.length() > 6) {
                    zwischenstand = zwischenstand + " verlieren ein Leben.";
                } else if (zwischenstand.equals("Du")){
                    zwischenstand = zwischenstand + " verlierst ein Leben.";
                } else {
                    zwischenstand = zwischenstand + " verliert ein Leben.";
                }


                if (spieler == 0) {

                    zwischenstandgrund = "Da Bot 1 klopfte.";
                }
                if (spieler == 1) {

                    zwischenstandgrund = "Da Bot 2 klopfte.";
                }
                if (spieler == 2) {

                    zwischenstandgrund = "Da Bot 3 klopfte.";
                }
                if (spieler == 3) {

                    zwischenstandgrund = "Da Du geklopft hast.";
                }

            }

        }



        //Texte
        g2.setFont(MaruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*10F));
        String text = "Zwischenstand!";

        int x = getXforCenteredText(text);
        double y = gp.tileSize * 4.3;

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        y = y + gp.tileSize*0.8;


        text = zwischenstandgrund;

        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        y = y + gp.tileSize*0.8;


        text = zwischenstand;
        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        y = y+gp.tileSize*2;


        //
        if (gp.bot.bot1raus == false) {

            text = "Punkte Bot 1: " + punktebot1;
            x = getXforCenteredText(text);

            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 5, (float) (y + 5));

            g2.setColor(Color.WHITE);
            g2.drawString(text, x, (float) y);

            y = y + gp.tileSize;

        }


        //
        if (gp.bot.bot2raus == false) {

            text = "Punkte Bot 2: " + punktebot2;
            x = getXforCenteredText(text);

            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 5, (float) (y + 5));

            g2.setColor(Color.WHITE);
            g2.drawString(text, x, (float) y);

            y = y + gp.tileSize;
        }


        //
        if (gp.bot.bot3raus == false) {

            text = "Punkte Bot 3: " + punktebot3;
            x = getXforCenteredText(text);

            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 5, (float) (y + 5));

            g2.setColor(Color.WHITE);
            g2.drawString(text, x, (float) y);

            y = y + gp.tileSize;
        }


        //
        if (gp.spieler.spielerraus == false) {
            text = "Punkte Spieler: " + punktespieler;
            x = getXforCenteredText(text);

            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 5, (float) (y + 5));

            g2.setColor(Color.WHITE);
            g2.drawString(text, x, (float) y);

        }

        y = y + gp.tileSize * 2;

        //
        text = "Fortsetzen";
        x = getXforCenteredText(text);

        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5, (float) (y+5));

        g2.setColor(Color.WHITE);
        g2.drawString(text,x, (float) y);

        if (commandNum == 0) {

            g2.drawString(">", x-25, (float) y);

        }

    }


    //aufrufen um herzen abzuziehen
    public void herzabziehen(int spieler) {

        System.out.println("herz abziehen" + spieler);

        if (herzen[spieler][2] == 0) {

            herzen[spieler][2] = 1;

        } else if (herzen[spieler][1] == 0 ) {

            herzen[spieler][1] = 1;

        } else if (herzen[spieler][0] == 0 ) {

            herzen[spieler][0] = 1;

        } else {

            if (spieler == 0) {

                gp.bot.bot1raus = true;

            }
            if (spieler == 1) {

                gp.bot.bot2raus = true;

            }
            if (spieler == 2) {

                gp.bot.bot3raus = true;

            }
            if (spieler == 3) {

                gp.spieler.spielerraus = true;

            }

        }

    }


    //herzen zurücksetzen
    public void herzenZurücksetzen() {


        int test = 0;

        gp.ui.herzen[0][0] = test;
        gp.ui.herzen[0][1] = test;
        gp.ui.herzen[0][2] = test;

        gp.ui.herzen[1][0] = test;
        gp.ui.herzen[1][1] = test;
        gp.ui.herzen[1][2] = test;

        gp.ui.herzen[2][0] = test;
        gp.ui.herzen[2][1] = test;
        gp.ui.herzen[2][2] = test;

        gp.ui.herzen[3][0] = test;
        gp.ui.herzen[3][1] = test;
        gp.ui.herzen[3][2] = test;
    }


    //warning bei scale veränderung
    public void warning() {

        if (gp.ui.title == true) {

            update = true;
            drawTitleScreen();
            update = false;

        } else {
            backround();
            gp.karten.kartenzuteilen();
        }


        g2.setFont(MaruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,gp.scale*10F));

        //sub window
        int frameX = gp.tileSize*8;
        int frameY = gp.tileSize*2;
        int frameWidth =  gp.tileSize*10;
        int frameHeight = gp.tileSize* 12;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        int textY = frameY + gp.tileSize*3;

        String text = "Um die Größe des Spieles";
        int textX = getXforCenteredText(text);
        g2.drawString(text,textX,textY);

        textY = textY + gp.tileSize;

        text = "zu verändern muss das Spiel";
        textX = getXforCenteredText(text);
        g2.drawString(text,textX,textY);

        textY = textY + gp.tileSize;

        text = "neugestartet werden. Bitte";
        textX = getXforCenteredText(text);
        g2.drawString(text,textX,textY);

        textY = textY + gp.tileSize;

        text = "speichere vorher deinen";
        textX = getXforCenteredText(text);
        g2.drawString(text,textX,textY);

        textY = textY + gp.tileSize;

        text = "Spielstand!";
        textX = getXforCenteredText(text);
        g2.drawString(text,textX,textY);

        textY = textY + gp.tileSize;

        text = "Möchtest du jetzt neustarten?";
        textX = getXforCenteredText(text);
        g2.drawString(text,textX,textY);

        int y = 0;
        text = "Ja";
        int x = getXforCenteredText(text);
        y = (int) (y + (gp.tileSize * 12));

        g2.drawString(text, x, y);


            if (commandNum == 0) {
                g2.drawString(">", (int) (x - gp.tileSize*0.6), y);
        }


        text = "Nein";
        x = getXforCenteredText(text);
        y = (int) (y + (gp.tileSize * 1.1));

        g2.drawString(text, x, y);


            if (commandNum == 1) {
                g2.drawString(">", (int) (x - gp.tileSize*0.6), y);

        }


    }


    //bildzeichnen
    public BufferedImage bildmalen(String imageName) {

        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/karten/"+imageName+ ".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
