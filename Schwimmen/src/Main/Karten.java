package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Karten {

    GamePanel gp;
    Graphics2D g2;
    BufferedImage image_7_of_clubs, image_7_of_diamonds, image_7_of_hearts,
            image_7_of_spades, image_8_of_clubs, image_8_of_diamonds,
            image_8_of_hearts, image_8_of_spades, image_9_of_clubs,
            image_9_of_diamonds, image_9_of_hearts, image_9_of_spades,
            image_10_of_clubs, image_10_of_diamonds, image_10_of_hearts,
            image_10_of_spades, image_ace_of_clubs, image_ace_of_diamonds,
            image_ace_of_hearts, image_ace_of_spades2, image_jack_of_clubs2,
            image_jack_of_diamonds2, image_jack_of_hearts2, image_jack_of_spades2,
            image_king_of_clubs2, image_king_of_diamonds2, image_king_of_hearts2,
            image_king_of_spades2, image_queen_of_clubs2, image_queen_of_diamonds2,
            image_queen_of_hearts2, image_queen_of_spades2 = null;


    public BufferedImage[] karten;
    public BufferedImage[] kartenzumVergleichen;
    public BufferedImage[] temp;
    public BufferedImage[][] hand;
    public Queue<BufferedImage> stapel = new LinkedList<>();
    Font MaruMonica;


    public Karten(GamePanel gp) {


        this.gp = gp;

    }


    public void draw(Graphics2D g2) {

        this.g2 = g2;
    }


    //karten zuteilen (werte)
    public void sortieren() {

        System.out.println("Karten mischen");


        //namen bilder zuordnen
        image_7_of_clubs = bildmalen("7_of_clubs");
        image_7_of_diamonds = bildmalen("7_of_diamonds");
        image_7_of_hearts = bildmalen("7_of_hearts");
        image_7_of_spades = bildmalen("7_of_spades");
        image_8_of_clubs = bildmalen("8_of_clubs");
        image_8_of_diamonds = bildmalen("8_of_diamonds");
        image_8_of_hearts = bildmalen("8_of_hearts");
        image_8_of_spades = bildmalen("8_of_spades");
        image_9_of_clubs = bildmalen("9_of_clubs");
        image_9_of_diamonds = bildmalen("9_of_diamonds");
        image_9_of_hearts = bildmalen("9_of_hearts");
        image_9_of_spades = bildmalen("9_of_spades");
        image_10_of_clubs = bildmalen("10_of_clubs");
        image_10_of_diamonds = bildmalen("10_of_diamonds");
        image_10_of_hearts = bildmalen("10_of_hearts");
        image_10_of_spades = bildmalen("10_of_spades");
        image_ace_of_clubs = bildmalen("ace_of_clubs");
        image_ace_of_diamonds = bildmalen("ace_of_diamonds");
        image_ace_of_hearts = bildmalen("ace_of_hearts");
        image_ace_of_spades2 = bildmalen("ace_of_spades2");
        image_jack_of_clubs2 = bildmalen("jack_of_clubs2");
        image_jack_of_diamonds2 = bildmalen("jack_of_diamonds2");
        image_jack_of_hearts2 = bildmalen("jack_of_hearts2");
        image_jack_of_spades2 = bildmalen("jack_of_spades2");
        image_king_of_clubs2 = bildmalen("king_of_clubs2");
        image_king_of_diamonds2 = bildmalen("king_of_diamonds2");
        image_king_of_hearts2 = bildmalen("king_of_hearts2");
        image_king_of_spades2 = bildmalen("king_of_spades2");
        image_queen_of_clubs2 = bildmalen("queen_of_clubs2");
        image_queen_of_diamonds2 = bildmalen("queen_of_diamonds2");
        image_queen_of_hearts2 = bildmalen("queen_of_hearts2");
        image_queen_of_spades2 = bildmalen("queen_of_spades2");

        karten = new BufferedImage[]{
                image_7_of_clubs, image_7_of_diamonds, image_7_of_hearts,
                image_7_of_spades, image_8_of_clubs, image_8_of_diamonds,
                image_8_of_hearts, image_8_of_spades, image_9_of_clubs,
                image_9_of_diamonds, image_9_of_hearts, image_9_of_spades,
                image_10_of_clubs, image_10_of_diamonds, image_10_of_hearts,
                image_10_of_spades, image_ace_of_clubs, image_ace_of_diamonds,
                image_ace_of_hearts, image_ace_of_spades2, image_jack_of_clubs2,
                image_jack_of_diamonds2, image_jack_of_hearts2, image_jack_of_spades2,
                image_king_of_clubs2, image_king_of_diamonds2, image_king_of_hearts2,
                image_king_of_spades2, image_queen_of_clubs2, image_queen_of_diamonds2,
                image_queen_of_hearts2, image_queen_of_spades2
        };

        kartenzumVergleichen = new BufferedImage[]{
                image_7_of_clubs, image_7_of_diamonds, image_7_of_hearts,
                image_7_of_spades, image_8_of_clubs, image_8_of_diamonds,
                image_8_of_hearts, image_8_of_spades, image_9_of_clubs,
                image_9_of_diamonds, image_9_of_hearts, image_9_of_spades,
                image_10_of_clubs, image_10_of_diamonds, image_10_of_hearts,
                image_10_of_spades, image_ace_of_clubs, image_ace_of_diamonds,
                image_ace_of_hearts, image_ace_of_spades2, image_jack_of_clubs2,
                image_jack_of_diamonds2, image_jack_of_hearts2, image_jack_of_spades2,
                image_king_of_clubs2, image_king_of_diamonds2, image_king_of_hearts2,
                image_king_of_spades2, image_queen_of_clubs2, image_queen_of_diamonds2,
                image_queen_of_hearts2, image_queen_of_spades2
        };


        // shuffle
        for (int i = 0; i < karten.length; i++) {
            int r = i + (int) (Math.random() * (karten.length - i));
            BufferedImage temp = karten[r];
            karten[r] = karten[i];
            karten[i] = temp;
        }

        System.out.println("sortiert");


        // spieler  k1  k2  k3
        // bot1     00  01  02
        // bot2     10  11  12
        // bot3     20  21  22
        // spieler  30  31  32
        // mitte    40  41  42


        hand = new BufferedImage[5][3];
        int reihe = 0;
        int spalte = 0;

        for (int i = 15; i > 0; i--) {


            if (reihe > 2) {
                reihe = 0;
                spalte++;
            }


            hand[spalte][reihe] = karten[i];


            reihe++;

        }

        for (int i = 31; i > 15; i--) {

            stapel.add(karten[i]);

        }


    }


    public void kartenMischenNachLoadConfig() {

        int reihe = 0;
        int spalte = 0;
        int z = 0;

        //stapel erzeugen
        for (int karten = 0; karten < 32; karten ++) {


            if (guckenObKarteInHand(kartenzumVergleichen[karten]) == false) {

                stapel.add(kartenzumVergleichen[karten]);
                z++;
                System.out.println(z);


            }

        }


        BufferedImage[] mischen = new BufferedImage[16];

        //stapel mischen
        //von stapel in array

        for (int i = 0; i < 16; i++) {

            mischen[i] = stapel.peek();
            stapel.poll();

        }

        // array mischen
        for (int i = 0; i < mischen.length; i++) {

            int r = i + (int) (Math.random() * (mischen.length - i));
            BufferedImage temp = mischen[r];
            mischen[r] = mischen[i];
            mischen[i] = temp;

        }

        //von array in stapel
        for (int i = 0; i < 16; i++) {

            stapel.add(mischen[i]);

        }






    }


    public boolean guckenObKarteInHand(BufferedImage image) {

        int reihe = 0;
        int spalte = 0;


            for (int handkarten = 15; handkarten > 0; handkarten --) {

                if (reihe > 2) {
                    reihe = 0;
                    spalte++;
                }

                if (hand[spalte][reihe] == image) {

                    return true;

                }


                reihe++;

            }


            return false;

    }


    //karten zeichnen (im spiel)
    public void kartenzuteilen() {


        //spieler kann bot karten sehen

        if (gp.ui.leicht == true) {

            int ybot1 = gp.tileSize * 3;


            for (int i = 0; i <= 2; i++) {

                Color c = new Color(0, 0, 0, 170);
                g2.setColor(c);
                g2.fillRoundRect(gp.tileSize * 3 + 7, ybot1 + 7, gp.tileSize * 2, gp.tileSize * 3, 25, 25);

                if (gp.bot.bot1raus == false) {
                    g2.drawImage(hand[0][i], gp.tileSize * 3, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);
                } else {
                    g2.drawImage(bildmalen("00"), gp.tileSize * 3, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);
                }

                ybot1 = ybot1 + gp.tileSize * 4;

            }

            //bot2
            int xbot2 = gp.tileSize * 9;
            for (int i = 0; i <= 2; i++) {

                Color c = new Color(0, 0, 0, 170);
                g2.setColor(c);
                g2.fillRoundRect(xbot2 + 7, gp.tileSize * 1 + 7, gp.tileSize * 2, gp.tileSize * 3, 25, 25);

                if (gp.bot.bot2raus == false) {
                    g2.drawImage(hand[1][i], xbot2, gp.tileSize * 1, gp.tileSize * 2, gp.tileSize * 3, null);
                } else {
                    g2.drawImage(bildmalen("00"), xbot2, gp.tileSize * 1, gp.tileSize * 2, gp.tileSize * 3, null);
                }

                xbot2 = xbot2 + gp.tileSize * 3;

            }

            //bot3
            int ybot3 = gp.tileSize * 3;
            for (int i = 0; i <= 2; i++) {

                Color c = new Color(0, 0, 0, 170);
                g2.setColor(c);
                g2.fillRoundRect(gp.tileSize * 21 + 7, ybot3 + 7, gp.tileSize * 2, gp.tileSize * 3, 25, 25);

                if (gp.bot.bot3raus == false) {
                    g2.drawImage(hand[2][i], gp.tileSize * 21, ybot3, gp.tileSize * 2, gp.tileSize * 3, null);
                } else {
                    g2.drawImage(bildmalen("00"), gp.tileSize * 21, ybot3, gp.tileSize * 2, gp.tileSize * 3, null);
                }

                ybot3 = ybot3 + gp.tileSize * 4;
            }


        } else {



        //spieler sieht bot karten nicht
        //bot1

        BufferedImage image = bildmalen("00");


        if (gp.bot.bot1raus == true) {

            float opacity = (float) 0.3;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        }

        int ybot1 = gp.tileSize * 3;
        for (int i = 0; i <= 2; i++) {

            Color c = new Color(0, 0, 0, 170);
            g2.setColor(c);
            g2.fillRoundRect(gp.tileSize*3+7,ybot1+7,gp.tileSize*2,gp.tileSize*3, 25, 25);

            g2.drawImage(image, gp.tileSize * 3, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);
            ybot1 = ybot1 + gp.tileSize * 4;

        }
        float opacity = (float) 1;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));



        if (gp.bot.bot2raus == true) {

            opacity = (float) 0.3;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        }

        //bot2
        int xbot2 = gp.tileSize * 9;
        for (int i = 0; i <= 2; i++) {

            Color c = new Color(0, 0, 0, 170);
            g2.setColor(c);
            g2.fillRoundRect(xbot2 +7, gp.tileSize * 1 + 7,gp.tileSize*2,gp.tileSize*3, 25, 25);

            g2.drawImage(image, xbot2, gp.tileSize * 1, gp.tileSize * 2, gp.tileSize * 3, null);
            xbot2 = xbot2 + gp.tileSize * 3;

        }
        opacity = (float) 1;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));


        if (gp.bot.bot3raus == true) {

            opacity = (float) 0.3;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        }

        //bot3
        int ybot3 = gp.tileSize * 3;
        for (int i = 0; i <= 2; i++) {

            Color c = new Color(0, 0, 0, 170);
            g2.setColor(c);
            g2.fillRoundRect(gp.tileSize * 21 + 7, ybot3 + 7,gp.tileSize*2,gp.tileSize*3, 25, 25);

            g2.drawImage(image, gp.tileSize * 21, ybot3, gp.tileSize * 2, gp.tileSize * 3, null);
            ybot3 = ybot3 + gp.tileSize * 4;
        }
        opacity = (float) 1;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));


        }



        //spieler
        int xspieler = gp.tileSize * 9;
        for (int i = 0; i <= 2; i++) {

            Color c = new Color(0, 0, 0, 170);
            g2.setColor(c);
            g2.fillRoundRect(xspieler + 7, gp.tileSize * 13 + 7,gp.tileSize*2,gp.tileSize*3, 25, 25);

            g2.drawImage(hand[3][i], xspieler, gp.tileSize * 13, gp.tileSize * 2, gp.tileSize * 3, null);
            xspieler = xspieler + gp.tileSize * 3;

        }

        //mitte
        int xmitte = gp.tileSize * 9;
        for (int i = 0; i <= 2; i++) {

            Color c = new Color(0, 0, 0, 170);
            g2.setColor(c);
            g2.fillRoundRect(xmitte + 7, (int) (gp.tileSize * 7.64) + 7,gp.tileSize*2,gp.tileSize*3, 25, 25);

            g2.drawImage(hand[4][i], xmitte, (int) (gp.tileSize * 7.64), gp.tileSize * 2, gp.tileSize * 3, null);
            xmitte = xmitte + gp.tileSize * 3;

        }




    }


    public void neueKartenMitte() {

        System.out.println("neueKartenMitte");

        BufferedImage[] mischen = new BufferedImage[16];

        //aktuelle karten hinten auf stapel
        stapel.add(hand[4][0]);
        stapel.add(hand[4][1]);
        stapel.add(hand[4][2]);

        //gibt karten ersten 3 karten von stapel
        hand[4][0] = stapel.peek();
        stapel.poll();
        hand[4][1] = stapel.peek();
        stapel.poll();
        hand[4][2] = stapel.peek();
        stapel.poll();


        //von stapel in array
        for (int i = 0; i < 16; i++) {

            mischen[i] = stapel.peek();
            stapel.poll();

        }

        // array mischen
        for (int i = 0; i < mischen.length; i++) {

            int r = i + (int) (Math.random() * (mischen.length - i));
            BufferedImage temp = mischen[r];
            mischen[r] = mischen[i];
            mischen[i] = temp;

        }

        //von array in stapel
        for (int i = 0; i < 16; i++) {

            stapel.add(mischen[i]);

        }

        gp.ui.backround();
        kartenzuteilen();



    }


    //kartenwert erhalten
    public Integer kartenwerterhalten (BufferedImage imagetocompare) {

        int kartenwert = 0;

        //11 ass);
        if (bufferedImagesEqual(imagetocompare, image_ace_of_clubs) ||
                bufferedImagesEqual(imagetocompare, image_ace_of_diamonds) ||
                bufferedImagesEqual(imagetocompare, image_ace_of_hearts) ||
                bufferedImagesEqual(imagetocompare, image_ace_of_spades2)) {

            kartenwert = 11;
        }


        //10 König, Dame und Bube
        if (bufferedImagesEqual(imagetocompare, image_jack_of_clubs2) ||
                bufferedImagesEqual(imagetocompare, image_jack_of_diamonds2) ||
                bufferedImagesEqual(imagetocompare, image_jack_of_hearts2) ||
                bufferedImagesEqual(imagetocompare, image_jack_of_spades2) ||
                bufferedImagesEqual(imagetocompare, image_king_of_clubs2) ||
                bufferedImagesEqual(imagetocompare, image_king_of_diamonds2) ||
                bufferedImagesEqual(imagetocompare, image_king_of_hearts2) ||
                bufferedImagesEqual(imagetocompare, image_king_of_spades2) ||
                bufferedImagesEqual(imagetocompare, image_queen_of_clubs2) ||
                bufferedImagesEqual(imagetocompare, image_queen_of_diamonds2) ||
                bufferedImagesEqual(imagetocompare, image_queen_of_hearts2) ||
                bufferedImagesEqual(imagetocompare, image_queen_of_spades2)) {


            kartenwert = 10;
        }

        //Augenzahlen:

        //10
        if (bufferedImagesEqual(imagetocompare, image_10_of_clubs) ||
                bufferedImagesEqual(imagetocompare, image_10_of_diamonds) ||
                bufferedImagesEqual(imagetocompare, image_10_of_hearts) ||
                bufferedImagesEqual(imagetocompare, image_10_of_spades)) {

            kartenwert = 10;
        }


        //9
        if (bufferedImagesEqual(imagetocompare, image_9_of_clubs) ||
                bufferedImagesEqual(imagetocompare, image_9_of_diamonds) ||
                bufferedImagesEqual(imagetocompare, image_9_of_hearts) ||
                bufferedImagesEqual(imagetocompare, image_9_of_spades)) {

            kartenwert = 9;
        }

        //8
        if (bufferedImagesEqual(imagetocompare, image_8_of_clubs) ||
                bufferedImagesEqual(imagetocompare, image_8_of_diamonds) ||
                bufferedImagesEqual(imagetocompare, image_8_of_hearts) ||
                bufferedImagesEqual(imagetocompare, image_8_of_spades)) {

            kartenwert = 8;
        }

        //7
        if (bufferedImagesEqual(imagetocompare, image_7_of_clubs) ||
                bufferedImagesEqual(imagetocompare, image_7_of_diamonds) ||
                bufferedImagesEqual(imagetocompare, image_7_of_hearts) ||
                bufferedImagesEqual(imagetocompare, image_7_of_spades)) {

            kartenwert = 7;
        }


        return kartenwert;

    }


    //kartenfarbe erhalten
    public String kartenfarbeerhalten (BufferedImage imagetocompare) {

            String kartenfarbe = "";

            //11 ass
            if (bufferedImagesEqual(imagetocompare, image_ace_of_clubs) ||
                    bufferedImagesEqual(imagetocompare, image_ace_of_spades2)) {

                kartenfarbe = "schwarz";
            }
            if (bufferedImagesEqual(imagetocompare, image_ace_of_diamonds) ||
                    bufferedImagesEqual(imagetocompare, image_ace_of_hearts)) {

                kartenfarbe = "rot";
            }


            //10 König, Dame und Bube
            if (bufferedImagesEqual(imagetocompare, image_jack_of_clubs2) ||
                    bufferedImagesEqual(imagetocompare, image_jack_of_spades2) ||
                    bufferedImagesEqual(imagetocompare, image_king_of_clubs2) ||
                    bufferedImagesEqual(imagetocompare, image_king_of_spades2) ||
                    bufferedImagesEqual(imagetocompare, image_queen_of_clubs2) ||
                    bufferedImagesEqual(imagetocompare, image_queen_of_spades2)) {


                kartenfarbe = "schwarz";
            }
            if (bufferedImagesEqual(imagetocompare, image_jack_of_diamonds2) ||
                    bufferedImagesEqual(imagetocompare, image_jack_of_hearts2) ||
                    bufferedImagesEqual(imagetocompare, image_king_of_diamonds2) ||
                    bufferedImagesEqual(imagetocompare, image_king_of_hearts2) ||
                    bufferedImagesEqual(imagetocompare, image_queen_of_diamonds2) ||
                    bufferedImagesEqual(imagetocompare, image_queen_of_hearts2)) {


                kartenfarbe = "rot";
            }

            //Augenzahlen:

            //10
            if (bufferedImagesEqual(imagetocompare, image_10_of_clubs) ||
                    bufferedImagesEqual(imagetocompare, image_10_of_spades)) {

                kartenfarbe = "schwarz";
            }
            if (bufferedImagesEqual(imagetocompare, image_10_of_diamonds) ||
                    bufferedImagesEqual(imagetocompare, image_10_of_hearts)) {

                kartenfarbe = "rot";
            }


            //9
            if (bufferedImagesEqual(imagetocompare, image_9_of_clubs) ||
                    bufferedImagesEqual(imagetocompare, image_9_of_spades)) {

                kartenfarbe = "schwarz";
            }
            if (bufferedImagesEqual(imagetocompare, image_9_of_diamonds) ||
                    bufferedImagesEqual(imagetocompare, image_9_of_hearts)) {

                kartenfarbe = "rot";
            }


            //8
            if (bufferedImagesEqual(imagetocompare, image_8_of_clubs) ||
                    bufferedImagesEqual(imagetocompare, image_8_of_spades)) {

                kartenfarbe = "schwarz";
            }
            if (bufferedImagesEqual(imagetocompare, image_8_of_diamonds) ||
                    bufferedImagesEqual(imagetocompare, image_8_of_hearts)) {

                kartenfarbe = "rot";
            }


            //7
            if (bufferedImagesEqual(imagetocompare, image_7_of_clubs) ||
                    bufferedImagesEqual(imagetocompare, image_7_of_spades)) {

                kartenfarbe = "schwarz";
            }
            if (bufferedImagesEqual(imagetocompare, image_7_of_diamonds) ||
                    bufferedImagesEqual(imagetocompare, image_7_of_hearts)) {

                kartenfarbe = "rot";
            }


            return kartenfarbe;

    }


    //kartensymbol
    public String kartensymbolerhalten (BufferedImage imagetocompare) {

        String kartensymbol = "";

        //clubs
        if (bufferedImagesEqual(imagetocompare, image_7_of_clubs) ||
                bufferedImagesEqual(imagetocompare, image_8_of_clubs) ||
                bufferedImagesEqual(imagetocompare, image_9_of_clubs) ||
                bufferedImagesEqual(imagetocompare, image_10_of_clubs) ||
                bufferedImagesEqual(imagetocompare, image_ace_of_clubs) ||
                bufferedImagesEqual(imagetocompare, image_king_of_clubs2) ||
                bufferedImagesEqual(imagetocompare, image_queen_of_clubs2) ||
                bufferedImagesEqual(imagetocompare, image_jack_of_clubs2)) {

            kartensymbol = "club";
            System.out.println("club");
        }






        //diamonds
        if (bufferedImagesEqual(imagetocompare, image_7_of_diamonds) ||
                bufferedImagesEqual(imagetocompare, image_8_of_diamonds) ||
                bufferedImagesEqual(imagetocompare, image_9_of_diamonds) ||
                bufferedImagesEqual(imagetocompare, image_10_of_diamonds) ||
                bufferedImagesEqual(imagetocompare, image_ace_of_diamonds) ||
                bufferedImagesEqual(imagetocompare, image_king_of_diamonds2) ||
                bufferedImagesEqual(imagetocompare, image_queen_of_diamonds2) ||
                bufferedImagesEqual(imagetocompare, image_jack_of_diamonds2)) {

            kartensymbol = "diamonds";
            System.out.println("diamonds");
        }






        //heart
        if (bufferedImagesEqual(imagetocompare, image_7_of_hearts) ||
                bufferedImagesEqual(imagetocompare, image_8_of_hearts) ||
                bufferedImagesEqual(imagetocompare, image_9_of_hearts) ||
                bufferedImagesEqual(imagetocompare, image_10_of_hearts) ||
                bufferedImagesEqual(imagetocompare, image_ace_of_hearts) ||
                bufferedImagesEqual(imagetocompare, image_king_of_hearts2) ||
                bufferedImagesEqual(imagetocompare, image_queen_of_hearts2) ||
                bufferedImagesEqual(imagetocompare, image_jack_of_hearts2)) {

            kartensymbol = "heart";
            System.out.println("heart");
        }






        //spades
        if (bufferedImagesEqual(imagetocompare, image_7_of_spades) ||
                bufferedImagesEqual(imagetocompare, image_8_of_spades) ||
                bufferedImagesEqual(imagetocompare, image_9_of_spades) ||
                bufferedImagesEqual(imagetocompare, image_10_of_spades) ||
                bufferedImagesEqual(imagetocompare, image_ace_of_spades2) ||
                bufferedImagesEqual(imagetocompare, image_king_of_spades2) ||
                bufferedImagesEqual(imagetocompare, image_queen_of_spades2) ||
                bufferedImagesEqual(imagetocompare, image_jack_of_spades2)) {

            kartensymbol = "spades";
            System.out.println("spades");

        }


        return kartensymbol;

    }


    //kartenfarbe erhalten
    public String karteerhalteneinzeln (BufferedImage imagetocompare) {

        String karte = "";



        //ass
        if (bufferedImagesEqual(imagetocompare, image_ace_of_spades2) ||
                bufferedImagesEqual(imagetocompare, image_ace_of_clubs) ||
                    bufferedImagesEqual(imagetocompare, image_ace_of_diamonds) ||
                        bufferedImagesEqual(imagetocompare, image_ace_of_hearts)) {

            karte = "ace";

        }


        //könig
        if (bufferedImagesEqual(imagetocompare, image_king_of_spades2) ||
                bufferedImagesEqual(imagetocompare, image_king_of_hearts2) ||
                    bufferedImagesEqual(imagetocompare, image_king_of_diamonds2) ||
                        bufferedImagesEqual(imagetocompare, image_king_of_clubs2)) {

            karte = "king";

        }


        //dame
        if (bufferedImagesEqual(imagetocompare, image_queen_of_spades2) ||
                bufferedImagesEqual(imagetocompare, image_queen_of_hearts2) ||
                    bufferedImagesEqual(imagetocompare, image_queen_of_diamonds2) ||
                        bufferedImagesEqual(imagetocompare, image_queen_of_clubs2)) {

            karte = "queen";

        }


        //bube
        if (bufferedImagesEqual(imagetocompare, image_jack_of_spades2) ||
                bufferedImagesEqual(imagetocompare, image_jack_of_hearts2) ||
                    bufferedImagesEqual(imagetocompare, image_jack_of_diamonds2) ||
                        bufferedImagesEqual(imagetocompare, image_jack_of_clubs2)) {

            karte = "jack";

        }


        //10
        if (bufferedImagesEqual(imagetocompare, image_10_of_clubs) ||
                bufferedImagesEqual(imagetocompare, image_10_of_diamonds) ||
                    bufferedImagesEqual(imagetocompare, image_10_of_hearts) ||
                        bufferedImagesEqual(imagetocompare, image_10_of_spades)) {

            karte = "10";

        }


        //9
        if (bufferedImagesEqual(imagetocompare, image_9_of_clubs) ||
                bufferedImagesEqual(imagetocompare, image_9_of_diamonds) ||
                    bufferedImagesEqual(imagetocompare, image_9_of_hearts) ||
                        bufferedImagesEqual(imagetocompare, image_9_of_spades)) {

            karte = "9";

        }


        //8
        if (bufferedImagesEqual(imagetocompare, image_8_of_clubs) ||
                bufferedImagesEqual(imagetocompare, image_8_of_diamonds) ||
                    bufferedImagesEqual(imagetocompare, image_8_of_hearts) ||
                        bufferedImagesEqual(imagetocompare, image_8_of_spades)) {

            karte = "8";

        }


        //7
        if (bufferedImagesEqual(imagetocompare, image_7_of_clubs) ||
                bufferedImagesEqual(imagetocompare, image_7_of_diamonds) ||
                    bufferedImagesEqual(imagetocompare, image_7_of_hearts) ||
                        bufferedImagesEqual(imagetocompare, image_7_of_spades)) {

            karte = "7";

        }


        return karte;

    }


    //karten vergleichen um zu schauen ob gleiche karten
    public boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y))
                        return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }


    public BufferedImage bildmalen(String imageName) {

        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/karten/" + imageName + ".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
