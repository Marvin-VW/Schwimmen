package Main;

import sun.util.resources.cldr.sr.CalendarData_sr_Cyrl_BA;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bot {


    GamePanel gp;
    Graphics2D g2;
    BufferedImage tempbot;
    BufferedImage tempmitte;
    public int ybot1;
    public int xmitte;
    public boolean pause = false;
    public int schwierigkeitsgrad = 0;

    boolean pausespieler, pausebotmarkierung, pausemarkiertekartentauschen, pausemarkierungweg, ersterrun, einunddreißig, blitz, bot1raus,bot2raus,bot3raus = false;

    //kartenwert von jeder karte
    double ersteKarteBotErsteMitteSchwarz, ersteKarteBotErsteMitteRot, ersteKarteBotZweiteMitteSchwarz, ersteKarteBotZweiteMitteRot, ersteKarteBotDritteMitteSchwarz,
    ersteKarteBotDritteMitteRot,

    ZweiteKarteBotErsteMitteSchwarz, ZweiteKarteBotErsteMitteRot, ZweiteKarteBotZweiteMitteSchwarz, ZweiteKarteBotZweiteMitteRot,
    ZweiteKarteBotDritteMitteSchwarz, ZweiteKarteBotDritteMitteRot,

    DritteKarteBotErsteMitteSchwarz, DritteKarteBotErsteMitteRot, DritteKarteBotZweiteMitteSchwarz, DritteKarteBotZweiteMitteRot, DritteKarteBotDritteMitteSchwarz,
    DritteKarteBotDritteMitteRot, aktuellePunkte = 0;

    double BotSpezialfall;

    int alleTauschenWertSchwarz, alleTauschenWertRot, KartenwertBotLinks, KartenwertBotMitte, KartenwertBotRechts, KartenwertMitteLinks, KartenwertMitteMitte, KartenwertMitteRechts;

    String KartenFarbeBotLinks, KartenFarbeBotMitte, KartenFarbeBotRechts, KartenFarbeMitteLinks, KartenFarbeMitteMitte, KartenFarbeMitteRechts,
        KartenSymbolBotLinks, KartenSymbolBotMitte, KartenSymbolBotRechts, KartenSymbolMitteLinks, KartenSymbolMitteMitte, KartenSymbolMitteRechts;

    int[] arrWerte;

    String[] arrFarben;
    String[] arrSymbol;

    int schwarzgesammtBot, rotgesammtBot, schwarzgesammtMitte, rotgesammtMitte, bot, botStelle1, mitteStelle1 = 0;

    boolean alletauschen, schieben, klopfen = false;

    int random;

    int randombot;
    int randommitte;

    public Bot(GamePanel gp) {

        this.gp = gp;

    }


    public void draw (Graphics2D g2) {

        this.g2 = g2;

    }


    //bot algorithm um methoden aufzurufen und pausen zu initialisieren
    public void computer() {

        System.out.println("bot start");

        gp.ui.backround();
        gp.karten.kartenzuteilen();

        if (schieben == true) {

            schiebenmalen();

        }

        if (klopfen == true) {

            klopfenmalen();

        }


        if (bot1raus == true && bot2raus == true && bot3raus == true) {

            gp.gameState = gp.endState;
            gp.ui.drawEndScreen(3);

        }


        //ein bot raus
        if (bot1raus == true || bot2raus == true || bot3raus == true) {

            gp.spieler.schiebennotwendig = 3;

        }

        //zwei bots raus
        if (bot1raus == true && bot2raus == true || bot1raus == true && bot3raus == true || bot2raus == true && bot3raus == true) {

            gp.spieler.schiebennotwendig = 2;

        }

        if (gp.spieler.schiebenmehrfach == gp.spieler.schiebennotwendig) {

            gp.karten.neueKartenMitte();
            gp.spieler.schiebenmehrfach = 0;

        }


        //falls 0 leben
        if(bot == 0 && bot1raus == true) {

            bot++;

        }
        if(bot == 1 && bot2raus == true) {

            bot++;

        }
        if(bot == 2 && bot3raus == true) {

            bot++;

        }

        gp.spieler.endgamephase(bot);
        ueberpruefenObSpezialfall(bot);

        if (bot == 3) {

            //spieler
            gp.gameState = gp.spielerState;
            System.out.println("Spieler");
            ersterrun = false;
            bot = 0;
            pausespieler = false;
            pausebotmarkierung = false;
            pausemarkiertekartentauschen = false;
            pausemarkierungweg = false;

        }

        if (gp.gameState == gp.botState) {

            if (ersterrun == false && pause == false) {

                System.out.println("bot ende");

                pause = true;
                gp.timer.pause(1);

                ersterrun = true;

            }


            System.out.println("bot: "+bot);

            //spieler
            if (pausespieler == false && pause == false) {


                pause = true;
                gp.timer.pause(0.6);

                pausespieler = true;

            }



            //bot markierung 90
            if (pausespieler == true && pausebotmarkierung == false && pause == false) {
                //markierung

                    System.out.println("Bot markieren");

                    random = (int) (Math.random() * 6);


                    if (random > schwierigkeitsgrad) {

                        randombot = (int) (Math.random() * 3);
                        randommitte = (int) (Math.random() * 3);

                        if (gp.ui.leicht == false) {

                            kartenUmdrehen(bot, randombot, randommitte);
                            pause = true;
                            gp.timer.pause(1);

                        }

                        randomMarkieren(bot, randombot, randommitte);

                    } else {

                        //bot algorithm rechnet aus
                        int[][] kartenposition = BotAlgorithm(bot);

                        botStelle1 = kartenposition[1][0];
                        mitteStelle1 = kartenposition[2][0];


                        if (schieben == false && klopfen == false) {

                            gp.spieler.schiebenmehrfach = 0;

                            if (gp.ui.leicht == false) {

                                kartenUmdrehen(bot, botStelle1, mitteStelle1);
                                pause = true;
                                gp.timer.pause(1);

                            }

                            BotAlgorithmMarkieren(bot, botStelle1, mitteStelle1);

                        } else if (schieben == true) {

                            gp.spieler.schiebenmehrfach++;

                            schiebenmalen();

                        }

                            else if (klopfen == true) {

                                klopfenmalen();
                                gp.playSE(8);

                        }


                    }

                pause = true;
                gp.timer.pause(1.5);


                pausebotmarkierung = true;

            }


            //bot tauscht markierte karten 90
            if (pausespieler == true && pausebotmarkierung == true && pausemarkiertekartentauschen == false && pause == false) {
                //tauschen

                if (schieben == false && klopfen == false) {

                    if (random > schwierigkeitsgrad) {

                        randomTauschen(bot, randombot, randommitte);

                    } else {

                        BotAlgorithmTauschen(bot, botStelle1, mitteStelle1);

                    }

                }

                pause = true;
                gp.timer.pause(1.5);


                pausemarkiertekartentauschen = true;

            }

            //markierung weg 50
            if (pausespieler == true && pausebotmarkierung == true && pausemarkiertekartentauschen == true && pausemarkierungweg == false && pause == false) {
                //markierung weg
                gp.ui.backround();
                gp.karten.kartenzuteilen();

                pause = true;
                gp.timer.pause(0.6);

                pausemarkierungweg = true;

            }

            if (pausespieler == true && pausebotmarkierung == true && pausemarkiertekartentauschen == true && pausemarkierungweg == true && pause == false) {


                ueberpruefenObSpezialfall(bot);

                bot++;

                schieben = false;
                klopfen = false;
                pausespieler = false;
                pausebotmarkierung = false;
                pausemarkiertekartentauschen = false;
                pausemarkierungweg = false;
                ersterrun = false;

            }

        }


    }


    //gibt karten die getauscht werden müssen bei aufruf im array zurück
    public int[][] BotAlgorithm(int bot) {


        //kartenwert von jeder karte
        KartenwertBotLinks = gp.karten.kartenwerterhalten(gp.karten.hand[bot][0]);
        KartenwertBotMitte = gp.karten.kartenwerterhalten(gp.karten.hand[bot][1]);
        KartenwertBotRechts =  gp.karten.kartenwerterhalten(gp.karten.hand[bot][2]);
        KartenwertMitteLinks =  gp.karten.kartenwerterhalten(gp.karten.hand[4][0]);
        KartenwertMitteMitte = gp.karten.kartenwerterhalten(gp.karten.hand[4][1]);
        KartenwertMitteRechts = gp.karten.kartenwerterhalten(gp.karten.hand[4][2]);


        KartenFarbeBotLinks = gp.karten.kartenfarbeerhalten(gp.karten.hand[bot][0]);
        KartenFarbeBotMitte = gp.karten.kartenfarbeerhalten(gp.karten.hand[bot][1]);
        KartenFarbeBotRechts =  gp.karten.kartenfarbeerhalten(gp.karten.hand[bot][2]);
        KartenFarbeMitteLinks =  gp.karten.kartenfarbeerhalten(gp.karten.hand[4][0]);
        KartenFarbeMitteMitte = gp.karten.kartenfarbeerhalten(gp.karten.hand[4][1]);
        KartenFarbeMitteRechts = gp.karten.kartenfarbeerhalten(gp.karten.hand[4][2]);

        KartenSymbolBotLinks = gp.karten.kartensymbolerhalten(gp.karten.hand[bot][0]);
        KartenSymbolBotMitte = gp.karten.kartensymbolerhalten(gp.karten.hand[bot][1]);
        KartenSymbolBotRechts =  gp.karten.kartensymbolerhalten(gp.karten.hand[bot][2]);
        KartenSymbolMitteLinks =  gp.karten.kartensymbolerhalten(gp.karten.hand[4][0]);
        KartenSymbolMitteMitte = gp.karten.kartensymbolerhalten(gp.karten.hand[4][1]);
        KartenSymbolMitteRechts = gp.karten.kartensymbolerhalten(gp.karten.hand[4][2]);


        arrWerte = new int[] {KartenwertBotLinks, KartenwertBotMitte, KartenwertBotRechts, KartenwertMitteLinks, KartenwertMitteMitte,
                KartenwertMitteRechts};

        arrFarben = new String[] {KartenFarbeBotLinks, KartenFarbeBotMitte, KartenFarbeBotRechts, KartenFarbeMitteLinks, KartenFarbeMitteMitte,
                KartenFarbeMitteRechts};

        arrSymbol = new String[] {KartenSymbolBotLinks, KartenSymbolBotMitte, KartenSymbolBotRechts, KartenSymbolMitteLinks, KartenSymbolMitteMitte,
                KartenSymbolMitteRechts};



        //--------------------------------------------------------------------------------------------------------------------------------------//

        //Werte für alle tauschen
        BotAlgorithmAlleTauschen();

        //--------------------------------------------------------------------------------------------------------------------------------------//

        //Werte für einzelne tauschen
        BotAlgorithmErsteKarte();
        BotAlgorithmZweiteKarte();
        BotAlgorithmDritteKarte();

        //--------------------------------------------------------------------------------------------------------------------------------------//

        //werte für keine tauschen
        aktuellePunkte = finaleKartenwerte(bot);
        System.out.println("aktuellePunkte"+aktuellePunkte);

        //--------------------------------------------------------------------------------------------------------------------------------------//

            double[] arr = {ersteKarteBotErsteMitteSchwarz, ersteKarteBotErsteMitteRot, ersteKarteBotZweiteMitteSchwarz, ersteKarteBotZweiteMitteRot, ersteKarteBotDritteMitteSchwarz,
                    ersteKarteBotDritteMitteRot, ZweiteKarteBotErsteMitteSchwarz, ZweiteKarteBotErsteMitteRot, ZweiteKarteBotZweiteMitteSchwarz, ZweiteKarteBotZweiteMitteRot,
                    ZweiteKarteBotDritteMitteSchwarz, ZweiteKarteBotDritteMitteRot, DritteKarteBotErsteMitteSchwarz, DritteKarteBotErsteMitteRot, DritteKarteBotZweiteMitteSchwarz,
                    DritteKarteBotZweiteMitteRot, DritteKarteBotDritteMitteSchwarz, DritteKarteBotDritteMitteRot, alleTauschenWertSchwarz, alleTauschenWertRot, aktuellePunkte};

            double curMax = arr[0];

            for (int h = 1; h < arr.length; h++) {
                if (arr[h] > curMax) {
                    curMax = arr[h];
                }
            }


            System.out.println("Maximum " + curMax);




        int Stelle = 0;

            for (int h = 0; h < arr.length; h++) {
                if (arr[h] == curMax) {
                    Stelle = h;
                }
            }


            int[][] kartenposition = new int[3][1];


        if (aktuellePunkte > 28) {

            //klopfen
            System.out.println("klopfen");
            klopfen = true;
            gp.spieler.endgamephase = true;
            gp.spieler.endgamephase(bot);


        } else {

            switch (Stelle) {

                case 0:
                    System.out.println("ersteKarteBotErsteMitteSchwarz");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 0;
                    kartenposition[2][0] = 0;

                    break;

                case 1:
                    System.out.println("ersteKarteBotErsteMitteRot");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 0;
                    kartenposition[2][0] = 0;

                    break; //ersteKarteBotErsteMitteRot

                case 2:
                    System.out.println("ersteKarteBotZweiteMitteSchwarz");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 0;
                    kartenposition[2][0] = 1;

                    break; //ersteKarteBotZweiteMitteSchwarz

                case 3:
                    System.out.println("ersteKarteBotZweiteMitteRot");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 0;
                    kartenposition[2][0] = 1;

                    break; //ersteKarteBotZweiteMitteRot

                case 4:
                    System.out.println("ersteKarteBotDritteMitteSchwarz");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 0;
                    kartenposition[2][0] = 2;

                    break; //ersteKarteBotDritteMitteSchwarz

                case 5:
                    System.out.println("ersteKarteBotDritteMitteRot");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 0;
                    kartenposition[2][0] = 2;

                    break; //ersteKarteBotDritteMitteRot

                case 6:
                    System.out.println("ZweiteKarteBotErsteMitteSchwarz");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 1;
                    kartenposition[2][0] = 0;

                    break; //ZweiteKarteBotErsteMitteSchwarz

                case 7:
                    System.out.println("ZweiteKarteBotErsteMitteRot");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 1;
                    kartenposition[2][0] = 0;

                    break; //ZweiteKarteBotErsteMitteRot

                case 8:
                    System.out.println("ZweiteKarteBotZweiteMitteSchwarz");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 1;
                    kartenposition[2][0] = 1;

                    break; //ZweiteKarteBotZweiteMitteSchwarz

                case 9:
                    System.out.println("ZweiteKarteBotZweiteMitteRot");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 1;
                    kartenposition[2][0] = 1;

                    break; //ZweiteKarteBotZweiteMitteRot

                case 10:
                    System.out.println("ZweiteKarteBotDritteMitteSchwarz");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 1;
                    kartenposition[2][0] = 2;

                    break; //ZweiteKarteBotDritteMitteSchwarz

                case 11:
                    System.out.println("ZweiteKarteBotDritteMitteRot");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 1;
                    kartenposition[2][0] = 2;

                    break; //ZweiteKarteBotDritteMitteRot

                case 12:
                    System.out.println("DritteKarteBotErsteMitteSchwarz");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 2;
                    kartenposition[2][0] = 0;

                    break; //DritteKarteBotErsteMitteSchwarz

                case 13:
                    System.out.println("DritteKarteBotErsteMitteRot");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 2;
                    kartenposition[2][0] = 0;

                    break; //eDritteKarteBotErsteMitteRot

                case 14:
                    System.out.println("DritteKarteBotZweiteMitteSchwarz");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 2;
                    kartenposition[2][0] = 1;

                    break; //DritteKarteBotZweiteMitteSchwarz

                case 15:
                    System.out.println("DritteKarteBotZweiteMitteRot");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 2;
                    kartenposition[2][0] = 1;

                    break; //DritteKarteBotZweiteMitteRot

                case 16:
                    System.out.println("DritteKarteBotDritteMitteSchwarz");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 2;
                    kartenposition[2][0] = 2;

                    break; //DritteKarteBotDritteMitteSchwarz

                case 17:
                    System.out.println("DritteKarteBotDritteMitteRot");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 2;
                    kartenposition[2][0] = 2;

                    break; //DritteKarteBotDritteMitteRot

                case 18:
                    System.out.println("alleTauschenWertSchwarz");
                    kartenposition[0][0] = 3;
                    kartenposition[1][0] = 3;
                    kartenposition[2][0] = bot;

                    break; //alleTauschenWertSchwarz


                case 19:
                    System.out.println("alleTauschenWertRot");
                    kartenposition[0][0] = bot;
                    kartenposition[1][0] = 3;
                    kartenposition[2][0] = 3;

                    break; //alleTauschenWertRot

                case 20:
                    System.out.println("schieben");
                    schieben = true;

                    break;


            }
        }


        return kartenposition;


    }


    //zufälliges markieren von Karten
    public void randomMarkieren(int bot, int randombot, int randommitte) {

        g2.setColor(Color.white);

        //mitte
        xmitte = gp.tileSize * 9 + ((gp.tileSize * 3) * randommitte);
        g2.drawRoundRect(xmitte - 8, (int) (gp.tileSize*7.64) - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);

        if (bot == 0) {

            ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * randombot);
            g2.drawRoundRect(gp.tileSize * 3 - 8, ybot1 - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);

        }
        if (bot == 1) {

            int xbot2 = gp.tileSize*9+ ((gp.tileSize*3)*randombot);
            g2.drawRoundRect(xbot2 - 8, gp.tileSize*1 - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);

        }
        if (bot == 2) {

            ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * randombot);
            g2.drawRoundRect(gp.tileSize * 21 - 8, ybot1 - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);

        }


    }


    //zufälliges tauschen von Karten
    public void randomTauschen(int bot, int randombot, int randommitte) {

        System.out.println("Random tauschen mit Bot " + bot);

        //temp
        tempbot = gp.karten.hand[bot][randombot];
        tempmitte = gp.karten.hand[4][randommitte];

        //tauschen im array
        gp.karten.hand[bot][randombot] = tempmitte;
        gp.karten.hand[4][randommitte] = tempbot;

        //evtl. pause

        //mitte
        xmitte = gp.tileSize * 9 + ((gp.tileSize * 3) * randommitte);
        g2.drawImage(gp.karten.hand[4][randommitte], xmitte, (int) (gp.tileSize*7.64), gp.tileSize * 2, gp.tileSize * 3, null);

        if (bot == 0) {

            ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * randombot);
            g2.drawImage(tempmitte, gp.tileSize * 3, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);

        }
        if (bot == 1) {

            int xbot2 = gp.tileSize*9+ ((gp.tileSize*3)*randombot);
            g2.drawImage(tempmitte, xbot2,gp.tileSize*1,gp.tileSize*2,gp.tileSize*3, null);

        }
        if (bot == 2) {

            ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * randombot);
            g2.drawImage(tempmitte, gp.tileSize * 21, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);

        }

        defaultWerte();

    }


    //dreht karten von bots um, falls schwierigkeit nicht leicht ist
    public void kartenUmdrehen(int bot, int botStelle, int mitteStelle) {




        if (mitteStelle != 3 && botStelle != 3) {


            xmitte = gp.tileSize * 9 + ((gp.tileSize * 3) * mitteStelle);
            g2.drawImage(gp.karten.hand[4][mitteStelle], xmitte, (int) (gp.tileSize*7.64), gp.tileSize * 2, gp.tileSize * 3, null);

            if (bot == 0) {

                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);
                g2.drawImage(gp.karten.hand[bot][botStelle], gp.tileSize * 3, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);

            }
            if (bot == 1) {

                int xbot2 = gp.tileSize*9+ ((gp.tileSize*3)*botStelle);
                g2.drawImage(gp.karten.hand[bot][botStelle], xbot2,gp.tileSize*1,gp.tileSize*2,gp.tileSize*3, null);

            }
            if (bot == 2) {

                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);
                g2.drawImage(gp.karten.hand[bot][botStelle], gp.tileSize * 21, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);

            }

        } else {



            if (bot == 0) {

                botStelle = 0;
                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);
                g2.drawImage(gp.karten.hand[bot][botStelle], gp.tileSize * 3, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);

                botStelle++;
                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);

                g2.drawImage(gp.karten.hand[bot][botStelle], gp.tileSize * 3, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);

                botStelle++;
                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);

                g2.drawImage(gp.karten.hand[bot][botStelle], gp.tileSize * 3, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);
            }

            if (bot == 1) {

                botStelle = 0;
                int xbot2 = gp.tileSize * 9 + ((gp.tileSize * 3) * botStelle);

                g2.drawImage(gp.karten.hand[bot][botStelle], xbot2, gp.tileSize * 1, gp.tileSize * 2, gp.tileSize * 3, null);

                botStelle++;
                xbot2 = gp.tileSize * 9 + ((gp.tileSize * 3) * botStelle);

                g2.drawImage(gp.karten.hand[bot][botStelle], xbot2, gp.tileSize * 1, gp.tileSize * 2, gp.tileSize * 3, null);

                botStelle++;
                xbot2 = gp.tileSize * 9 + ((gp.tileSize * 3) * botStelle);

                g2.drawImage(gp.karten.hand[bot][botStelle], xbot2, gp.tileSize * 1, gp.tileSize * 2, gp.tileSize * 3, null);
            }

            if (bot == 2) {


                botStelle = 0;
                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);

                g2.drawImage(gp.karten.hand[bot][botStelle], gp.tileSize * 21, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);

                botStelle++;
                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);

                g2.drawImage(gp.karten.hand[bot][botStelle], gp.tileSize * 21, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);

                botStelle++;
                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);

                g2.drawImage(gp.karten.hand[bot][botStelle], gp.tileSize * 21, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);
            }




            //mitte
            mitteStelle = 0;
            xmitte = gp.tileSize * 9 + ((gp.tileSize * 3) * mitteStelle);

            g2.drawImage(gp.karten.hand[4][mitteStelle], xmitte, (int) (gp.tileSize*7.64), gp.tileSize * 2, gp.tileSize * 3, null);

            mitteStelle++;
            xmitte = gp.tileSize * 9 + ((gp.tileSize * 3) * mitteStelle);

            g2.drawImage(gp.karten.hand[4][mitteStelle], xmitte, (int) (gp.tileSize*7.64), gp.tileSize * 2, gp.tileSize * 3, null);

            mitteStelle++;
            xmitte = gp.tileSize * 9 + ((gp.tileSize * 3) * mitteStelle);

            g2.drawImage(gp.karten.hand[4][mitteStelle], xmitte, (int) (gp.tileSize*7.64), gp.tileSize * 2, gp.tileSize * 3, null);


        }





    }


    public void schiebenmalen() {


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.scale * 8F));
        g2.setColor(Color.white);

        if (bot == 0) {

            g2.drawString("Schieben", (int) (gp.tileSize*0.85), (int) (gp.tileSize*10.5));

        }

        if (bot == 1) {

            String text = "Schieben";
            g2.drawString("Schieben", (int) (gp.ui.getXforCenteredText(text)+gp.tileSize*2.3), gp.tileSize*5);

        }

        if (bot == 2) {

            g2.drawString("Schieben",(int) (gp.tileSize * 23.4),(int) (gp.tileSize*10.5));

        }

    }


    public void klopfenmalen() {


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.scale * 8F));
        g2.setColor(Color.white);

        if (bot == 0) {

            g2.drawString("Klopfen", (int) (gp.tileSize), (int) (gp.tileSize*8));

        }

        if (bot == 1) {

            String text = "Klopfen";
            g2.drawString("Klopfen", (int) (gp.ui.getXforCenteredText(text)-gp.tileSize*2.1), gp.tileSize*5);

        }

        if (bot == 2) {

            g2.drawString("Klopfen",(int) (gp.tileSize * 23.55),(int) (gp.tileSize*8));

        }

    }


    //30,5 pkt
    public double dreißigeinhalbpkt (BufferedImage Kartelinks, BufferedImage KarteMitte, BufferedImage KarteRechts) {


        double ausgabe = 0;


        if (gp.karten.karteerhalteneinzeln(Kartelinks).equals("7") && gp.karten.karteerhalteneinzeln(KarteMitte).equals("7") && gp.karten.karteerhalteneinzeln(KarteRechts).equals("7")) {


            //30,5
            ausgabe = 30.5;
            return ausgabe;


        }

        if (gp.karten.karteerhalteneinzeln(Kartelinks).equals("8") && gp.karten.karteerhalteneinzeln(KarteMitte).equals("8") && gp.karten.karteerhalteneinzeln(KarteRechts).equals("8")) {


            //30,5
            ausgabe = 30.5;
            return ausgabe;

        }

        if (gp.karten.karteerhalteneinzeln(Kartelinks).equals("9") && gp.karten.karteerhalteneinzeln(KarteMitte).equals("9") && gp.karten.karteerhalteneinzeln(KarteRechts).equals("9")) {


            //30,5
            ausgabe = 30.5;
            return ausgabe;

        }

        if (gp.karten.karteerhalteneinzeln(Kartelinks).equals("10") && gp.karten.karteerhalteneinzeln(KarteMitte).equals("10") && gp.karten.karteerhalteneinzeln(KarteRechts).equals("10")) {


            //30,5
            ausgabe = 30.5;
            return ausgabe;

        }

        if (gp.karten.karteerhalteneinzeln(Kartelinks).equals("king") && gp.karten.karteerhalteneinzeln(KarteMitte).equals("king") && gp.karten.karteerhalteneinzeln(KarteRechts).equals("king")) {


            //30,5
            ausgabe = 30.5;
            return ausgabe;

        }

        if (gp.karten.karteerhalteneinzeln(Kartelinks).equals("queen") && gp.karten.karteerhalteneinzeln(KarteMitte).equals("queen") && gp.karten.karteerhalteneinzeln(KarteRechts).equals("queen")) {


            //30,5
            ausgabe = 30.5;
            return ausgabe;

        }

        if (gp.karten.karteerhalteneinzeln(Kartelinks).equals("jack") && gp.karten.karteerhalteneinzeln(KarteMitte).equals("jack") && gp.karten.karteerhalteneinzeln(KarteRechts).equals("jack")) {


            //30,5
            ausgabe = 30.5;
            return ausgabe;

        }




        ausgabe = 0;
        return ausgabe;





    }


    //berechnet die punktzahl wenn alle getauscht werden
    public void BotAlgorithmAlleTauschen() {


        schwarzgesammtBot = 0;
        int tempschwarz = 0;


        //bot gesammt
        for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

            if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("club")) {

                tempschwarz = tempschwarz + arrWerte[kartenPosition];

            }
        }

        if (tempschwarz > schwarzgesammtBot) {
            schwarzgesammtBot = tempschwarz;
        }
        tempschwarz = 0;



        for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

            if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("diamonds")) {

                tempschwarz = tempschwarz + arrWerte[kartenPosition];

            }
        }

        if (tempschwarz > schwarzgesammtBot) {
            schwarzgesammtBot = tempschwarz;
        }
        tempschwarz = 0;


        for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

            if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("heart")) {

                tempschwarz = tempschwarz + arrWerte[kartenPosition];

            }
        }

        if (tempschwarz > schwarzgesammtBot) {
                    schwarzgesammtBot = tempschwarz;
        }
        tempschwarz = 0;


        for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

            if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("spades")) {

                tempschwarz = tempschwarz + arrWerte[kartenPosition];

            }
        }

         if (tempschwarz > schwarzgesammtBot) {
                schwarzgesammtBot = tempschwarz;
         }
        tempschwarz = 0;



        rotgesammtBot = 0;
        int temprot = 0;

        //bot gesammt
        for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

            if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("club")) {

                temprot = temprot + arrWerte[kartenPosition];

            }
        }

        if (temprot > rotgesammtBot) {
            rotgesammtBot = temprot;
        }
        temprot = 0;



        for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

            if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("diamonds")) {

                temprot = temprot + arrWerte[kartenPosition];

            }
        }

        if (temprot > rotgesammtBot) {
            rotgesammtBot = temprot;
        }
        temprot = 0;


        for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

            if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("heart")) {

                temprot = temprot + arrWerte[kartenPosition];

            }
        }

        if (temprot > rotgesammtBot) {
            rotgesammtBot = temprot;
        }
        temprot = 0;


        for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

            if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("spades")) {

                temprot = temprot + arrWerte[kartenPosition];

            }
        }

        if (temprot > rotgesammtBot) {
            rotgesammtBot = temprot;
        }
        temprot = 0;



        //---------------------------------------------------------------------------------------------------------------//


        //mitte
        schwarzgesammtMitte = 0;
        tempschwarz = 0;

        //mitte gesammt
        for (int kartenPosition = 3; kartenPosition < 6; kartenPosition++) {

            if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("club")) {

                tempschwarz = tempschwarz + arrWerte[kartenPosition];

            }
        }

        if (tempschwarz > schwarzgesammtMitte) {
            schwarzgesammtMitte = tempschwarz;
        }
        tempschwarz = 0;



        for (int kartenPosition = 3; kartenPosition < 6; kartenPosition++) {

            if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("diamonds")) {

                tempschwarz = tempschwarz + arrWerte[kartenPosition];

            }
        }

        if (tempschwarz > schwarzgesammtMitte) {
            schwarzgesammtMitte = tempschwarz;
        }
        tempschwarz = 0;


        for (int kartenPosition = 3; kartenPosition < 6; kartenPosition++) {

            if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("heart")) {

                tempschwarz = tempschwarz + arrWerte[kartenPosition];

            }
        }

        if (tempschwarz > schwarzgesammtMitte) {
            schwarzgesammtMitte = tempschwarz;
        }
        tempschwarz = 0;


        for (int kartenPosition = 3; kartenPosition < 6; kartenPosition++) {

            if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("spades")) {

                tempschwarz = tempschwarz + arrWerte[kartenPosition];

            }
        }

        if (tempschwarz > schwarzgesammtMitte) {
            schwarzgesammtMitte = tempschwarz;
        }
        tempschwarz = 0;



        rotgesammtMitte = 0;
        temprot = 0;

        //bot gesammt
        for (int kartenPosition = 3; kartenPosition < 6; kartenPosition++) {

            if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("club")) {

                temprot = temprot + arrWerte[kartenPosition];

            }
        }

        if (temprot > rotgesammtMitte) {
            rotgesammtMitte = temprot;
        }
        temprot = 0;



        for (int kartenPosition = 3; kartenPosition < 6; kartenPosition++) {

            if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("diamonds")) {

                temprot = temprot + arrWerte[kartenPosition];

            }
        }

        if (temprot > rotgesammtMitte) {
            rotgesammtMitte = temprot;
        }
        temprot = 0;


        for (int kartenPosition = 3; kartenPosition < 6; kartenPosition++) {

            if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("heart")) {

                temprot = temprot + arrWerte[kartenPosition];

            }
        }

        if (temprot > rotgesammtMitte) {
            rotgesammtMitte = temprot;
        }
        temprot = 0;


        for (int kartenPosition = 3; kartenPosition < 6; kartenPosition++) {

            if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("spades")) {

                temprot = temprot + arrWerte[kartenPosition];

            }
        }

        if (temprot > rotgesammtMitte) {
            rotgesammtMitte = temprot;
        }
        temprot = 0;




        if ((KartenwertMitteLinks + KartenwertMitteMitte + KartenwertMitteRechts) == 31) {

            schwarzgesammtMitte = 31;
            rotgesammtMitte = 31;

        }
        if ((KartenwertMitteLinks + KartenwertMitteMitte + KartenwertMitteRechts) == 33) {

            schwarzgesammtMitte = 33;
            rotgesammtMitte = 33;

        }


        if (schwarzgesammtBot < schwarzgesammtMitte) {
            if (schwarzgesammtMitte > rotgesammtBot && schwarzgesammtMitte > rotgesammtMitte) {
                //schwarz neue Karten vom Bot
                if (schwarzgesammtMitte > 24) { //tauscht nur alle karten wenn über wert 24 in der mitte
                    alleTauschenWertSchwarz = schwarzgesammtMitte;
                } else {
                    alleTauschenWertSchwarz = 0;
                }

            }
        }

        if (rotgesammtBot < rotgesammtMitte) {
            if (rotgesammtMitte > schwarzgesammtBot && rotgesammtMitte > schwarzgesammtMitte) {
                //rot neue Karten vom Bot
                if (rotgesammtMitte > 24) {
                    alleTauschenWertRot = rotgesammtMitte;
                } else {
                    alleTauschenWertRot = 0;
                }

            }
        }


    }


    //berechnet punktzahl wenn erste bot karte getauscht wird
    public void BotAlgorithmErsteKarte() {

        // erste karte bot, erste karte mitte
        //schwarz

        if (dreißigeinhalbpkt(gp.karten.hand[4][0], gp.karten.hand[bot][1], gp.karten.hand[bot][2]) == 30.5) {


            ersteKarteBotErsteMitteRot = 30.5;
            ersteKarteBotErsteMitteSchwarz = 30.5;

        }

         else if (KartenwertMitteLinks + KartenwertBotMitte + KartenwertBotRechts == 33) {

            ersteKarteBotErsteMitteRot = 33;
            ersteKarteBotErsteMitteSchwarz = 33;

        } else {

             //schwarz
            schwarzgesammtBot = 0;
            int tempschwarz = 0;


            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {


                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("club")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteLinks.equals("schwarz") && KartenSymbolMitteLinks.equals("club")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteLinks;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ersteKarteBotErsteMitteSchwarz) {
                ersteKarteBotErsteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;

            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("diamonds")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteLinks.equals("schwarz") && KartenSymbolMitteLinks.equals("diamonds")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteLinks;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ersteKarteBotErsteMitteSchwarz) {
                ersteKarteBotErsteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("heart")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteLinks.equals("schwarz") && KartenSymbolMitteLinks.equals("heart")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteLinks;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ersteKarteBotErsteMitteSchwarz) {
                ersteKarteBotErsteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;

            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("spades")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteLinks.equals("schwarz") && KartenSymbolMitteLinks.equals("spades")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteLinks;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ersteKarteBotErsteMitteSchwarz) {
                ersteKarteBotErsteMitteSchwarz = schwarzgesammtBot;
            }


            System.out.println("ersteKarteBotErsteMitteSchwarz"+ersteKarteBotErsteMitteSchwarz);


            //rot
            rotgesammtBot = 0;
            int temprot = 0;


            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("club")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteLinks.equals("rot") && KartenSymbolMitteLinks.equals("club")) {
                rotgesammtBot = temprot + KartenwertMitteLinks;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ersteKarteBotErsteMitteRot) {
                ersteKarteBotErsteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;

            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("diamonds")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteLinks.equals("rot") && KartenSymbolMitteLinks.equals("diamonds")) {
                rotgesammtBot = temprot + KartenwertMitteLinks;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ersteKarteBotErsteMitteRot) {
                ersteKarteBotErsteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("heart")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteLinks.equals("rot") && KartenSymbolMitteLinks.equals("heart")) {
                rotgesammtBot = temprot + KartenwertMitteLinks;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ersteKarteBotErsteMitteRot) {
                ersteKarteBotErsteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;

            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("spades")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteLinks.equals("rot") && KartenSymbolMitteLinks.equals("spades")) {
                rotgesammtBot = temprot + KartenwertMitteLinks;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ersteKarteBotErsteMitteRot) {
                ersteKarteBotErsteMitteRot = rotgesammtBot;
            }


            System.out.println("ersteKarteBotErsteMitteRot"+ersteKarteBotErsteMitteRot);


        }

        //----------------------------------------------------------------------------------------------------------//

        // erste karte bot, zweite karte mitte
        //schwarz

        if (dreißigeinhalbpkt(gp.karten.hand[4][1], gp.karten.hand[bot][1], gp.karten.hand[bot][2]) == 30.5) {

            ersteKarteBotZweiteMitteSchwarz = 30.5;
            ersteKarteBotZweiteMitteRot = 30.5;

        }
        else if (KartenwertMitteMitte + KartenwertBotMitte + KartenwertBotRechts == 33) {

            ersteKarteBotErsteMitteRot = 33;
            ersteKarteBotErsteMitteSchwarz = 33;

        } else {


            //schwarz
            schwarzgesammtBot = 0;
            int tempschwarz = 0;


            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("club")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteMitte.equals("schwarz") && KartenSymbolMitteMitte.equals("club")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteMitte;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ersteKarteBotZweiteMitteSchwarz) {
                ersteKarteBotZweiteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;

            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("diamonds")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteMitte.equals("schwarz") && KartenSymbolMitteMitte.equals("diamonds")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteMitte;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ersteKarteBotZweiteMitteSchwarz) {
                ersteKarteBotZweiteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("heart")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteMitte.equals("schwarz") && KartenSymbolMitteMitte.equals("heart")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteMitte;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ersteKarteBotZweiteMitteSchwarz) {
                ersteKarteBotZweiteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;

            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("spades")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteMitte.equals("schwarz") && KartenSymbolMitteMitte.equals("spades")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteMitte;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ersteKarteBotZweiteMitteSchwarz) {
                ersteKarteBotZweiteMitteSchwarz = schwarzgesammtBot;
            }


            System.out.println("ersteKarteBotZweiteMitteSchwarz"+ersteKarteBotZweiteMitteSchwarz);



            //rot
            rotgesammtBot = 0;
            int temprot = 0;


            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("club")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteMitte.equals("rot") && KartenSymbolMitteMitte.equals("club")) {
                rotgesammtBot = temprot + KartenwertMitteMitte;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ersteKarteBotZweiteMitteRot) {
                ersteKarteBotZweiteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("diamonds")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteMitte.equals("rot") && KartenSymbolMitteMitte.equals("diamonds")) {
                rotgesammtBot = temprot + KartenwertMitteMitte;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ersteKarteBotZweiteMitteRot) {
                ersteKarteBotZweiteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("heart")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteMitte.equals("rot") && KartenSymbolMitteMitte.equals("heart")) {
                rotgesammtBot = temprot + KartenwertMitteMitte;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ersteKarteBotZweiteMitteRot) {
                ersteKarteBotZweiteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("spades")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteMitte.equals("rot") && KartenSymbolMitteMitte.equals("spades")) {
                rotgesammtBot = temprot + KartenwertMitteMitte;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ersteKarteBotZweiteMitteRot) {
                ersteKarteBotZweiteMitteRot = rotgesammtBot;
            }

            System.out.println("ersteKarteBotZweiteMitteRot"+ersteKarteBotZweiteMitteRot);


        }

        //----------------------------------------------------------------------------------------------------------//

        // erste karte bot, dritte karte mitte
        //schwarz

        if (dreißigeinhalbpkt(gp.karten.hand[4][2], gp.karten.hand[bot][1], gp.karten.hand[bot][2]) == 30.5) {

            ersteKarteBotDritteMitteSchwarz = 30.5;
            ersteKarteBotDritteMitteRot = 30.5;

        }
        else if (KartenwertMitteRechts + KartenwertBotMitte + KartenwertBotRechts == 33) {

            ersteKarteBotErsteMitteRot = 33;
            ersteKarteBotErsteMitteSchwarz = 33;

        } else {

            //schwarz
            schwarzgesammtBot = 0;
            int tempschwarz = 0;


            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("club")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteRechts.equals("schwarz") && KartenSymbolMitteRechts.equals("club")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteRechts;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ersteKarteBotDritteMitteSchwarz) {
                ersteKarteBotDritteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;

            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("diamonds")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteRechts.equals("schwarz") && KartenSymbolMitteRechts.equals("diamonds")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteRechts;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ersteKarteBotDritteMitteSchwarz) {
                ersteKarteBotDritteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("heart")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteRechts.equals("schwarz") && KartenSymbolMitteRechts.equals("heart")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteRechts;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ersteKarteBotDritteMitteSchwarz) {
                ersteKarteBotDritteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;

            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("spades")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteRechts.equals("schwarz") && KartenSymbolMitteRechts.equals("spades")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteRechts;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ersteKarteBotDritteMitteSchwarz) {
                ersteKarteBotDritteMitteSchwarz = schwarzgesammtBot;
            }


            System.out.println("ersteKarteBotDritteMitteSchwarz"+ersteKarteBotDritteMitteSchwarz);


            //rot
            rotgesammtBot = 0;
            int temprot = 0;


            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("club")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteRechts.equals("rot") && KartenSymbolMitteRechts.equals("club")) {
                rotgesammtBot = temprot + KartenwertMitteRechts;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ersteKarteBotDritteMitteRot) {
                ersteKarteBotDritteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("diamonds")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteRechts.equals("rot") && KartenSymbolMitteRechts.equals("diamonds")) {
                rotgesammtBot = temprot + KartenwertMitteRechts;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ersteKarteBotDritteMitteRot) {
                ersteKarteBotDritteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("heart")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteRechts.equals("rot") && KartenSymbolMitteRechts.equals("heart")) {
                rotgesammtBot = temprot + KartenwertMitteRechts;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ersteKarteBotDritteMitteRot) {
                ersteKarteBotDritteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 1; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("spades")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (KartenFarbeMitteRechts.equals("rot") && KartenSymbolMitteRechts.equals("spades")) {
                rotgesammtBot = temprot + KartenwertMitteRechts;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ersteKarteBotDritteMitteRot) {
                ersteKarteBotDritteMitteRot = rotgesammtBot;
            }


            System.out.println("ersteKarteBotDritteMitteRot"+ersteKarteBotDritteMitteRot);


        }

        //----------------------------------------------------------------------------------------------------------//



    }


    //berechnet punktzahl wenn zweite bot karte getauscht wird
    public void BotAlgorithmZweiteKarte() {

        // zweite karte bot, erste karte mitte
        //schwarz

        if (dreißigeinhalbpkt(gp.karten.hand[bot][0], gp.karten.hand[4][0], gp.karten.hand[bot][2]) == 30.5) {

            ZweiteKarteBotErsteMitteSchwarz = 30.5;
            ZweiteKarteBotErsteMitteRot = 30.5;

        }
        else if (KartenwertBotLinks + KartenwertMitteLinks + KartenwertBotRechts == 33) {

            ersteKarteBotErsteMitteRot = 33;
            ersteKarteBotErsteMitteSchwarz = 33;

        } else {

            schwarzgesammtBot = 0;
            int tempschwarz = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("club")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteLinks.equals("schwarz") && KartenSymbolMitteLinks.equals("club")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteLinks;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ZweiteKarteBotErsteMitteSchwarz) {
                ZweiteKarteBotErsteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("diamonds")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteLinks.equals("schwarz") && KartenSymbolMitteLinks.equals("diamonds")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteLinks;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ZweiteKarteBotErsteMitteSchwarz) {
                ZweiteKarteBotErsteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("heart")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteLinks.equals("schwarz") && KartenSymbolMitteLinks.equals("heart")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteLinks;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ZweiteKarteBotErsteMitteSchwarz) {
                ZweiteKarteBotErsteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("spades")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteLinks.equals("schwarz") && KartenSymbolMitteLinks.equals("spades")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteLinks;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ZweiteKarteBotErsteMitteSchwarz) {
                ZweiteKarteBotErsteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;

            System.out.println("ZweiteKarteBotErsteMitteSchwarz"+ZweiteKarteBotErsteMitteSchwarz);



            //rot
            rotgesammtBot = 0;
            int temprot = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("club")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteLinks.equals("rot") && KartenSymbolMitteLinks.equals("club")) {
                rotgesammtBot = temprot + KartenwertMitteLinks;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ZweiteKarteBotErsteMitteRot) {
                ZweiteKarteBotErsteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("diamonds")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteLinks.equals("rot") && KartenSymbolMitteLinks.equals("diamonds")) {
                rotgesammtBot = temprot + KartenwertMitteLinks;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ZweiteKarteBotErsteMitteRot) {
                ZweiteKarteBotErsteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("heart")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteLinks.equals("rot") && KartenSymbolMitteLinks.equals("heart")) {
                rotgesammtBot = temprot + KartenwertMitteLinks;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ZweiteKarteBotErsteMitteRot) {
                ZweiteKarteBotErsteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("spades")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteLinks.equals("rot") && KartenSymbolMitteLinks.equals("spades")) {
                rotgesammtBot = temprot + KartenwertMitteLinks;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ZweiteKarteBotErsteMitteRot) {
                ZweiteKarteBotErsteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;

            System.out.println("ZweiteKarteBotErsteMitteRot"+ZweiteKarteBotErsteMitteRot);


        }
        //----------------------------------------------------------------------------------------------------------//

        // zweite karte bot, zweite karte mitte
        //schwarz

        if (dreißigeinhalbpkt(gp.karten.hand[bot][0], gp.karten.hand[4][1], gp.karten.hand[bot][2]) == 30.5) {

            ZweiteKarteBotZweiteMitteSchwarz = 30.5;
            ZweiteKarteBotZweiteMitteRot = 30.5;

        }
        else if (KartenwertBotLinks + KartenwertMitteMitte + KartenwertBotRechts == 33) {

            ersteKarteBotErsteMitteRot = 33;
            ersteKarteBotErsteMitteSchwarz = 33;

        } else {


            schwarzgesammtBot = 0;
            int tempschwarz = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("club")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteMitte.equals("schwarz") && KartenSymbolMitteMitte.equals("club")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteMitte;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ZweiteKarteBotZweiteMitteSchwarz) {
                ZweiteKarteBotZweiteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("diamonds")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteMitte.equals("schwarz") && KartenSymbolMitteMitte.equals("diamonds")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteMitte;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ZweiteKarteBotZweiteMitteSchwarz) {
                ZweiteKarteBotZweiteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("heart")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteMitte.equals("schwarz") && KartenSymbolMitteMitte.equals("heart")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteMitte;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ZweiteKarteBotZweiteMitteSchwarz) {
                ZweiteKarteBotZweiteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("spades")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteMitte.equals("schwarz") && KartenSymbolMitteMitte.equals("spades")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteMitte;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ZweiteKarteBotZweiteMitteSchwarz) {
                ZweiteKarteBotZweiteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;

            System.out.println("ZweiteKarteBotZweiteMitteSchwarz"+ZweiteKarteBotZweiteMitteSchwarz);




            rotgesammtBot = 0;
            int temprot = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("club")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteMitte.equals("rot") && KartenSymbolMitteMitte.equals("club")) {
                rotgesammtBot = temprot + KartenwertMitteMitte;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ZweiteKarteBotZweiteMitteRot) {
                ZweiteKarteBotZweiteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("diamonds")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteMitte.equals("rot") && KartenSymbolMitteMitte.equals("diamonds")) {
                rotgesammtBot = temprot + KartenwertMitteMitte;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ZweiteKarteBotZweiteMitteRot) {
                ZweiteKarteBotZweiteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("heart")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteMitte.equals("rot") && KartenSymbolMitteMitte.equals("heart")) {
                rotgesammtBot = temprot + KartenwertMitteMitte;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ZweiteKarteBotZweiteMitteRot) {
                ZweiteKarteBotZweiteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("spades")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteMitte.equals("rot") && KartenSymbolMitteMitte.equals("spades")) {
                rotgesammtBot = temprot + KartenwertMitteMitte;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ZweiteKarteBotZweiteMitteRot) {
                ZweiteKarteBotZweiteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;

            System.out.println("ZweiteKarteBotZweiteMitteRot"+ZweiteKarteBotZweiteMitteRot);

        }


        //----------------------------------------------------------------------------------------------------------//

        // erste karte bot, dritte karte mitte
        //schwarz

        if (dreißigeinhalbpkt(gp.karten.hand[bot][0], gp.karten.hand[4][2], gp.karten.hand[bot][2]) == 30.5) {

            ZweiteKarteBotDritteMitteSchwarz = 30.5;
            ZweiteKarteBotDritteMitteRot = 30.5;

        }
        else if (KartenwertBotLinks + KartenwertMitteRechts + KartenwertBotRechts == 33) {

            ersteKarteBotErsteMitteRot = 33;
            ersteKarteBotErsteMitteSchwarz = 33;

        } else {


            schwarzgesammtBot = 0;
            int tempschwarz = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("club")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteRechts.equals("schwarz") && KartenSymbolMitteRechts.equals("club")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteRechts;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ZweiteKarteBotDritteMitteSchwarz) {
                ZweiteKarteBotDritteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("diamonds")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteRechts.equals("schwarz") && KartenSymbolMitteRechts.equals("diamonds")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteRechts;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ZweiteKarteBotDritteMitteSchwarz) {
                ZweiteKarteBotDritteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("heart")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteRechts.equals("schwarz") && KartenSymbolMitteRechts.equals("heart")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteRechts;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ZweiteKarteBotDritteMitteSchwarz) {
                ZweiteKarteBotDritteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("spades")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteRechts.equals("schwarz") && KartenSymbolMitteRechts.equals("spades")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteRechts;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > ZweiteKarteBotDritteMitteSchwarz) {
                ZweiteKarteBotDritteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            System.out.println("ZweiteKarteBotDritteMitteSchwarz"+ZweiteKarteBotDritteMitteSchwarz);



            rotgesammtBot = 0;
            int temprot = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("club")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteRechts.equals("rot") && KartenSymbolMitteRechts.equals("club")) {
                rotgesammtBot = temprot + KartenwertMitteRechts;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ZweiteKarteBotDritteMitteRot) {
                ZweiteKarteBotDritteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("diamonds")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteRechts.equals("rot") && KartenSymbolMitteRechts.equals("diamonds")) {
                rotgesammtBot = temprot + KartenwertMitteRechts;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ZweiteKarteBotDritteMitteRot) {
                ZweiteKarteBotDritteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("heart")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteRechts.equals("rot") && KartenSymbolMitteRechts.equals("heart")) {
                rotgesammtBot = temprot + KartenwertMitteRechts;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ZweiteKarteBotDritteMitteRot) {
                ZweiteKarteBotDritteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("spades")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }

                kartenPosition++;

            }
            if (KartenFarbeMitteRechts.equals("rot") && KartenSymbolMitteRechts.equals("spades")) {
                rotgesammtBot = temprot + KartenwertMitteRechts;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > ZweiteKarteBotDritteMitteRot) {
                ZweiteKarteBotDritteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;



            System.out.println("ZweiteKarteBotDritteMitteRot"+ZweiteKarteBotDritteMitteRot);










        }


        //----------------------------------------------------------------------------------------------------------//

    }


    //berechnet punktzahl wenn dritte bot karte getauscht wird
    public void BotAlgorithmDritteKarte() {

        // erste karte bot, erste karte mitte
        //schwarz

        if (dreißigeinhalbpkt(gp.karten.hand[bot][0], gp.karten.hand[bot][1], gp.karten.hand[4][0]) == 30.5) {

            DritteKarteBotErsteMitteSchwarz = 30.5;
            DritteKarteBotErsteMitteRot = 30.5;

        }
        else if (KartenwertBotLinks + KartenwertBotMitte + KartenwertMitteLinks == 33) {

            ersteKarteBotErsteMitteRot = 33;
            ersteKarteBotErsteMitteSchwarz = 33;

        } else {


            //schwarz
            schwarzgesammtBot = 0;
            int tempschwarz = 0;

            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("club")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteLinks.equals("schwarz") && KartenSymbolMitteLinks.equals("club")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteLinks;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > DritteKarteBotErsteMitteSchwarz) {
                DritteKarteBotErsteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("diamonds")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteLinks.equals("schwarz") && KartenSymbolMitteLinks.equals("diamonds")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteLinks;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > DritteKarteBotErsteMitteSchwarz) {
                DritteKarteBotErsteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("heart")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteLinks.equals("schwarz") && KartenSymbolMitteLinks.equals("heart")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteLinks;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > DritteKarteBotErsteMitteSchwarz) {
                DritteKarteBotErsteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("spades")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteLinks.equals("schwarz") && KartenSymbolMitteLinks.equals("spades")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteLinks;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > DritteKarteBotErsteMitteSchwarz) {
                DritteKarteBotErsteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;


            System.out.println("DritteKarteBotErsteMitteSchwarz"+DritteKarteBotErsteMitteSchwarz);


            //rot
            rotgesammtBot = 0;
            int temprot = 0;

            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("club")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteLinks.equals("rot") && KartenSymbolMitteLinks.equals("club")) {
                rotgesammtBot = temprot + KartenwertMitteLinks;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > DritteKarteBotErsteMitteRot) {
                DritteKarteBotErsteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("diamonds")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteLinks.equals("rot") && KartenSymbolMitteLinks.equals("diamonds")) {
                rotgesammtBot = temprot + KartenwertMitteLinks;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > DritteKarteBotErsteMitteRot) {
                DritteKarteBotErsteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("heart")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteLinks.equals("rot") && KartenSymbolMitteLinks.equals("heart")) {
                rotgesammtBot = temprot + KartenwertMitteLinks;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > DritteKarteBotErsteMitteRot) {
                DritteKarteBotErsteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("spades")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteLinks.equals("rot") && KartenSymbolMitteLinks.equals("spades")) {
                rotgesammtBot = temprot + KartenwertMitteLinks;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > DritteKarteBotErsteMitteRot) {
                DritteKarteBotErsteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;


            System.out.println("DritteKarteBotErsteMitteRot"+DritteKarteBotErsteMitteRot);

        }

        //----------------------------------------------------------------------------------------------------------//

        // erste karte bot, zweite karte mitte
        //schwarz

        if (dreißigeinhalbpkt(gp.karten.hand[bot][0], gp.karten.hand[bot][1], gp.karten.hand[4][1]) == 30.5) {

            DritteKarteBotZweiteMitteSchwarz = 30.5;
            DritteKarteBotZweiteMitteRot = 30.5;

        }
        else if (KartenwertBotLinks + KartenwertBotMitte + KartenwertMitteMitte == 33) {

            ersteKarteBotErsteMitteRot = 33;
            ersteKarteBotErsteMitteSchwarz = 33;

        } else {

            //schwarz
            schwarzgesammtBot = 0;
            int tempschwarz = 0;

            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("club")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteMitte.equals("schwarz") && KartenSymbolMitteMitte.equals("club")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteMitte;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > DritteKarteBotZweiteMitteSchwarz) {
                DritteKarteBotZweiteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("diamonds")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteMitte.equals("schwarz") && KartenSymbolMitteMitte.equals("diamonds")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteMitte;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > DritteKarteBotZweiteMitteSchwarz) {
                DritteKarteBotZweiteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("heart")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteMitte.equals("schwarz") && KartenSymbolMitteMitte.equals("heart")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteMitte;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > DritteKarteBotZweiteMitteSchwarz) {
                DritteKarteBotZweiteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("spades")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteMitte.equals("schwarz") && KartenSymbolMitteMitte.equals("spades")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteMitte;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > DritteKarteBotZweiteMitteSchwarz) {
                DritteKarteBotZweiteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;

            System.out.println("DritteKarteBotZweiteMitteSchwarz"+DritteKarteBotZweiteMitteSchwarz);


            //rot
            rotgesammtBot = 0;
            int temprot = 0;

            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("club")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteMitte.equals("rot") && KartenSymbolMitteMitte.equals("club")) {
                rotgesammtBot = temprot + KartenwertMitteMitte;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > DritteKarteBotZweiteMitteRot) {
                DritteKarteBotZweiteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("diamonds")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteMitte.equals("rot") && KartenSymbolMitteMitte.equals("diamonds")) {
                rotgesammtBot = temprot + KartenwertMitteMitte;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > DritteKarteBotZweiteMitteRot) {
                DritteKarteBotZweiteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("heart")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteMitte.equals("rot") && KartenSymbolMitteMitte.equals("heart")) {
                rotgesammtBot = temprot + KartenwertMitteMitte;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > DritteKarteBotZweiteMitteRot) {
                DritteKarteBotZweiteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("spades")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteMitte.equals("rot") && KartenSymbolMitteMitte.equals("spades")) {
                rotgesammtBot = temprot + KartenwertMitteMitte;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > DritteKarteBotZweiteMitteRot) {
                DritteKarteBotZweiteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;

            System.out.println("DritteKarteBotZweiteMitteRot"+DritteKarteBotZweiteMitteRot);

        }

        //----------------------------------------------------------------------------------------------------------//

        // erste karte bot, dritte karte mitte
        //schwarz

        if (dreißigeinhalbpkt(gp.karten.hand[bot][0], gp.karten.hand[bot][1], gp.karten.hand[4][2]) == 30.5) {

            DritteKarteBotDritteMitteSchwarz = 30.5;
            DritteKarteBotDritteMitteRot = 30.5;

        }
        else if (KartenwertBotLinks + KartenwertBotMitte + KartenwertMitteRechts== 33) {

            ersteKarteBotErsteMitteRot = 33;
            ersteKarteBotErsteMitteSchwarz = 33;

        } else {

            //schwarz
            schwarzgesammtBot = 0;
            int tempschwarz = 0;

            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("club")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteRechts.equals("schwarz") && KartenSymbolMitteRechts.equals("club")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteRechts;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > DritteKarteBotDritteMitteSchwarz) {
                DritteKarteBotDritteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("diamonds")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteRechts.equals("schwarz") && KartenSymbolMitteRechts.equals("diamonds")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteRechts;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > DritteKarteBotDritteMitteSchwarz) {
                DritteKarteBotDritteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("heart")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteRechts.equals("schwarz") && KartenSymbolMitteRechts.equals("heart")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteRechts;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > DritteKarteBotDritteMitteSchwarz) {
                DritteKarteBotDritteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;
            tempschwarz = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("spades")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteRechts.equals("schwarz") && KartenSymbolMitteRechts.equals("spades")) {
                schwarzgesammtBot = tempschwarz + KartenwertMitteRechts;
            } else {
                schwarzgesammtBot = tempschwarz;
            }
            if (schwarzgesammtBot > DritteKarteBotDritteMitteSchwarz) {
                DritteKarteBotDritteMitteSchwarz = schwarzgesammtBot;
            }
            schwarzgesammtBot = 0;


            System.out.println("DritteKarteBotDritteMitteSchwarz"+DritteKarteBotDritteMitteSchwarz);


            //rot
            rotgesammtBot = 0;
            int temprot = 0;

            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("club")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteRechts.equals("rot") && KartenSymbolMitteRechts.equals("club")) {
                rotgesammtBot = temprot + KartenwertMitteRechts;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > DritteKarteBotDritteMitteRot) {
                DritteKarteBotDritteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("diamonds")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteRechts.equals("rot") && KartenSymbolMitteRechts.equals("diamonds")) {
                rotgesammtBot = temprot + KartenwertMitteRechts;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > DritteKarteBotDritteMitteRot) {
                DritteKarteBotDritteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("heart")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteRechts.equals("rot") && KartenSymbolMitteRechts.equals("heart")) {
                rotgesammtBot = temprot + KartenwertMitteRechts;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > DritteKarteBotDritteMitteRot) {
                DritteKarteBotDritteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;
            temprot = 0;


            for (int kartenPosition = 0; kartenPosition < 2; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("spades")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }

            if (KartenFarbeMitteRechts.equals("rot") && KartenSymbolMitteRechts.equals("spades")) {
                rotgesammtBot = temprot + KartenwertMitteRechts;
            } else {
                rotgesammtBot = temprot;
            }
            if (rotgesammtBot > DritteKarteBotDritteMitteRot) {
                DritteKarteBotDritteMitteRot = rotgesammtBot;
            }
            rotgesammtBot = 0;


            System.out.println("DritteKarteBotDritteMitteRot"+DritteKarteBotDritteMitteRot);


        }

        //----------------------------------------------------------------------------------------------------------//


    }


    //markiert karte welche getauscht wird (höchste zahlen möglichkeit)
    public void BotAlgorithmMarkieren(int bot, int botStelle, int mitteStelle) {

        g2.setColor(Color.white);

        if (mitteStelle != 3 && botStelle != 3) {

            //mitte
            xmitte = gp.tileSize * 9 + ((gp.tileSize * 3) * mitteStelle);
            g2.drawRoundRect(xmitte - 8, (int) (gp.tileSize*7.64) - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);

            if (bot == 0) {

                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);
                g2.drawRoundRect(gp.tileSize * 3 - 8, ybot1 - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);

            }
            if (bot == 1) {

                int xbot2 = gp.tileSize*9+ ((gp.tileSize*3)*botStelle);
                g2.drawRoundRect(xbot2 - 8, gp.tileSize*1 - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);

            }
            if (bot == 2) {

                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);
                g2.drawRoundRect(gp.tileSize * 21 - 8, ybot1 - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);

            }

        } else {



            if (bot == 0) {

                botStelle = 0;
                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);
                g2.drawRoundRect(gp.tileSize * 3 - 8, ybot1 - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);

                botStelle++;
                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);

                g2.drawRoundRect(gp.tileSize * 3 - 8, ybot1 - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);

                botStelle++;
                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);

                g2.drawRoundRect(gp.tileSize * 3 - 8, ybot1 - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);
            }

            if (bot == 1) {

                botStelle = 0;
                int xbot2 = gp.tileSize * 9 + ((gp.tileSize * 3) * botStelle);

                g2.drawRoundRect(xbot2 - 8, gp.tileSize*1 - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);

                botStelle++;
                xbot2 = gp.tileSize * 9 + ((gp.tileSize * 3) * botStelle);

                g2.drawRoundRect(xbot2 - 8, gp.tileSize*1 - 8, gp.tileSize * 2 + 15, gp.tileSize * 3 + 20, 25, 25);

                botStelle++;
                xbot2 = gp.tileSize * 9 + ((gp.tileSize * 3) * botStelle);

                g2.drawRoundRect(xbot2 - 8, gp.tileSize*1 - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);
            }

            if (bot == 2) {


                botStelle = 0;
                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);

                g2.drawRoundRect(gp.tileSize * 21 - 8, ybot1 - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);

                botStelle++;
                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);

                g2.drawRoundRect(gp.tileSize * 21 - 8, ybot1 - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);

                botStelle++;
                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);

                g2.drawRoundRect(gp.tileSize * 21 - 8, ybot1 - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);
            }




            //mitte
            mitteStelle = 0;
            xmitte = gp.tileSize * 9 + ((gp.tileSize * 3) * mitteStelle);

            g2.drawRoundRect(xmitte - 8, (int) (gp.tileSize*7.64) - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);

            mitteStelle++;
            xmitte = gp.tileSize * 9 + ((gp.tileSize * 3) * mitteStelle);

            g2.drawRoundRect(xmitte - 8, (int) (gp.tileSize*7.64) - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);

            mitteStelle++;
            xmitte = gp.tileSize * 9 + ((gp.tileSize * 3) * mitteStelle);

            g2.drawRoundRect(xmitte - 8, (int) (gp.tileSize*7.64) - 8, gp.tileSize * 2 + 20, gp.tileSize * 3 + 20, 25, 25);


        }

    }


    //tauscht karte die vorher markiert wurde (höchste zahlen möglichkeit)
    public void BotAlgorithmTauschen(int bot, int botStelle, int mitteStelle) {


        System.out.println("Tauschen mit Bot " + bot);


        if (mitteStelle != 3 && botStelle != 3) {

            //temp
            tempbot = gp.karten.hand[bot][botStelle];
            tempmitte = gp.karten.hand[4][mitteStelle];

            //tauschen im array
            gp.karten.hand[bot][botStelle] = tempmitte;
            gp.karten.hand[4][mitteStelle] = tempbot;

            //evtl. pause

            //mitte
            xmitte = gp.tileSize * 9 + ((gp.tileSize * 3) * mitteStelle);
            g2.drawImage(gp.karten.hand[4][mitteStelle], xmitte, (int) (gp.tileSize*7.64), gp.tileSize * 2, gp.tileSize * 3, null);

            if (bot == 0) {

                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);
                g2.drawImage(tempmitte, gp.tileSize * 3, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);

            }
            if (bot == 1) {

                int xbot2 = gp.tileSize*9+ ((gp.tileSize*3)*botStelle);
                g2.drawImage(tempmitte, xbot2,gp.tileSize*1,gp.tileSize*2,gp.tileSize*3, null);

            }
            if (bot == 2) {

                ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);
                g2.drawImage(tempmitte, gp.tileSize * 21, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);

            }

        } else {


            //temp
            BufferedImage tempbot1 = gp.karten.hand[bot][0];
            BufferedImage tempbot2 = gp.karten.hand[bot][1];
            BufferedImage tempbot3 = gp.karten.hand[bot][2];
            BufferedImage tempmitte1 = gp.karten.hand[4][0];
            BufferedImage tempmitte2 = gp.karten.hand[4][1];
            BufferedImage tempmitte3 = gp.karten.hand[4][2];

            //tauschen im array
            gp.karten.hand[bot][0] = tempmitte1;
            gp.karten.hand[bot][1] = tempmitte2;
            gp.karten.hand[bot][2] = tempmitte3;
            gp.karten.hand[4][0] = tempbot1;
            gp.karten.hand[4][1] = tempbot2;
            gp.karten.hand[4][2] = tempbot3;

            if (bot == 0) {

                    botStelle = 0;
                    ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);
                    g2.drawImage(gp.karten.hand[bot][botStelle], gp.tileSize * 3, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);

                    botStelle++;
                    ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);

                    g2.drawImage(gp.karten.hand[bot][botStelle], gp.tileSize * 3, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);

                    botStelle++;
                    ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);

                    g2.drawImage(gp.karten.hand[bot][botStelle], gp.tileSize * 3, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);
            }

            if (bot == 1) {

                    botStelle = 0;
                    int xbot2 = gp.tileSize * 9 + ((gp.tileSize * 3) * botStelle);

                    g2.drawImage(gp.karten.hand[bot][botStelle], xbot2, gp.tileSize * 1, gp.tileSize * 2, gp.tileSize * 3, null);

                    botStelle++;
                    xbot2 = gp.tileSize * 9 + ((gp.tileSize * 3) * botStelle);

                    g2.drawImage(gp.karten.hand[bot][botStelle], xbot2, gp.tileSize * 1, gp.tileSize * 2, gp.tileSize * 3, null);

                    botStelle++;
                    xbot2 = gp.tileSize * 9 + ((gp.tileSize * 3) * botStelle);

                    g2.drawImage(gp.karten.hand[bot][botStelle], xbot2, gp.tileSize * 1, gp.tileSize * 2, gp.tileSize * 3, null);
            }

            if (bot == 2) {


                    botStelle = 0;
                    ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);

                    g2.drawImage(gp.karten.hand[bot][botStelle], gp.tileSize * 21, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);

                    botStelle++;
                    ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);

                    g2.drawImage(gp.karten.hand[bot][botStelle], gp.tileSize * 21, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);

                    botStelle++;
                    ybot1 = gp.tileSize * 3 + ((gp.tileSize * 4) * botStelle);

                    g2.drawImage(gp.karten.hand[bot][botStelle], gp.tileSize * 21, ybot1, gp.tileSize * 2, gp.tileSize * 3, null);
            }




                //mitte
                mitteStelle = 0;
                xmitte = gp.tileSize * 9 + ((gp.tileSize * 3) * mitteStelle);

                g2.drawImage(gp.karten.hand[4][mitteStelle], xmitte, (int) (gp.tileSize*7.64), gp.tileSize * 2, gp.tileSize * 3, null);

                mitteStelle++;
                xmitte = gp.tileSize * 9 + ((gp.tileSize * 3) * mitteStelle);

                g2.drawImage(gp.karten.hand[4][mitteStelle], xmitte, (int) (gp.tileSize*7.64), gp.tileSize * 2, gp.tileSize * 3, null);

                mitteStelle++;
                xmitte = gp.tileSize * 9 + ((gp.tileSize * 3) * mitteStelle);

                g2.drawImage(gp.karten.hand[4][mitteStelle], xmitte, (int) (gp.tileSize*7.64), gp.tileSize * 2, gp.tileSize * 3, null);

                gp.spieler.endgamephase = true;
                gp.spieler.endgamephase(bot);



        }

        defaultWerte();

    }


    //guckt ob 31 oder 33 pkt
    public void ueberpruefenObSpezialfall(int bot) {

        //wenn 31 pkt runde sofort vorbei, alle gleiche farben
        if (finaleKartenwerte(bot) == 31) {

            einunddreißig = true;
            //karten drehen
            kartenUmdrehen(bot,3,3);
            gp.timer.pause(2.5);
            gp.ui.drawZwischenScreen(bot,31);
            gp.gameState = gp.zwischenState;
            //runde vorbei, wenigste punkte verliert herz

        }


        //wenn 33 blitz (3asse) -> sofort zu ende alle leben verloren

        KartenwertBotLinks = gp.karten.kartenwerterhalten(gp.karten.hand[bot][0]);
        KartenwertBotMitte = gp.karten.kartenwerterhalten(gp.karten.hand[bot][1]);
        KartenwertBotRechts =  gp.karten.kartenwerterhalten(gp.karten.hand[bot][2]);


        if ((KartenwertBotLinks + KartenwertBotMitte + KartenwertBotRechts) == 33) {

            blitz = true;
            //karten drehen
            kartenUmdrehen(bot,3,3);
            gp.timer.pause(2.5);
            gp.ui.drawZwischenScreen(bot,33);
            gp.gameState = gp.zwischenState;
            //blitz, sofort zu ende, alle anderen leben verloren


        }


        //wenn spiel beendet, spieler mit niedrigster Punktzahl ein leben verloren (ings. 3)


        //kartenpkt
        //gleiche farbe oder gleicher rang
        //gleicher rang immer 30,5


    }


    //defaultwerte von botalgorithm
    public void defaultWerte() {


        System.out.println("reset");

        KartenwertBotLinks = 0;
        KartenwertBotMitte = 0;
        KartenwertBotRechts = 0;
        KartenwertMitteLinks = 0;
        KartenwertMitteMitte = 0;
        KartenwertMitteRechts = 0;

        ersteKarteBotErsteMitteSchwarz = 0;
        ersteKarteBotErsteMitteRot = 0;
        ersteKarteBotZweiteMitteSchwarz = 0;
        ersteKarteBotZweiteMitteRot = 0;
        ersteKarteBotDritteMitteSchwarz = 0;
        ersteKarteBotDritteMitteRot = 0;

        ZweiteKarteBotErsteMitteSchwarz = 0;
        ZweiteKarteBotErsteMitteRot = 0;
        ZweiteKarteBotZweiteMitteSchwarz = 0;
        ZweiteKarteBotZweiteMitteRot = 0;
        ZweiteKarteBotDritteMitteSchwarz = 0;
        ZweiteKarteBotDritteMitteRot = 0;

        DritteKarteBotErsteMitteSchwarz = 0;
        DritteKarteBotErsteMitteRot = 0;
        DritteKarteBotZweiteMitteSchwarz = 0;
        DritteKarteBotZweiteMitteRot = 0;
        DritteKarteBotDritteMitteSchwarz = 0;
        DritteKarteBotDritteMitteRot = 0;

        alleTauschenWertSchwarz = 0;
        alleTauschenWertRot = 0;

        String KartenFarbeBotLinks = "";
        String KartenFarbeBotMitte = "";
        String KartenFarbeBotRechts = "";
        String KartenFarbeMitteLinks = "";
        String KartenFarbeMitteMitte = "";
        String KartenFarbeMitteRechts = "";

        schwarzgesammtBot = 0;
        rotgesammtBot = 0;
        schwarzgesammtMitte = 0;
        rotgesammtMitte = 0;
        alletauschen = false;


    }


    //finale botkartenwerte für spielende
    public double finaleKartenwerte(int bot) {

        //kartenwert von jeder karte
        KartenwertBotLinks = gp.karten.kartenwerterhalten(gp.karten.hand[bot][0]);
        KartenwertBotMitte = gp.karten.kartenwerterhalten(gp.karten.hand[bot][1]);
        KartenwertBotRechts =  gp.karten.kartenwerterhalten(gp.karten.hand[bot][2]);

        KartenFarbeBotLinks = gp.karten.kartenfarbeerhalten(gp.karten.hand[bot][0]);
        KartenFarbeBotMitte = gp.karten.kartenfarbeerhalten(gp.karten.hand[bot][1]);
        KartenFarbeBotRechts =  gp.karten.kartenfarbeerhalten(gp.karten.hand[bot][2]);

        KartenSymbolBotLinks = gp.karten.kartensymbolerhalten(gp.karten.hand[bot][0]);
        KartenSymbolBotMitte = gp.karten.kartensymbolerhalten(gp.karten.hand[bot][1]);
        KartenSymbolBotRechts = gp.karten.kartensymbolerhalten(gp.karten.hand[bot][2]);


        arrWerte = new int[] {KartenwertBotLinks, KartenwertBotMitte, KartenwertBotRechts};

        arrFarben = new String[] {KartenFarbeBotLinks, KartenFarbeBotMitte, KartenFarbeBotRechts};

        arrSymbol = new String[] {KartenSymbolBotLinks, KartenSymbolBotMitte, KartenSymbolBotRechts};



        if (dreißigeinhalbpkt(gp.karten.hand[bot][0], gp.karten.hand[bot][1], gp.karten.hand[bot][2]) == 30.5) {

            BotSpezialfall = 30.5;
            return BotSpezialfall;

        } else {


            //schwarz
            schwarzgesammtBot = 0;
            int tempschwarz = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("club")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (tempschwarz > schwarzgesammtBot) {
                schwarzgesammtBot = tempschwarz;
            }
            tempschwarz = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("diamonds")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (tempschwarz > schwarzgesammtBot) {
                schwarzgesammtBot = tempschwarz;
            }
            tempschwarz = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("heart")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (tempschwarz > schwarzgesammtBot) {
                schwarzgesammtBot = tempschwarz;
            }
            tempschwarz = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("schwarz") && arrSymbol[kartenPosition].equals("spades")) {
                    tempschwarz = tempschwarz + arrWerte[kartenPosition];
                }
            }
            if (tempschwarz > schwarzgesammtBot) {
                schwarzgesammtBot = tempschwarz;
            }
            tempschwarz = 0;



            //rot
            rotgesammtBot = 0;
            int temprot = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("club")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (temprot > rotgesammtBot) {
                rotgesammtBot = temprot;
            }
            temprot = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("diamonds")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (temprot > rotgesammtBot) {
                rotgesammtBot = temprot;
            }
            temprot = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("heart")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (temprot > rotgesammtBot) {
                rotgesammtBot = temprot;
            }
            temprot = 0;

            for (int kartenPosition = 0; kartenPosition < 3; kartenPosition++) {

                if (arrFarben[kartenPosition].equals("rot") && arrSymbol[kartenPosition].equals("spades")) {
                    temprot = temprot + arrWerte[kartenPosition];
                }
            }
            if (temprot > rotgesammtBot) {
                rotgesammtBot = temprot;
            }
            temprot = 0;



            if (rotgesammtBot > schwarzgesammtBot) {

                return rotgesammtBot;

            } else {

                return schwarzgesammtBot;

            }

        }

        //spezialfälle

    }

}
