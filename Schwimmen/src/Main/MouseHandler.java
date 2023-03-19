package Main;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {


    GamePanel gp;
    Graphics2D g2;
    Color c;
    boolean KarteMitteLinks, KarteMitteMitte, KarteMitteRechts, KarteSpielerLinks, KarteSpielerMitte, KarteSpielerRechts = false;
    int sevorher, musicvorher;
    boolean doppelt = false;

    public MouseHandler(GamePanel gp) {

        this.gp = gp;

    }

    public void draw (Graphics2D g2) {

        this.g2 = g2;

    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {


        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        int fall = -1;


        if (gp.gameState == gp.optionsState) {

            //settings
            if (e.getX() < (gp.ui.settings.x + gp.ui.settings.width) && //kleiner als rechter rand
                    e.getX() > (gp.ui.settings.x) && //aber größer als linker rand
                    e.getY() < (gp.ui.settings.y + gp.ui.settings.height) && //kleiner als unterer rand
                    e.getY() > (gp.ui.settings.y)) //größer als oberer rand

            {
                    gp.playSE(6);
                    doppelt = true;
                    gp.gameState = gp.spielerState;

            }


        }


        if (gp.gameState == gp.spielerState) {

            //mittelinks
            if (e.getX() < (gp.ui.mittelinks.x + gp.ui.mittelinks.width) && //kleiner als rechter rand
                    e.getX() > (gp.ui.mittelinks.x) && //größer als linker rand
                    e.getY() < (gp.ui.mittelinks.y + gp.ui.mittelinks.height) && //kleiner als unterer rand
                    e.getY() > (gp.ui.mittelinks.y)) //größer als oberer rand


            {
                fall = 0;
            }



            //mittemitte
            if (e.getX() < (gp.ui.mittemitte.x + gp.ui.mittemitte.width) && //kleiner als rechter rand
                    e.getX() > (gp.ui.mittemitte.x) && //aber größer als linker rand
                    e.getY() < (gp.ui.mittemitte.y + gp.ui.mittemitte.height) && //kleiner als unterer rand
                    e.getY() > (gp.ui.mittemitte.y)) //größer als oberer rand


            {
                fall = 1;
            }


            //mitterechts
            if (e.getX() < (gp.ui.mitterechts.x + gp.ui.mitterechts.width) && //kleiner als rechter rand
                    e.getX() > (gp.ui.mitterechts.x) && //aber größer als linker rand
                    e.getY() < (gp.ui.mitterechts.y + gp.ui.mitterechts.height) && //kleiner als unterer rand
                    e.getY() > (gp.ui.mitterechts.y)) //größer als oberer rand


            {
                fall = 2;
            }

            //spielerlinks
            if (e.getX() < (gp.ui.spielerlinks.x + gp.ui.spielerlinks.width) && //kleiner als rechter rand
                    e.getX() > (gp.ui.spielerlinks.x) && //aber größer als linker rand
                    e.getY() < (gp.ui.spielerlinks.y + gp.ui.spielerlinks.height) && //kleiner als unterer rand
                    e.getY() > (gp.ui.spielerlinks.y)) //größer als oberer rand


            {
                fall = 3;
            }

            //spielermitte
            if (e.getX() < (gp.ui.spielermitte.x + gp.ui.spielermitte.width) && //kleiner als rechter rand
                    e.getX() > (gp.ui.spielermitte.x) && //aber größer als linker rand
                    e.getY() < (gp.ui.spielermitte.y + gp.ui.spielermitte.height) && //kleiner als unterer rand
                    e.getY() > (gp.ui.spielermitte.y)) //größer als oberer rand


            {
                fall = 4;
            }

            //spielerrechts
            if (e.getX() < (gp.ui.spielerrechts.x + gp.ui.spielerrechts.width) && //kleiner als rechter rand
                    e.getX() > (gp.ui.spielerrechts.x) && //aber größer als linker rand
                    e.getY() < (gp.ui.spielerrechts.y + gp.ui.spielerrechts.height) && //kleiner als unterer rand
                    e.getY() > (gp.ui.spielerrechts.y)) //größer als oberer rand

            {
                fall = 5;
            }

            //settings
            if (e.getX() < (gp.ui.settings.x + gp.ui.settings.width) && //kleiner als rechter rand
                    e.getX() > (gp.ui.settings.x) && //aber größer als linker rand
                    e.getY() < (gp.ui.settings.y + gp.ui.settings.height) && //kleiner als unterer rand
                    e.getY() > (gp.ui.settings.y)) //größer als oberer rand

            {
                fall = 6;

            }

            //sound
            if (e.getX() < (gp.ui.sound.x + gp.ui.sound.width) && //kleiner als rechter rand
                    e.getX() > (gp.ui.sound.x) && //aber größer als linker rand
                    e.getY() < (gp.ui.sound.y + gp.ui.sound.height) && //kleiner als unterer rand
                    e.getY() > (gp.ui.sound.y)) //größer als oberer rand

            {
                fall = 7;
            }

            //klopfen
            if (e.getX() < (gp.ui.klopfen.x + gp.ui.klopfen.width) && //kleiner als rechter rand
                    e.getX() > (gp.ui.klopfen.x) && //aber größer als linker rand
                    e.getY() < (gp.ui.klopfen.y + gp.ui.klopfen.height) && //kleiner als unterer rand
                    e.getY() > (gp.ui.klopfen.y)) //größer als oberer rand

            {
                fall = 8;
            }

            //schieben
            if (e.getX() < (gp.ui.schieben.x + gp.ui.schieben.width) && //kleiner als rechter rand
                    e.getX() > (gp.ui.schieben.x) && //aber größer als linker rand
                    e.getY() < (gp.ui.schieben.y + gp.ui.schieben.height) && //kleiner als unterer rand
                    e.getY() > (gp.ui.schieben.y)) //größer als oberer rand

            {
                fall = 9;
            }





            switch (fall)  {
                case -1 : System.out.println("Keine Karte"); break;

                case 0 :

                    if (KarteMitteLinks == false && gp.spieler.sperreMitte == false) {

                        KarteMitteLinks = true;

                    } else if (KarteMitteLinks == true) {

                        KarteMitteLinks = false;

                        if (KarteMitteLinks == false && KarteMitteMitte == false && KarteMitteRechts == false) {

                            gp.spieler.MitteIstAngeklickt = false;

                        }
                    }

                    break;


                case 1 :

                    if (KarteMitteMitte == false && gp.spieler.sperreMitte == false) {
                        KarteMitteMitte = true;
                    } else {

                        KarteMitteMitte = false;

                        if (KarteMitteLinks == false && KarteMitteMitte == false && KarteMitteRechts == false) {

                            gp.spieler.MitteIstAngeklickt = false;

                        }

                    }
                    break;


                case 2 :
                    if (KarteMitteRechts == false && gp.spieler.sperreMitte == false) {
                        KarteMitteRechts = true;
                    } else {

                        KarteMitteRechts = false;

                        if (KarteMitteLinks == false && KarteMitteMitte == false && KarteMitteRechts == false) {

                            gp.spieler.MitteIstAngeklickt = false;

                        }

                    }

                    break;


                case 3 :
                    if (KarteSpielerLinks == false && gp.spieler.sperreSpieler == false) {

                        KarteSpielerLinks = true;

                    } else {

                        KarteSpielerLinks = false;

                        if (KarteSpielerLinks == false && KarteSpielerMitte == false && KarteSpielerRechts == false) {

                            gp.spieler.SpielerIstAngeklickt = false;

                        }

                    }
                    break;


                case 4 :
                    if (KarteSpielerMitte == false && gp.spieler.sperreSpieler == false) {

                        KarteSpielerMitte = true;
                    } else {

                        KarteSpielerMitte = false;

                        if (KarteSpielerLinks == false && KarteSpielerMitte == false && KarteSpielerRechts == false) {

                            gp.spieler.SpielerIstAngeklickt = false;

                        }

                    }
                    break;


                case 5 :
                    if (KarteSpielerRechts == false && gp.spieler.sperreSpieler == false) {

                        KarteSpielerRechts = true;

                    } else {

                        KarteSpielerRechts = false;

                        if (KarteSpielerLinks == false && KarteSpielerMitte == false && KarteSpielerRechts == false) {

                            gp.spieler.SpielerIstAngeklickt = false;

                        }

                    }
                    break;


                case 6 :
                    System.out.println("settings");
                    if (doppelt == false) {

                        gp.playSE(6);
                        gp.gameState = gp.optionsState;
                    } else {
                        doppelt = false;
                    }
                    break;


                case 7 :
                    System.out.println("mute");
                    if (gp.ui.mute == false) {
                        gp.ui.mute = true;
                        sevorher = gp.se.volumeScale;
                        musicvorher = gp.music.volumeScale;
                        gp.se.volumeScale = 0;
                        gp.music.volumeScale = 0;
                        gp.music.checkVolume();

                    } else {
                        gp.ui.mute = false;
                        gp.se.volumeScale = sevorher;
                        gp.music.volumeScale = musicvorher;
                        gp.music.checkVolume();

                    }
                    break;

                case 8:


                    if (gp.spieler.klopfen == false) {
                        gp.playSE(3);
                        gp.spieler.klopfen = true;
                    } else {
                        gp.playSE(3);
                        gp.spieler.klopfen = false;
                    }


                    break;

                case 9:


                    if (gp.spieler.schieben == false) {
                        gp.playSE(3);
                        gp.spieler.schieben = true;
                    } else {
                        gp.playSE(3);
                        gp.spieler.schieben = false;
                    }

                    break;
            }

        }


    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

