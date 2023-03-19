package Main;

import java.awt.image.BufferedImage;
import java.io.*;

public class Config {

    boolean speichern, laden = false;
    int stelle = -1;
    String Karte = "";

    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }


    public void saveConfig() {
        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            //music volume
            bw.write(String.valueOf(gp.music.volumeScale));

            bw.newLine();

            //se volume
            bw.write(String.valueOf(gp.se.volumeScale));

            bw.newLine();

            //scale
            if (gp.scaleneu > 0) {
                bw.write(String.valueOf(gp.scaleneu));
            } else {
                bw.write(String.valueOf(gp.scale));
            }

            bw.newLine();


            if (speichern == true) {

                //------------------------------------------------------------------------------------------//


                //spieler hand 1
                bw.write(configImageToString(gp.karten.hand[3][0]));
                bw.newLine();

                //spieler hand 2
                bw.write(configImageToString(gp.karten.hand[3][1]));
                bw.newLine();

                //spieler hand 3
                bw.write(configImageToString(gp.karten.hand[3][2]));
                bw.newLine();


                //------------------------------------------------------------------------------------------//

                //bot 1 hand 1
                bw.write(configImageToString(gp.karten.hand[0][0]));
                bw.newLine();

                //bot 1 hand 2
                bw.write(configImageToString(gp.karten.hand[0][1]));
                bw.newLine();

                //bot 1 hand 3
                bw.write(configImageToString(gp.karten.hand[0][2]));
                bw.newLine();

                //------------------------------------------------------------------------------------------//

                //bot 2 hand 1
                bw.write(configImageToString(gp.karten.hand[1][0]));
                bw.newLine();

                //bot 2 hand 2
                bw.write(configImageToString(gp.karten.hand[1][1]));
                bw.newLine();

                //bot 2 hand 3
                bw.write(configImageToString(gp.karten.hand[1][2]));
                bw.newLine();

                //------------------------------------------------------------------------------------------//

                //bot 3 hand 1
                bw.write(configImageToString(gp.karten.hand[2][0]));
                bw.newLine();

                //bot 3 hand 2
                bw.write(configImageToString(gp.karten.hand[2][1]));
                bw.newLine();

                //bot 3 hand 3
                bw.write(configImageToString(gp.karten.hand[2][2]));
                bw.newLine();

                //------------------------------------------------------------------------------------------//

                //mitte 1
                bw.write(configImageToString(gp.karten.hand[4][0]));
                bw.newLine();

                //mitte 2
                bw.write(configImageToString(gp.karten.hand[4][1]));
                bw.newLine();

                //mitte 3
                bw.write(configImageToString(gp.karten.hand[4][2]));
                bw.newLine();


                //herzen
                //bot1
                int herzen = 0;
                if (gp.ui.herzen[0][2] == 0) {

                    herzen = 3;
                } else if (gp.ui.herzen[0][1] == 0) {

                    herzen = 2;
                } else if (gp.ui.herzen[0][0] == 0) {

                    herzen = 1;
                } else {

                    herzen = 0;
                }

                bw.write(String.valueOf(herzen));
                bw.newLine();


                //bot2
                herzen = 0;
                if (gp.ui.herzen[1][2] == 0) {

                    herzen = 3;
                } else if (gp.ui.herzen[1][1] == 0) {

                    herzen = 2;
                } else if (gp.ui.herzen[1][0] == 0) {

                    herzen = 1;
                } else {

                    herzen = 0;
                }

                bw.write(String.valueOf(herzen));
                bw.newLine();


                //bot3
                herzen = 0;
                if (gp.ui.herzen[2][2] == 0) {

                    herzen = 3;
                } else if (gp.ui.herzen[2][1] == 0) {

                    herzen = 2;
                } else if (gp.ui.herzen[2][0] == 0) {

                    herzen = 1;
                } else {

                    herzen = 0;
                }

                bw.write(String.valueOf(herzen));
                bw.newLine();


                //spieler
                herzen = 0;
                if (gp.ui.herzen[3][2] == 0) {

                    herzen = 3;
                } else if (gp.ui.herzen[3][1] == 0) {

                    herzen = 2;
                } else if (gp.ui.herzen[3][0] == 0) {

                    herzen = 1;
                } else {

                    herzen = 0;
                }

                bw.write(String.valueOf(herzen));
                bw.newLine();


                //schiebencounter
                bw.write(String.valueOf(gp.spieler.schiebennotwendig));
                bw.newLine();


                //raus
                bw.write(String.valueOf(gp.spieler.spielerraus));
                bw.newLine();

                bw.write(String.valueOf(gp.bot.bot1raus));
                bw.newLine();

                bw.write(String.valueOf(gp.bot.bot2raus));
                bw.newLine();

                bw.write(String.valueOf(gp.bot.bot3raus));
                bw.newLine();


                //endgamephase
                bw.write(String.valueOf(gp.spieler.endgamephase));
                bw.newLine();

                bw.write(String.valueOf(gp.spieler.auslöserbestätigt));
                bw.newLine();

                bw.write(String.valueOf(gp.spieler.auslöser2));
                bw.newLine();


                //schwierigkeitsgrad
                bw.write(String.valueOf(gp.bot.schwierigkeitsgrad));
                bw.newLine();

                bw.write(String.valueOf(gp.ui.leicht));
                bw.newLine();

                bw.write(String.valueOf(gp.ui.mittel));
                bw.newLine();

                bw.write(String.valueOf(gp.ui.schwer));
                bw.newLine();


                speichern = false;

                //------------------------------------------------------------------------------------------//

            }



            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String s = br.readLine();

            if (laden == false) {
                //musicvolume
                gp.music.volumeScale = Integer.parseInt(s);

                //se volume
                s = br.readLine();
                gp.se.volumeScale = Integer.parseInt(s);

                //scale
                s = br.readLine();
                gp.scale = Integer.parseInt(s);
                gp.ui.pixelscale = (Integer.parseInt(s) - 1);

            } else {

                //musicvolume
                gp.music.volumeScale = Integer.parseInt(s);

                //se volume
                s = br.readLine();
                gp.se.volumeScale = Integer.parseInt(s);

                //scale
                s = br.readLine();
                gp.scale = Integer.parseInt(s);
                gp.ui.pixelscale = (Integer.parseInt(s) - 1);

                System.out.println("Config geladen");

                s = br.readLine();
                BufferedImage image = configStringToImage(s);
                gp.karten.hand[3][0] = image;

                s = br.readLine();
                image = configStringToImage(s);
                gp.karten.hand[3][1] = image;

                s = br.readLine();
                image = configStringToImage(s);
                gp.karten.hand[3][2] = image;

                s = br.readLine();
                image = configStringToImage(s);
                gp.karten.hand[0][0] = image;

                s = br.readLine();
                image = configStringToImage(s);
                gp.karten.hand[0][1] = image;

                s = br.readLine();
                image = configStringToImage(s);
                gp.karten.hand[0][2] = image;

                s = br.readLine();
                image = configStringToImage(s);
                gp.karten.hand[1][0] = image;

                s = br.readLine();
                image = configStringToImage(s);
                gp.karten.hand[1][1] = image;

                s = br.readLine();
                image = configStringToImage(s);
                gp.karten.hand[1][2] = image;

                s = br.readLine();
                image = configStringToImage(s);
                gp.karten.hand[2][0] = image;

                s = br.readLine();
                image = configStringToImage(s);
                gp.karten.hand[2][1] = image;

                s = br.readLine();
                image = configStringToImage(s);
                gp.karten.hand[2][2] = image;

                s = br.readLine();
                image = configStringToImage(s);
                gp.karten.hand[4][0] = image;

                s = br.readLine();
                image = configStringToImage(s);
                gp.karten.hand[4][1] = image;

                s = br.readLine();
                image = configStringToImage(s);
                gp.karten.hand[4][2] = image;

                gp.karten.kartenMischenNachLoadConfig();


                //herzen
                //bot1
                s = br.readLine();
                if (Integer.parseInt(s) == 3) {

                    gp.ui.herzen[0][0] = 0;
                    gp.ui.herzen[0][1] = 0;
                    gp.ui.herzen[0][2] = 0;

                } else if (Integer.parseInt(s) == 2) {

                    gp.ui.herzen[0][0] = 0;
                    gp.ui.herzen[0][1] = 0;
                    gp.ui.herzen[0][2] = 1;

                } else if (Integer.parseInt(s) == 1) {

                    gp.ui.herzen[0][0] = 0;
                    gp.ui.herzen[0][1] = 1;
                    gp.ui.herzen[0][2] = 1;

                } else {

                    gp.ui.herzen[0][0] = 1;
                    gp.ui.herzen[0][1] = 1;
                    gp.ui.herzen[0][2] = 1;

                }



                //bot2
                s = br.readLine();
                if (Integer.parseInt(s) == 3) {

                    gp.ui.herzen[1][0] = 0;
                    gp.ui.herzen[1][1] = 0;
                    gp.ui.herzen[1][2] = 0;

                } else if (Integer.parseInt(s) == 2) {

                    gp.ui.herzen[1][0] = 0;
                    gp.ui.herzen[1][1] = 0;
                    gp.ui.herzen[1][2] = 1;

                } else if (Integer.parseInt(s) == 1) {

                    gp.ui.herzen[1][0] = 0;
                    gp.ui.herzen[1][1] = 1;
                    gp.ui.herzen[1][2] = 1;

                } else {

                    gp.ui.herzen[1][0] = 1;
                    gp.ui.herzen[1][1] = 1;
                    gp.ui.herzen[1][2] = 1;

                }


                //bot3
                s = br.readLine();
                if (Integer.parseInt(s) == 3) {

                    gp.ui.herzen[2][0] = 0;
                    gp.ui.herzen[2][1] = 0;
                    gp.ui.herzen[2][2] = 0;

                } else if (Integer.parseInt(s) == 2) {

                    gp.ui.herzen[2][0] = 0;
                    gp.ui.herzen[2][1] = 0;
                    gp.ui.herzen[2][2] = 1;

                } else if (Integer.parseInt(s) == 1) {

                    gp.ui.herzen[2][0] = 0;
                    gp.ui.herzen[2][1] = 1;
                    gp.ui.herzen[2][2] = 1;

                } else {

                    gp.ui.herzen[2][0] = 1;
                    gp.ui.herzen[2][1] = 1;
                    gp.ui.herzen[2][2] = 1;

                }


                //spieler
                s = br.readLine();
                if (Integer.parseInt(s) == 3) {

                    gp.ui.herzen[3][0] = 0;
                    gp.ui.herzen[3][1] = 0;
                    gp.ui.herzen[3][2] = 0;

                } else if (Integer.parseInt(s) == 2) {

                    gp.ui.herzen[3][0] = 0;
                    gp.ui.herzen[3][1] = 0;
                    gp.ui.herzen[3][2] = 1;

                } else if (Integer.parseInt(s) == 1) {

                    gp.ui.herzen[3][0] = 0;
                    gp.ui.herzen[3][1] = 1;
                    gp.ui.herzen[3][2] = 1;

                } else {

                    gp.ui.herzen[3][0] = 1;
                    gp.ui.herzen[3][1] = 1;
                    gp.ui.herzen[3][2] = 1;

                }




                //schiebencounter
                s = br.readLine();
                gp.spieler.schiebennotwendig = Integer.parseInt(s);


                //raus
                s = br.readLine();
                if (s.equals("false")) {
                    gp.spieler.spielerraus = false;
                } else {
                    gp.spieler.spielerraus = true;
                }

                s = br.readLine();
                if (s.equals("false")) {
                    gp.bot.bot1raus = false;
                } else {
                    gp.bot.bot1raus = true;
                }

                s = br.readLine();
                if (s.equals("false")) {
                    gp.bot.bot2raus = false;
                } else {
                    gp.bot.bot2raus = true;
                }

                s = br.readLine();
                if (s.equals("false")) {
                    gp.bot.bot3raus = false;
                } else {
                    gp.bot.bot3raus = true;
                }


                //endgamephase
                s = br.readLine();
                if (s.equals("false")) {
                    gp.spieler.endgamephase = false;
                } else {
                    gp.spieler.endgamephase = true;
                }

                //endgamephase
                s = br.readLine();
                if (s.equals("false")) {
                    gp.spieler.auslöserbestätigt = false;
                } else {
                    gp.spieler.auslöserbestätigt = true;
                }

                s = br.readLine();
                gp.spieler.auslöser2 = Integer.parseInt(s);

                //schwierigkeitsgrad
                s = br.readLine();
                gp.bot.schwierigkeitsgrad = Integer.parseInt(s);

                //leicht
                s = br.readLine();
                if (s.equals("false")) {
                    gp.ui.leicht = false;
                } else {
                    gp.ui.leicht = true;
                }

                //mittel
                s = br.readLine();
                if (s.equals("false")) {
                    gp.ui.mittel = false;
                } else {
                    gp.ui.mittel = true;
                }

                //schwer
                s = br.readLine();
                if (s.equals("false")) {
                    gp.ui.schwer = false;
                } else {
                    gp.ui.schwer = true;
                }




            }


            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String configImageToString(BufferedImage image) {


        for (int schleife = 0; schleife < 32; schleife++) {

            if (gp.karten.bufferedImagesEqual(image,gp.karten.kartenzumVergleichen[schleife]) == true) {

                stelle = schleife;
                System.out.println(stelle);

            }


        }


        switch (stelle) {


            case 0:

                Karte = "7clubs";

            break;
            case 1:

                Karte = "7diamonds";

            break;
            case 2:

                Karte = "7hearts";

            break;
            case 3:

                Karte = "7spades";

            break;
            case 4:

                Karte = "8clubs";

            break;
            case 5:

                Karte = "8diamonds";

            break;
            case 6:

                Karte = "8hearts";

            break;
            case 7:

                Karte = "8spades";

            break;
            case 8:

                Karte = "9clubs";

            break;
            case 9:

                Karte = "9diamonds";

            break;
            case 10:

                Karte = "9hearts";

            break;
            case 11:

                Karte = "9spades";

            break;
            case 12:

                Karte = "10clubs";

            break;
            case 13:

                Karte = "10diamonds";

            break;
            case 14:

                Karte = "10hearts";

            break;
            case 15:

                Karte = "10spades";

            break;

            case 16:

                Karte = "aceclubs";

            break;
            case 17:

                Karte = "acediamonds";

            break;
            case 18:

                Karte = "acehearts";

            break;
            case 19:

                Karte = "acespades";

            break;
            case 20:

                Karte = "jackclubs";

            break;
            case 21:

                Karte = "jackdiamonds";

            break;
            case 22:

                Karte = "jackhearts";

            break;
            case 23:

                Karte = "jackspades";

            break;
            case 24:

                Karte = "kingclubs";

            break;
            case 25:

                Karte = "kingdiamonds";

            break;
            case 26:

                Karte = "kinghearts";

            break;
            case 27:

                Karte = "kingspades";

            break;
            case 28:

                Karte = "queenclubs";

            break;
            case 29:

                Karte = "queendiamonds";

            break;
            case 30:

                Karte = "queenhearts";

            break;
            case 31:

                Karte = "queenspades";

            break;


        }

        System.out.println(Karte);
        return Karte;


    }


    public BufferedImage configStringToImage(String imageName) {


        BufferedImage image = null;


        if (imageName.equals("7clubs")) {

            System.out.println("7clubs");
            image = gp.karten.image_7_of_clubs;

        }
        if (imageName.equals("7diamonds")) {

            System.out.println("7diamonds");
            image = gp.karten.image_7_of_diamonds;

        }
        if (imageName.equals("7hearts")) {

            System.out.println("7hearts");
            image = gp.karten.image_7_of_hearts;

        }
        if (imageName.equals("7spades")) {

            System.out.println("7spades");
            image = gp.karten.image_7_of_spades;

        }
        if (imageName.equals("8clubs")) {

            System.out.println("8clubs");
            image = gp.karten.image_8_of_clubs;

        }
        if (imageName.equals("8diamonds")) {

            System.out.println("8diamonds");
            image = gp.karten.image_8_of_diamonds;

        }
        if (imageName.equals("8hearts")) {

            System.out.println("8hearts");
            image = gp.karten.image_8_of_hearts;

        }
        if (imageName.equals("8spades")) {

            System.out.println("8spades");
            image = gp.karten.image_8_of_spades;

        }
        if (imageName.equals("9clubs")) {

            System.out.println("9clubs");
            image = gp.karten.image_9_of_clubs;

        }
        if (imageName.equals("9diamonds")) {

            System.out.println("9diamonds");
            image = gp.karten.image_9_of_diamonds;

        }
        if (imageName.equals("9hearts")) {

            System.out.println("9hearts");
            image = gp.karten.image_9_of_hearts;

        }
        if (imageName.equals("9spades")) {

            System.out.println("9spades");
            image = gp.karten.image_9_of_spades;

        }
        if (imageName.equals("10clubs")) {

            System.out.println("10clubs");
            image = gp.karten.image_10_of_clubs;

        }
        if (imageName.equals("10diamonds")) {

            System.out.println("10diamonds");
            image = gp.karten.image_10_of_diamonds;

        }
        if (imageName.equals("10hearts")) {

            System.out.println("10hearts");
            image = gp.karten.image_10_of_hearts;

        }
        if (imageName.equals("10spades")) {

            System.out.println("10spades");
            image = gp.karten.image_10_of_spades;

        }
        if (imageName.equals("aceclubs")) {

            System.out.println("aceclubs");
            image = gp.karten.image_ace_of_clubs;

        }
        if (imageName.equals("acediamonds")) {

            System.out.println("acediamonds");
            image = gp.karten.image_ace_of_diamonds;

        }
        if (imageName.equals("acehearts")) {

            System.out.println("acehearts");
            image = gp.karten.image_ace_of_hearts;

        }
        if (imageName.equals("acespades")) {

            System.out.println("acespades");
            image = gp.karten.image_ace_of_spades2;

        }
        if (imageName.equals("jackclubs")) {

            System.out.println("jackclubs");
            image = gp.karten.image_jack_of_clubs2;

        }
        if (imageName.equals("jackdiamonds")) {

            System.out.println("jackdiamonds");
            image = gp.karten.image_jack_of_diamonds2;

        }
        if (imageName.equals("jackhearts")) {

            System.out.println("jackhearts");
            image = gp.karten.image_jack_of_hearts2;

        }
        if (imageName.equals("jackspades")) {

            System.out.println("jackspades");
            image = gp.karten.image_jack_of_spades2;

        }
        if (imageName.equals("kingclubs")) {

            System.out.println("kingclubs");
            image = gp.karten.image_king_of_clubs2;

        }
        if (imageName.equals("kingdiamonds")) {

            System.out.println("kingdiamonds");
            image = gp.karten.image_king_of_diamonds2;

        }
        if (imageName.equals("kinghearts")) {

            System.out.println("kinghearts");
            image = gp.karten.image_king_of_hearts2;

        }
        if (imageName.equals("kingspades")) {

            System.out.println("kingspades");
            image = gp.karten.image_king_of_spades2;

        }
        if (imageName.equals("queenclubs")) {

            System.out.println("queenclubs");
            image = gp.karten.image_queen_of_clubs2;

        }
        if (imageName.equals("queendiamonds")) {

            System.out.println("queendiamonds");
            image = gp.karten.image_queen_of_diamonds2;

        }
        if (imageName.equals("queenhearts")) {

            System.out.println("queenhearts");
            image = gp.karten.image_queen_of_hearts2;

        }
        if (imageName.equals("queenspades")) {

            System.out.println("queenspades");
            image = gp.karten.image_queen_of_spades2;


        }

        return image;

    }

}
