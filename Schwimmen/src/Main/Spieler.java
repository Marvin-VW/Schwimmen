package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Spieler {

    GamePanel gp;
    Graphics2D g2;
    Font MaruMonica;

    public boolean schieben, klopfen, endgamephase, spielerraus = false;

    String auslöser = "";
    int auslöser2 = -1;
    boolean auslöserbestätigt ,sperre ,einunddreißig, blitz ,MitteIstAngeklickt , SpielerIstAngeklickt,sperreMitte,sperreSpieler,mitteMehrereTrigger ,spielerMehrereTrigger = false;

    int KartenwertSpielerLinks, KartenwertSpielerMitte, KartenwertSpielerRechts, schwarzgesammtSpieler, rotgesammtSpieler;

    String KartenFarbeSpielerLinks, KartenFarbeSpielerMitte, KartenFarbeSpielerRechts, KartenSymbolSpielerLinks, KartenSymbolSpielerMitte, KartemSymboleSpielerRechts;

    int schiebenmehrfach = 0;
    int schiebennotwendig = 4;
    int[] arrWerte;
    double SpielerSpezialfall;
    String[] arrFarben;
    String[] arrSymbol;
    int startpunkt = 3;


    public Spieler(GamePanel gp) {

        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/res/font/x12y16pxMaruMonica.ttf");
            MaruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void draw(Graphics2D g2) {

        this.g2 = g2;
    }


    //equal zu draw
    public void run() {

        if (spielerraus == true) {

            gp.playSE(5);
            gp.gameState = gp.endState;
            gp.ui.drawEndScreen(0);

        }

        if (gp.bot.bot1raus == true && gp.bot.bot2raus == true && gp.bot.bot3raus == true) {

            gp.playSE(4);
            gp.gameState = gp.endState;
            gp.ui.drawEndScreen(3);

        }

        if (gp.gameState == gp.spielerState) {

            gp.ui.backround();
            gp.karten.kartenzuteilen();


            //ein bot raus
            if (gp.bot.bot1raus == true || gp.bot.bot2raus == true || gp.bot.bot3raus == true) {

                schiebennotwendig = 3;

            }

            //zwei bots raus
            if (gp.bot.bot1raus == true && gp.bot.bot2raus == true || gp.bot.bot1raus == true && gp.bot.bot3raus == true || gp.bot.bot2raus == true && gp.bot.bot3raus == true) {

                schiebennotwendig = 2;

            }

            if (schiebenmehrfach == schiebennotwendig) {

                gp.karten.neueKartenMitte();
                schiebenmehrfach = 0;

            }


            spieleraktion();

        }

    }


    public void spieleraktion() {


        endgamephase(3);


        //wenn schieben nichts tauschen
        if (schieben == true) {

            ueberpruefenObSpezialfall();
            schiebenmehrfach++;
            gp.gameState = gp.botState;
            schieben = false;

        }


        //klopfen -> initialisiert endgame phase (auch für bots möglich) -> letzte runde
        if (klopfen == true) {

            schiebenmehrfach = 0;
            ueberpruefenObSpezialfall();
            endgamephase = true;
            endgamephase(3);
            gp.gameState = gp.botState;
            klopfen = false;

        }


        //normales tauschen
        if (gp.mouseH.KarteMitteLinks == true ||
                gp.mouseH.KarteMitteMitte == true ||
                gp.mouseH.KarteMitteRechts == true ||
                gp.mouseH.KarteSpielerLinks == true ||
                gp.mouseH.KarteSpielerMitte == true ||
                gp.mouseH.KarteSpielerRechts == true) {

            auswahl();

        }

    }


    public void auswahl() {

        MitteIstAngeklickt = false;
        SpielerIstAngeklickt = false;
        sperreMitte = false;
        sperreSpieler = false;
        //mitteMehrereTrigger = false;
        //spielerMehrereTrigger = false;

        g2.setColor(Color.WHITE);


        if (gp.mouseH.KarteMitteLinks == true) {

            if (sperreMitte == false) {

                if (gp.mouseH.KarteSpielerLinks == true && gp.mouseH.KarteSpielerMitte == false && gp.mouseH.KarteSpielerRechts == false && mitteMehrereTrigger == false||
                        gp.mouseH.KarteSpielerLinks == false && gp.mouseH.KarteSpielerMitte == true && gp.mouseH.KarteSpielerRechts == false && mitteMehrereTrigger == false||
                            gp.mouseH.KarteSpielerLinks == false && gp.mouseH.KarteSpielerMitte == false && gp.mouseH.KarteSpielerRechts == true && mitteMehrereTrigger == false) {

                    //normales tauschen

                    tauschen();

                }
            }

            if (MitteIstAngeklickt == true) {


                sperreSpieler = true; //weil evtl nur 2 karten angeklickt

                if (gp.mouseH.KarteMitteLinks == true && gp.mouseH.KarteMitteMitte == true && gp.mouseH.KarteMitteRechts == true) {


                    //wahrscheinlich mehrfach tauschen
                    mitteMehrereTrigger = true;
                    sperreSpieler = false;

                }
            }


            if (sperreMitte == false) {

                if (gp.gameState == gp.spielerState) {

                    g2.drawRoundRect(gp.ui.mittelinks.x - 8, gp.ui.mittelinks.y - 8, gp.ui.mittelinks.width + 20, gp.ui.mittelinks.height + 20, 25, 25);

                }

                MitteIstAngeklickt = true; //Mitte ist noch angeklickt

                if (gp.mouseH.KarteSpielerMitte == true && gp.mouseH.KarteSpielerRechts == true && gp.mouseH.KarteSpielerLinks == true) {

                    mehreretauschen();

                }

            }


        }


        if (gp.mouseH.KarteMitteMitte == true) {

            if (sperreMitte == false) {

                if (gp.mouseH.KarteSpielerLinks == true && gp.mouseH.KarteSpielerMitte == false && gp.mouseH.KarteSpielerRechts == false && mitteMehrereTrigger == false ||
                        gp.mouseH.KarteSpielerLinks == false && gp.mouseH.KarteSpielerMitte == true && gp.mouseH.KarteSpielerRechts == false && mitteMehrereTrigger == false||
                            gp.mouseH.KarteSpielerLinks == false && gp.mouseH.KarteSpielerMitte == false && gp.mouseH.KarteSpielerRechts == true && mitteMehrereTrigger == false) {


                    //normales tauschen

                    tauschen();

                }

            }

            if (MitteIstAngeklickt == true) {

                sperreSpieler = true; //weil evtl nur 2 karten angeklickt

                if (gp.mouseH.KarteMitteLinks == true && gp.mouseH.KarteMitteMitte == true && gp.mouseH.KarteMitteRechts == true) {


                    //wahrscheinlich mehrfach tauschen
                    mitteMehrereTrigger = true;
                    sperreSpieler = false;

                }
            }


            if (sperreMitte == false) {

                if (gp.gameState == gp.spielerState) {

                    g2.drawRoundRect(gp.ui.mittemitte.x - 8, gp.ui.mittemitte.y - 8, gp.ui.mittemitte.width + 20, gp.ui.mittemitte.height + 20, 25, 25);

                }

                MitteIstAngeklickt = true; //Mitte ist noch angeklickt

                if (gp.mouseH.KarteSpielerMitte == true && gp.mouseH.KarteSpielerRechts == true && gp.mouseH.KarteSpielerLinks == true) {

                    mehreretauschen();

                }

            }





        }


        if (gp.mouseH.KarteMitteRechts == true) {

            if (sperreMitte == false) {

                if (gp.mouseH.KarteSpielerLinks == true && gp.mouseH.KarteSpielerMitte == false && gp.mouseH.KarteSpielerRechts == false &&  mitteMehrereTrigger == false||
                        gp.mouseH.KarteSpielerLinks == false && gp.mouseH.KarteSpielerMitte == true && gp.mouseH.KarteSpielerRechts == false && mitteMehrereTrigger == false||
                            gp.mouseH.KarteSpielerLinks == false && gp.mouseH.KarteSpielerMitte == false && gp.mouseH.KarteSpielerRechts == true && mitteMehrereTrigger == false) {

                    //normales tauschen

                    tauschen();
                }
            }

            if (MitteIstAngeklickt == true) {

                sperreSpieler = true; //weil evtl nur 2 karten angeklickt

                if (gp.mouseH.KarteMitteLinks == true && gp.mouseH.KarteMitteMitte == true && gp.mouseH.KarteMitteRechts == true) {


                    //wahrscheinlich mehrfach tauschen
                    mitteMehrereTrigger = true;
                    sperreSpieler = false;

                }
            }


            if (sperreMitte == false) {

                if (gp.gameState == gp.spielerState) {

                    g2.drawRoundRect(gp.ui.mitterechts.x - 8, gp.ui.mitterechts.y - 8, gp.ui.mitterechts.width + 20, gp.ui.mitterechts.height + 20, 25, 25);

                }

                MitteIstAngeklickt = true; //Mitte ist noch angeklickt

                if (gp.mouseH.KarteSpielerMitte == true && gp.mouseH.KarteSpielerRechts == true && gp.mouseH.KarteSpielerLinks == true) {

                    mehreretauschen();

                }

            }





        }



        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------//



        if (gp.mouseH.KarteSpielerLinks == true) {

            if (sperreSpieler == false) {

                if (gp.mouseH.KarteMitteLinks == true && gp.mouseH.KarteMitteMitte == false && gp.mouseH.KarteMitteRechts == false && mitteMehrereTrigger == false ||
                        gp.mouseH.KarteMitteLinks == false && gp.mouseH.KarteMitteMitte == true && gp.mouseH.KarteMitteRechts == false && mitteMehrereTrigger == false ||
                            gp.mouseH.KarteMitteLinks == false && gp.mouseH.KarteMitteMitte == false && gp.mouseH.KarteMitteRechts == true && mitteMehrereTrigger == false) {

                    //normales tauschen

                    tauschen();
                }
            }

            if (SpielerIstAngeklickt == true) {

                sperreMitte = true; //weil evtl nur 2 karten angeklickt

                if (gp.mouseH.KarteSpielerMitte == true && gp.mouseH.KarteSpielerRechts == true && gp.mouseH.KarteSpielerLinks == true) {


                    //wahrscheinlich mehrfach tauschen
                    spielerMehrereTrigger = true;
                    sperreMitte = false;

                }
            }

            if (sperreSpieler == false) {

                if (gp.gameState == gp.spielerState) {

                    g2.drawRoundRect(gp.ui.spielerlinks.x - 8, gp.ui.spielerlinks.y - 8, gp.ui.spielerlinks.width + 20, gp.ui.spielerlinks.height + 20, 25, 25);

                }

                SpielerIstAngeklickt = true; //Mitte ist noch angeklickt

                if (gp.mouseH.KarteMitteLinks == true && gp.mouseH.KarteMitteMitte == true && gp.mouseH.KarteMitteRechts == true) {

                    mehreretauschen();

                }

            }
        }


        if (gp.mouseH.KarteSpielerMitte == true) {


            if (sperreSpieler == false) {

                if (gp.mouseH.KarteMitteLinks == true && gp.mouseH.KarteMitteMitte == false && gp.mouseH.KarteMitteRechts == false && mitteMehrereTrigger == false ||
                        gp.mouseH.KarteMitteLinks == false && gp.mouseH.KarteMitteMitte == true && gp.mouseH.KarteMitteRechts == false && mitteMehrereTrigger == false||
                            gp.mouseH.KarteMitteLinks == false && gp.mouseH.KarteMitteMitte == false && gp.mouseH.KarteMitteRechts == true && mitteMehrereTrigger == false) {

                    //normales tauschen

                    tauschen();
                }
            }

            if (SpielerIstAngeklickt == true) {

                sperreMitte = true; //weil evtl nur 2 karten angeklickt

                if (gp.mouseH.KarteSpielerMitte == true && gp.mouseH.KarteSpielerRechts == true && gp.mouseH.KarteSpielerLinks == true) {


                    //wahrscheinlich mehrfach tauschen
                    spielerMehrereTrigger = true;
                    sperreMitte = false;

                }
            }

            if (sperreSpieler == false) {

                if (gp.gameState == gp.spielerState) {

                    g2.drawRoundRect(gp.ui.spielermitte.x - 8, gp.ui.spielermitte.y - 8, gp.ui.spielermitte.width + 20, gp.ui.spielermitte.height + 20, 25, 25);

                }

                SpielerIstAngeklickt = true; //Mitte ist noch angeklickt

                if (gp.mouseH.KarteMitteLinks == true && gp.mouseH.KarteMitteMitte == true && gp.mouseH.KarteMitteRechts == true) {

                    mehreretauschen();

                }

            }

        }


        if (gp.mouseH.KarteSpielerRechts == true) {

            if (sperreSpieler == false) {

                if (gp.mouseH.KarteMitteLinks == true && gp.mouseH.KarteMitteMitte == false && gp.mouseH.KarteMitteRechts == false && mitteMehrereTrigger == false ||
                        gp.mouseH.KarteMitteLinks == false && gp.mouseH.KarteMitteMitte == true && gp.mouseH.KarteMitteRechts == false && mitteMehrereTrigger == false ||
                            gp.mouseH.KarteMitteLinks == false && gp.mouseH.KarteMitteMitte == false && gp.mouseH.KarteMitteRechts == true && mitteMehrereTrigger == false) {

                    //normales tauschen

                    tauschen();
                }
            }

            if (SpielerIstAngeklickt == true) {

                sperreMitte = true; //weil evtl nur 2 karten angeklickt

                if (gp.mouseH.KarteSpielerMitte == true && gp.mouseH.KarteSpielerRechts == true && gp.mouseH.KarteSpielerLinks == true) {


                    //wahrscheinlich mehrfach tauschen
                    spielerMehrereTrigger = true;
                    sperreMitte = false;

                }
            }

            if (sperreSpieler == false) {

                if (gp.gameState == gp.spielerState) {

                    g2.drawRoundRect(gp.ui.spielerrechts.x - 8, gp.ui.spielerrechts.y - 8, gp.ui.spielerrechts.width + 20, gp.ui.spielerrechts.height + 20, 25, 25);

                }

                SpielerIstAngeklickt = true; //Mitte ist noch angeklickt

                if (gp.mouseH.KarteMitteLinks == true && gp.mouseH.KarteMitteMitte == true && gp.mouseH.KarteMitteRechts == true) {

                    mehreretauschen();

                }

            }

        }


    }


    public void tauschen() {

        if (gp.gameState == gp.spielerState) {

            int fall = 0;
            // 0 : oben 1 unten 1
            if (gp.mouseH.KarteMitteLinks == true && gp.mouseH.KarteSpielerLinks == true) {

                g2.drawRoundRect(gp.ui.mittelinks.x - 8, gp.ui.mittelinks.y - 8, gp.ui.mittelinks.width + 20, gp.ui.mittelinks.height + 20, 25, 25);
                g2.drawRoundRect(gp.ui.spielerlinks.x - 8, gp.ui.spielerlinks.y - 8, gp.ui.spielerlinks.width + 20, gp.ui.spielerlinks.height + 20, 25, 25);

                fall = 0;
            }
            // 1 : oben 1 unten 2
            if (gp.mouseH.KarteMitteLinks == true && gp.mouseH.KarteSpielerMitte == true) {

                g2.drawRoundRect(gp.ui.mittelinks.x - 8, gp.ui.mittelinks.y - 8, gp.ui.mittelinks.width + 20, gp.ui.mittelinks.height + 20, 25, 25);
                g2.drawRoundRect(gp.ui.spielermitte.x - 8, gp.ui.spielermitte.y - 8, gp.ui.spielermitte.width + 20, gp.ui.spielermitte.height + 20, 25, 25);

                fall = 1;
            }
            // 2 : oben 1 unten 3
            if (gp.mouseH.KarteMitteLinks == true && gp.mouseH.KarteSpielerRechts == true) {

                g2.drawRoundRect(gp.ui.mittelinks.x - 8, gp.ui.mittelinks.y - 8, gp.ui.mittelinks.width + 20, gp.ui.mittelinks.height + 20, 25, 25);
                g2.drawRoundRect(gp.ui.spielerrechts.x - 8, gp.ui.spielerrechts.y - 8, gp.ui.spielerrechts.width + 20, gp.ui.spielerrechts.height + 20, 25, 25);

                fall = 2;
            }

            // 3 : oben 2 unten 1
            if (gp.mouseH.KarteMitteMitte == true && gp.mouseH.KarteSpielerLinks == true) {

                g2.drawRoundRect(gp.ui.mittemitte.x - 8, gp.ui.mittemitte.y - 8, gp.ui.mittemitte.width + 20, gp.ui.mittemitte.height + 20, 25, 25);
                g2.drawRoundRect(gp.ui.spielerlinks.x - 8, gp.ui.spielerlinks.y - 8, gp.ui.spielerlinks.width + 20, gp.ui.spielerlinks.height + 20, 25, 25);

                fall = 3;
            }
            // 4 : oben 2 unten 2
            if (gp.mouseH.KarteMitteMitte == true && gp.mouseH.KarteSpielerMitte == true) {

                g2.drawRoundRect(gp.ui.mittemitte.x - 8, gp.ui.mittemitte.y - 8, gp.ui.mittemitte.width + 20, gp.ui.mittemitte.height + 20, 25, 25);
                g2.drawRoundRect(gp.ui.spielermitte.x - 8, gp.ui.spielermitte.y - 8, gp.ui.spielermitte.width + 20, gp.ui.spielermitte.height + 20, 25, 25);

                fall = 4;
            }
            // 5 : oben 2 unten 3
            if (gp.mouseH.KarteMitteMitte == true && gp.mouseH.KarteSpielerRechts == true) {

                g2.drawRoundRect(gp.ui.mittemitte.x - 8, gp.ui.mittemitte.y - 8, gp.ui.mittemitte.width + 20, gp.ui.mittemitte.height + 20, 25, 25);
                g2.drawRoundRect(gp.ui.spielerrechts.x - 8, gp.ui.spielerrechts.y - 8, gp.ui.spielerrechts.width + 20, gp.ui.spielerrechts.height + 20, 25, 25);

                fall = 5;
            }


            // 6 : oben 3 unten 1
            if (gp.mouseH.KarteMitteRechts == true && gp.mouseH.KarteSpielerLinks == true) {

                g2.drawRoundRect(gp.ui.mitterechts.x - 8, gp.ui.mitterechts.y - 8, gp.ui.mitterechts.width + 20, gp.ui.mitterechts.height + 20, 25, 25);
                g2.drawRoundRect(gp.ui.spielerlinks.x - 8, gp.ui.spielerlinks.y - 8, gp.ui.spielerlinks.width + 20, gp.ui.spielerlinks.height + 20, 25, 25);

                fall = 6;
            }
            // 7 : oben 3 unten 2
            if (gp.mouseH.KarteMitteRechts == true && gp.mouseH.KarteSpielerMitte == true) {

                g2.drawRoundRect(gp.ui.mitterechts.x - 8, gp.ui.mitterechts.y - 8, gp.ui.mitterechts.width + 20, gp.ui.mitterechts.height + 20, 25, 25);
                g2.drawRoundRect(gp.ui.spielermitte.x - 8, gp.ui.spielermitte.y - 8, gp.ui.spielermitte.width + 20, gp.ui.spielermitte.height + 20, 25, 25);

                fall = 7;
            }
            // 8 : oben 3 unten 3
            if (gp.mouseH.KarteMitteRechts == true && gp.mouseH.KarteSpielerRechts == true) {

                g2.drawRoundRect(gp.ui.mitterechts.x - 8, gp.ui.mitterechts.y - 8, gp.ui.mitterechts.width + 20, gp.ui.mitterechts.height + 20, 25, 25);
                g2.drawRoundRect(gp.ui.spielerrechts.x - 8, gp.ui.spielerrechts.y - 8, gp.ui.spielerrechts.width + 20, gp.ui.spielerrechts.height + 20, 25, 25);

                fall = 8;
            }


            switch (fall) {
                case 0:

                    BufferedImage temp1 = gp.karten.hand[4][0];
                    BufferedImage temp2 = gp.karten.hand[3][0];

                    gp.karten.hand[4][0] = temp2;
                    gp.karten.hand[3][0] = temp1;

                    gp.mouseH.KarteMitteLinks = false;
                    gp.mouseH.KarteSpielerLinks = false;


                    break;

                case 1:

                    temp1 = gp.karten.hand[4][0];
                    temp2 = gp.karten.hand[3][1];

                    gp.karten.hand[4][0] = temp2;
                    gp.karten.hand[3][1] = temp1;

                    gp.mouseH.KarteMitteLinks = false;
                    gp.mouseH.KarteSpielerMitte = false;

                    break;
                case 2:

                    temp1 = gp.karten.hand[4][0];
                    temp2 = gp.karten.hand[3][2];

                    gp.karten.hand[4][0] = temp2;
                    gp.karten.hand[3][2] = temp1;

                    gp.mouseH.KarteMitteLinks = false;
                    gp.mouseH.KarteSpielerRechts = false;
                    break;

                case 3:

                    temp1 = gp.karten.hand[4][1];
                    temp2 = gp.karten.hand[3][0];

                    gp.karten.hand[4][1] = temp2;
                    gp.karten.hand[3][0] = temp1;

                    gp.mouseH.KarteMitteMitte = false;
                    gp.mouseH.KarteSpielerLinks = false;
                    break;

                case 4:

                    temp1 = gp.karten.hand[4][1];
                    temp2 = gp.karten.hand[3][1];

                    gp.karten.hand[4][1] = temp2;
                    gp.karten.hand[3][1] = temp1;

                    gp.mouseH.KarteMitteMitte = false;
                    gp.mouseH.KarteSpielerMitte = false;

                    break;

                case 5:

                    temp1 = gp.karten.hand[4][1];
                    temp2 = gp.karten.hand[3][2];

                    gp.karten.hand[4][1] = temp2;
                    gp.karten.hand[3][2] = temp1;

                    gp.mouseH.KarteMitteMitte = false;
                    gp.mouseH.KarteSpielerRechts = false;

                    break;

                case 6:

                    temp1 = gp.karten.hand[4][2];
                    temp2 = gp.karten.hand[3][0];

                    gp.karten.hand[4][2] = temp2;
                    gp.karten.hand[3][0] = temp1;

                    gp.mouseH.KarteMitteRechts = false;
                    gp.mouseH.KarteSpielerLinks = false;

                    break;

                case 7:

                    temp1 = gp.karten.hand[4][2];
                    temp2 = gp.karten.hand[3][1];

                    gp.karten.hand[4][2] = temp2;
                    gp.karten.hand[3][1] = temp1;

                    gp.mouseH.KarteMitteRechts = false;
                    gp.mouseH.KarteSpielerMitte = false;

                    break;

                case 8:

                    temp1 = gp.karten.hand[4][2];
                    temp2 = gp.karten.hand[3][2];

                    gp.karten.hand[4][2] = temp2;
                    gp.karten.hand[3][2] = temp1;

                    gp.mouseH.KarteMitteRechts = false;
                    gp.mouseH.KarteSpielerRechts = false;

                    break;

            }
        }

        defaultwerte();

        gp.timer.pause(0.8);

        schiebenmehrfach = 0;
        gp.ui.backround();
        gp.karten.kartenzuteilen();
        gp.gameState = gp.botState;
        ueberpruefenObSpezialfall();

        /*
        gp.gameState = gp.botState;

        gp.timer.pause(0.8);

        gp.ui.backround();
        gp.karten.kartenzuteilen();


        schiebenmehrfach = 0;
        ueberpruefenObSpezialfall();

        defaultwerte();

         */

    }


    public void mehreretauschen() {

        g2.drawRoundRect(gp.ui.mittelinks.x - 8, gp.ui.mittelinks.y - 8, gp.ui.mittelinks.width + 20, gp.ui.mittelinks.height + 20, 25, 25);
        g2.drawRoundRect(gp.ui.mittemitte.x - 8, gp.ui.mittemitte.y - 8, gp.ui.mittemitte.width + 20, gp.ui.mittemitte.height + 20, 25, 25);
        g2.drawRoundRect(gp.ui.mitterechts.x - 8, gp.ui.mitterechts.y - 8, gp.ui.mitterechts.width + 20, gp.ui.mitterechts.height + 20, 25, 25);
        g2.drawRoundRect(gp.ui.spielerlinks.x - 8, gp.ui.spielerlinks.y - 8, gp.ui.spielerlinks.width + 20, gp.ui.spielerlinks.height + 20, 25, 25);
        g2.drawRoundRect(gp.ui.spielermitte.x - 8, gp.ui.spielermitte.y - 8, gp.ui.spielermitte.width + 20, gp.ui.spielermitte.height + 20, 25, 25);
        g2.drawRoundRect(gp.ui.spielerrechts.x - 8, gp.ui.spielerrechts.y - 8, gp.ui.spielerrechts.width + 20, gp.ui.spielerrechts.height + 20, 25, 25);

        BufferedImage temp1 = gp.karten.hand[4][0];
            BufferedImage temp2 = gp.karten.hand[4][1];
            BufferedImage temp3 = gp.karten.hand[4][2];
            BufferedImage temp4 = gp.karten.hand[3][0];
            BufferedImage temp5 = gp.karten.hand[3][1];
            BufferedImage temp6 = gp.karten.hand[3][2];

            gp.karten.hand[3][0] = temp1;
            gp.karten.hand[3][1] = temp2;
            gp.karten.hand[3][2] = temp3;
            gp.karten.hand[4][0] = temp4;
            gp.karten.hand[4][1] = temp5;
            gp.karten.hand[4][2] = temp6;


            gp.gameState = gp.botState;

            gp.timer.pause(0.8);

            gp.ui.backround();
            gp.karten.kartenzuteilen();


            schiebenmehrfach = 0;
            endgamephase = true;
            endgamephase(3);
            ueberpruefenObSpezialfall();

            defaultwerte();


    }


    public void ueberpruefenObSpezialfall() {


        //wenn 31 pkt runde sofort vorbei, alle gleiche farben
        if (spielerKartenwerte() == 31) {

            einunddreißig = true;
            gp.timer.pause(2.5);
            gp.ui.drawZwischenScreen(3,31);
            gp.gameState = gp.zwischenState;
            //runde vorbei, wenigste punkte verliert herz


        }



        //wenn 33 blitz (3asse) -> sofort zu ende alle leben verloren

        KartenwertSpielerLinks = gp.karten.kartenwerterhalten(gp.karten.hand[3][0]);
        KartenwertSpielerMitte = gp.karten.kartenwerterhalten(gp.karten.hand[3][1]);
        KartenwertSpielerRechts =  gp.karten.kartenwerterhalten(gp.karten.hand[3][2]);


        if ((KartenwertSpielerLinks + KartenwertSpielerMitte + KartenwertSpielerRechts) == 33) {

            blitz = true;
            gp.timer.pause(2.5);
            gp.ui.drawZwischenScreen(3,33);
            gp.gameState = gp.zwischenState;
            //blitz, sofort zu ende, alle anderen leben verloren


        }


        //wenn spiel beendet, spieler mit niedrigster Punktzahl ein leben verloren (ings. 3)


        //kartenpkt
        //gleiche farbe oder gleicher rang
        //gleicher rang immer 30,5


    }


    public void endgamephase(int spielerwert) {


        //erst wenn methode nochmal getiggerd wurde:
        if (auslöserbestätigt == true) {

            sperre = false;

        }


        if (endgamephase == true && auslöserbestätigt == false) {

            //auslöser speichern

            if (spielerwert == 0) {

                auslöser = "bot1";
                auslöser2 = 0;

            }
            if (spielerwert == 1) {

                auslöser = "bot2";
                auslöser2 = 1;

            }
            if (spielerwert == 2) {

                auslöser = "bot3";
                auslöser2 = 2;

            }
            if (spielerwert == 3) {

                auslöser = "spieler";
                auslöser2 = 3;
                System.out.println("spieler gespeichert");

            }

            auslöserbestätigt = true;
            sperre = true;

        }


        if (endgamephase == true && sperre == false) {


            if (auslöser2 == spielerwert) {


                System.out.println("------------------------------------------");
                System.out.println("ende");
                System.out.println("------------------------------------------");

                gp.gameState = gp.zwischenState;
                gp.ui.drawZwischenScreen(auslöser2,0);


            }


        }


    }


    public double spielerKartenwerte() {


        //kartenwert von jeder karte
        KartenwertSpielerLinks = gp.karten.kartenwerterhalten(gp.karten.hand[3][0]);
        KartenwertSpielerMitte = gp.karten.kartenwerterhalten(gp.karten.hand[3][1]);
        KartenwertSpielerRechts =  gp.karten.kartenwerterhalten(gp.karten.hand[3][2]);

        KartenFarbeSpielerLinks = gp.karten.kartenfarbeerhalten(gp.karten.hand[3][0]);
        KartenFarbeSpielerMitte = gp.karten.kartenfarbeerhalten(gp.karten.hand[3][1]);
        KartenFarbeSpielerRechts =  gp.karten.kartenfarbeerhalten(gp.karten.hand[3][2]);

        KartenSymbolSpielerLinks = gp.karten.kartensymbolerhalten(gp.karten.hand[3][0]);
        KartenSymbolSpielerMitte = gp.karten.kartensymbolerhalten(gp.karten.hand[3][1]);
        KartemSymboleSpielerRechts = gp.karten.kartensymbolerhalten(gp.karten.hand[3][2]);

        arrWerte = new int[] {KartenwertSpielerLinks, KartenwertSpielerMitte, KartenwertSpielerRechts};

        arrFarben = new String[] {KartenFarbeSpielerLinks, KartenFarbeSpielerMitte, KartenFarbeSpielerRechts};

        arrSymbol = new String[] {KartenSymbolSpielerLinks, KartenSymbolSpielerMitte, KartemSymboleSpielerRechts};



        if (KartenwertSpielerLinks + KartenwertSpielerMitte + KartenwertSpielerRechts == 33) {

            SpielerSpezialfall = 33;
            return SpielerSpezialfall;

        }

        else if (gp.bot.dreißigeinhalbpkt(gp.karten.hand[3][0], gp.karten.hand[3][1], gp.karten.hand[3][2]) == 30.5) {

            SpielerSpezialfall = 30.5;
            return SpielerSpezialfall;

        } else if (gp.bot.dreißigeinhalbpkt(gp.karten.hand[3][0], gp.karten.hand[3][1], gp.karten.hand[3][2]) == 30.5) {

            SpielerSpezialfall = 31;
            return SpielerSpezialfall;}
        else {

            //schwarz
            schwarzgesammtSpieler = 0;
            int tempschwarz = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("club")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (tempschwarz > schwarzgesammtSpieler) {
                schwarzgesammtSpieler = tempschwarz;
            }
            tempschwarz = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("diamonds")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (tempschwarz > schwarzgesammtSpieler) {
                schwarzgesammtSpieler = tempschwarz;
            }
            tempschwarz = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("heart")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (tempschwarz > schwarzgesammtSpieler) {
                schwarzgesammtSpieler = tempschwarz;
            }
            tempschwarz = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("spades")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (tempschwarz > schwarzgesammtSpieler) {
                schwarzgesammtSpieler = tempschwarz;
            }
            tempschwarz = 0;


            //rot
            rotgesammtSpieler = 0;
            int temprot = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("club")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (temprot > rotgesammtSpieler) {
                rotgesammtSpieler = temprot;
            }
            temprot = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("diamonds")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (temprot > rotgesammtSpieler) {
                rotgesammtSpieler = temprot;
            }
            temprot = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("heart")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (temprot > rotgesammtSpieler) {
                rotgesammtSpieler = temprot;
            }
            temprot = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("spades")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (temprot > rotgesammtSpieler) {
                rotgesammtSpieler = temprot;
            }
            temprot = 0;


            if (rotgesammtSpieler > schwarzgesammtSpieler) {

                return rotgesammtSpieler;

            } else {

                return schwarzgesammtSpieler;

            }

        }

    }


    public void defaultwerte() {

        gp.mouseH.KarteMitteLinks = false;
        gp.mouseH.KarteMitteMitte = false;
        gp.mouseH.KarteMitteRechts = false;


        gp.mouseH.KarteSpielerMitte = false;
        gp.mouseH.KarteSpielerRechts = false;
        gp.mouseH.KarteSpielerLinks = false;


        MitteIstAngeklickt = false;
        SpielerIstAngeklickt = false;
        sperreMitte = false;
        sperreSpieler = false;

        mitteMehrereTrigger = false;
        spielerMehrereTrigger = false;
    }


}
