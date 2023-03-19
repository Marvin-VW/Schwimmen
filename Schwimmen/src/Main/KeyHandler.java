package Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    GamePanel gp;
    Graphics2D g2;
    boolean sperre = true;

    public KeyHandler(GamePanel gp) {

        this.gp = gp;
    }

    public void draw (Graphics2D g2) {

        this.g2 = g2;

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();


        //regelstate
        if (gp.gameState == gp.regelState) {



            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                    //nach unten
                }

            }
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                    //nach oben
                }
            }




            if (code == KeyEvent.VK_ENTER) {

                if (gp.ui.commandNum == 0) {
                    if (gp.ui.seite == 0) {


                    if (gp.ui.title == true) {
                        gp.ui.drawTitleScreen();
                        gp.gameState = gp.optionsState;
                        sperre = true;
                    } else if (gp.ui.ersterStart == true) {
                        gp.gameState = gp.startState;
                        sperre = true;
                    } else {

                        gp.spieler.run();
                        gp.gameState = gp.optionsState;
                        sperre = true;

                    }


                    gp.ui.regeln = false;
                    gp.ui.commandNum = 0;

                }
                    else{
                      gp.ui.seite --;
                    }
                }


                if (gp.ui.commandNum == 1) {
                    //weiter
                    if (gp.ui.seite == 0) {
                        gp.ui.seite++;
                    } else {
                        gp.gameState = gp.startState;
                        gp.ui.seite = 0;
                        sperre = true;
                    }
                }

            }

        }


        //tiltestate
        if (gp.gameState == gp.titleState) {


            if (code == KeyEvent.VK_W || code== KeyEvent.VK_UP) {
                gp.playSE(1);
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 3;
                }
            }
            if (code == KeyEvent.VK_S || code== KeyEvent.VK_DOWN) {
                gp.playSE(1);
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 3) {
                    gp.ui.commandNum = 0;
                }
            }


            if (code == KeyEvent.VK_ENTER) {

                if (gp.ui.commandNum == 0) {

                    gp.stopMusic(7);
                    gp.playMusic(0);
                    gp.ui.title = false;

                    if (gp.config.laden == false) {

                        gp.karten.sortieren();
                        gp.ui.herzenZurücksetzen();

                    } else {

                        gp.ui.ersterStart = false;

                    }

                    if (gp.ui.ersterStart == false) {

                        //gp.ui.backround();

                        //gp.timer.pause(0.5);


                        sperre = true;

                        gp.gameState = gp.schwierigkeitState;
                        gp.ui.commandNum = 0;


                    } else {

                        gp.gameState = gp.startState;
                        gp.ui.commandNum = 0;

                    }


                    gp.config.laden = false;


                }

                if (gp.ui.commandNum == 1) {

                    gp.playSE(6);
                    gp.gameState = gp.optionsState;
                    gp.ui.commandNum = 0;
                }

                if (gp.ui.commandNum == 2) {

                    //load config
                    gp.config.laden = true;
                    gp.config.loadConfig();

                }

                if (gp.ui.commandNum == 3) {
                    System.exit(0);
                }

            }
        }


        //runstate
        if (gp.gameState == gp.spielerState) {

            if (gp.ui.ersterStart == false) {
                if (code == KeyEvent.VK_ESCAPE) {

                    gp.playSE(6);
                    gp.gameState = gp.optionsState;
                }
            }

        }


        //startstate
        if (gp.gameState == gp.startState) {



            if (code == KeyEvent.VK_W || code== KeyEvent.VK_UP) {
                gp.playSE(1);
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                    //nach unten
                }
            }
            if (code == KeyEvent.VK_S|| code== KeyEvent.VK_DOWN) {
                gp.playSE(1);
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                    //nach oben
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0 && sperre == false) {
                    //spielanleitung
                    gp.playSE(1);
                    gp.gameState = gp.steuerung;
                    gp.ui.steuerung();
                    sperre = true;

                }

                if (gp.ui.commandNum == 1 && sperre == false) {
                    //regeln
                    gp.playSE(1);
                    gp.gameState = gp.regelState;
                    gp.ui.commandNum = 0;
                    sperre = true;

                }

                if (gp.ui.commandNum == 2 && sperre == false) {
                    //start
                    gp.playSE(1);
                    gp.ui.commandNum = 0;
                    gp.ui.ersterStart = false;
                    gp.gameState = gp.schwierigkeitState;
                    sperre = true;


                }
            }
        }


        //optionsstate
        if (gp.gameState == gp.optionsState) {

            if (code == KeyEvent.VK_ESCAPE) {
                if (gp.ui.title == true) {

                    gp.playSE(6);
                    gp.gameState = gp.titleState;

                } else {

                    gp.playSE(6);
                    gp.gameState = gp.spielerState;
                }
            }


            if (code == KeyEvent.VK_W || code== KeyEvent.VK_UP) {
                gp.playSE(1);
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 5;
                    //nach unten
                }
            }

            if (code == KeyEvent.VK_S || code== KeyEvent.VK_DOWN) {
                gp.playSE(1);
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 5) {
                    gp.ui.commandNum = 0;
                    //nach oben
                }
            }



            if (code == KeyEvent.VK_ENTER) {

                if (gp.ui.commandNum == 3) {
                    //regeln
                    gp.ui.commandNum = 0;
                    gp.gameState = gp.regelState;
                    gp.ui.drawRegelScreen();

                }

                if (gp.ui.title == true) {

                    if (gp.ui.commandNum == 4) {

                        //untenfeld (back)
                        gp.playSE(6);
                        gp.gameState = gp.titleState;
                        gp.ui.title = true;
                        gp.ui.commandNum = 0;

                    }

                } else {

                    if (gp.ui.commandNum == 4) {

                        //save game
                        gp.config.speichern = true;
                        gp.config.saveConfig();
                    }

                    if (gp.ui.commandNum == 5) {

                        //untenfeld (exit)
                        gp.stopMusic(0);
                        gp.playMusic(7);
                        gp.gameState = gp.titleState;
                        gp.ui.title = true;
                        gp.ui.commandNum = 0;

                    }

                }

            }



            if (code == KeyEvent.VK_A|| code== KeyEvent.VK_LEFT) {
                gp.playSE(1);

                if (gp.ui.commandNum == 0 && gp.ui.pixelscale > 0) {
                    //scale niedriger
                    gp.gameState = gp.warningState;
                    gp.ui.warning();
                    gp.ui.plus = false;
                }

                if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    //1 also musik
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(1);
                    gp.config.saveConfig();
                    }


                if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSE(1);
                    gp.config.saveConfig();
                }

                if (gp.music.volumeScale == 0 && gp.se.volumeScale == 0) {
                    gp.ui.mute = true;
                    gp.config.saveConfig();
                }
                
            }
            if (code == KeyEvent.VK_D ||  code== KeyEvent.VK_RIGHT) {
                gp.playSE(1);

                if (gp.ui.commandNum == 0 && gp.ui.pixelscale < 5) {
                    //scale erhöhen
                    gp.gameState = gp.warningState;
                    gp.ui.warning();
                    gp.ui.plus = true;
                }

                if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.ui.mute = false;
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(1);
                    gp.config.saveConfig();
                }

                if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
                    gp.ui.mute = false;
                    gp.se.volumeScale++;
                    gp.playSE(1);
                    gp.config.saveConfig();
                }
            }

        }


        //schwierigkeitsstate
        if (gp.gameState == gp.schwierigkeitState) {

            System.out.println(gp.ui.leicht);

            if (code == KeyEvent.VK_W || code== KeyEvent.VK_UP) {


                if (gp.ui.commandNum == 3) {

                    if (gp.ui.leicht == true || gp.ui.mittel == true || gp.ui.schwer == true) {

                        if (gp.ui.leicht == true) {

                            gp.ui.commandNum = 0;
                            gp.playSE(1);

                        }
                        if (gp.ui.mittel == true) {

                            gp.ui.commandNum = 1;
                            gp.playSE(1);

                        }
                        if (gp.ui.schwer == true) {

                            gp.ui.commandNum = 2;
                            gp.playSE(1);

                        }

                    } else {
                        gp.ui.commandNum = 0;
                        gp.playSE(1);
                    }

                }

            }

            if (code == KeyEvent.VK_S || code== KeyEvent.VK_DOWN) {
                gp.playSE(1);
                gp.ui.commandNum = 3;
            }

            if (code == KeyEvent.VK_A || code== KeyEvent.VK_LEFT) {
                if (gp.ui.leicht == false && gp.ui.mittel == false && gp.ui.schwer == false) {
                gp.playSE(1);
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                    //nach unten

                    }
                }
            }
            if (code == KeyEvent.VK_D || code== KeyEvent.VK_RIGHT) {
                if (gp.ui.leicht == false && gp.ui.mittel == false && gp.ui.schwer == false) {
                gp.playSE(1);
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                    //nach oben
                    }
                }
            }

            if (code == KeyEvent.VK_ENTER && sperre == false) {


                if (gp.ui.commandNum == 0) {
                    //leicht
                    gp.playSE(1);

                    if (gp.ui.leicht == false && gp.ui.mittel == false && gp.ui.schwer == false && sperre == false) {

                        gp.ui.leicht = true;
                        sperre = true;

                    }
                    if (gp.ui.leicht == true && sperre == false) {

                        gp.ui.leicht = false;
                        sperre = true;

                    }
                }

                if (gp.ui.commandNum == 1) {
                    //mittel
                    gp.playSE(1);

                    if (gp.ui.leicht == false && gp.ui.mittel == false && gp.ui.schwer == false && sperre == false) {
                        gp.ui.mittel = true;
                        sperre = true;
                    }
                    if (gp.ui.mittel == true && sperre == false) {
                        gp.ui.mittel = false;
                        sperre = true;
                    }

                }

                if (gp.ui.commandNum == 2) {
                    //mittel
                    gp.playSE(1);

                    if (gp.ui.leicht == false && gp.ui.mittel == false && gp.ui.schwer == false && sperre == false) {
                        gp.ui.schwer = true;
                        sperre = true;
                    }
                    if (gp.ui.schwer == true && sperre == false) {
                        gp.ui.schwer = false;
                        sperre = true;
                    }

                }


                if (gp.ui.commandNum == 3) {
                    //schwer

                    if (gp.ui.leicht == true || gp.ui.mittel == true || gp.ui.schwer == true) {


                        gp.playSE(1);
                        gp.ui.commandNum = 0;
                        gp.ui.ersterStart = false;

                        if (gp.ui.leicht == true) {

                            gp.bot.schwierigkeitsgrad = 2;

                        }
                        if (gp.ui.mittel == true) {

                            gp.bot.schwierigkeitsgrad = 3;

                        }
                        if (gp.ui.schwer == true) {

                            gp.bot.schwierigkeitsgrad = 5;

                        }

                        gp.config.laden = false;

                        gp.gameState = gp.backroundState;

                        gp.ui.gezeichnet = false;

                        gp.timer.pause(1);

                        gp.gameState = gp.spielerState;




                    }

                }
            }

        }


        //endState
        if (gp.gameState == gp.endState) {


            if (code == KeyEvent.VK_W || code== KeyEvent.VK_UP) {
                gp.playSE(1);
                gp.ui.commandNum--;

                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                    }
                }

                if (code == KeyEvent.VK_S || code== KeyEvent.VK_DOWN) {
                    gp.playSE(1);
                    gp.ui.commandNum++;

                    if (gp.ui.commandNum > 2) {
                        gp.ui.commandNum = 0;
                    }
                }


                if (code == KeyEvent.VK_ENTER) {

                    if (gp.ui.commandNum == 0) {

                        gp.playSE(1);
                        //erneut spielen
                        defaultwerte();
                        gp.karten.sortieren();
                        gp.ui.herzenZurücksetzen();
                        gp.ui.backround();
                        gp.karten.kartenzuteilen();
                        gp.gameState = gp.spielerState;


                    }


                    if (gp.ui.commandNum == 1) {

                        gp.playSE(1);
                        //schwierigkeit ändern
                        defaultwerte();
                        gp.karten.sortieren();
                        gp.ui.herzenZurücksetzen();
                        gp.gameState = gp.schwierigkeitState;



                    }

                    if (gp.ui.commandNum == 2) {

                        gp.playSE(1);

                        //beenden
                        gp.stopMusic(0);
                        gp.playMusic(7);
                        defaultwerte();
                        gp.karten.sortieren();
                        gp.ui.herzenZurücksetzen();
                        gp.gameState = gp.titleState;

                    }

                }

            }


        //zwischenstate
        if (gp.gameState == gp.zwischenState) {


            if (code == KeyEvent.VK_ENTER) {

                if (gp.ui.commandNum == 0) {

                    //evtl. commundnum auf 0 setzen

                    gp.playSE(1);

                    //fortsetzen
                    defaultwerteZwischenstand();
                    gp.karten.sortieren();





                    if(gp.spieler.startpunkt == 0){
                        gp.gameState= gp.botState;
                        gp.bot.bot=0;
                    }

                    if(gp.spieler.startpunkt == 1){
                        gp.gameState= gp.botState;
                        gp.bot.bot= 1;
                    }
                    if(gp.spieler.startpunkt == 2){
                        gp.gameState= gp.botState;
                        gp.bot.bot=2;
                    }
                    if(gp.spieler.startpunkt == 3){
                        gp.gameState= gp.spielerState;
                    }



                }
            }
        }


        //warningstate
        if (gp.gameState == gp.warningState) {


            if (code == KeyEvent.VK_W || code== KeyEvent.VK_UP) {

                gp.playSE(1);
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }

            }

            if (code == KeyEvent.VK_D || code== KeyEvent.VK_RIGHT) {

                gp.playSE(1);
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }

            }

            if (code == KeyEvent.VK_ENTER) {

                if (gp.ui.commandNum == 0) {


                    if (gp.ui.plus == true) {

                        gp.ui.pixelscale++;
                    }
                    if (gp.ui.plus == false) {

                        gp.ui.pixelscale--;
                    }

                        gp.checkScale();
                        gp.playSE(1);
                        gp.config.saveConfig();
                        gp.ui.plus = false;
                        System.exit(0);


                }

                if (gp.ui.commandNum == 1) {

                    gp.gameState = gp.optionsState;

                }
            }

        }


        //Steuerung
        if (gp.gameState == gp.steuerung && sperre == false) {

            if (code == KeyEvent.VK_ENTER) {

                if (gp.ui.title == true) {
                    gp.ui.drawTitleScreen();
                    gp.gameState = gp.optionsState;
                    sperre = true;
                } else if (gp.ui.ersterStart == true) {
                    gp.gameState = gp.startState;
                    sperre = true;
                } else {

                    gp.spieler.run();
                    gp.gameState = gp.optionsState;
                    sperre = true;

                }

            }


        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_ENTER) {

            if (sperre == true) {

                sperre = false;

            }

        }




    }


    public void defaultwerteZwischenstand() {

        gp.ui.commandNum = 0;

        gp.ui.gezeichnet = false;
        gp.ui.update = false;
        gp.ui.sperre = false;

        gp.bot.alletauschen = false;
        gp.bot.bot = 0;
        //bot 0 startet wieder wenn von bot getriggerd

        gp.spieler.auslöser = "";
        gp.spieler.auslöser2 = -1;
        gp.spieler.auslöserbestätigt = false;
        gp.spieler.sperre = false;
        gp.spieler.einunddreißig = false;
        gp.spieler.blitz = false;
        gp.spieler.schiebenmehrfach = 0;


        gp.spieler.schieben = false;
        gp.spieler.klopfen = false;
        gp.spieler.endgamephase = false;

        gp.mouseH.KarteMitteLinks = false;
        gp.mouseH.KarteMitteMitte = false;
        gp.mouseH.KarteMitteRechts = false;
        gp.mouseH.KarteSpielerLinks = false;
        gp.mouseH.KarteSpielerMitte = false;
        gp.mouseH.KarteSpielerRechts = false;

        gp.mouseH.doppelt = false;


    }


    public void defaultwerte() {

        gp.ui.commandNum = 0;


        //nur bei komplett neustart
        //gp.ui.ersterStart = true;


        gp.ui.regeln = false;


        gp.ui.title = true;


        //herzen


        gp.ui.gezeichnet = false;
        gp.ui.update = false;
        gp.ui.sperre = false;
        gp.ui.endsperre = false;


        gp.bot.alletauschen = false;
        gp.bot.bot1raus = false;
        gp.bot.bot2raus = false;
        gp.bot.bot3raus = false;


        gp.spieler.auslöser = "";
        gp.spieler.auslöser2 = -1;
        gp.spieler.auslöserbestätigt = false;
        gp.spieler.sperre = false;
        gp.spieler.einunddreißig = false;
        gp.spieler.blitz = false;
        gp.spieler.schiebenmehrfach = 0;



        gp.spieler.schieben = false;
        gp.spieler.klopfen = false;
        gp.spieler.endgamephase = false;
        gp.spieler.spielerraus = false;


        gp.mouseH.KarteMitteLinks = false;
        gp.mouseH.KarteMitteMitte = false;
        gp.mouseH.KarteMitteRechts = false;
        gp.mouseH.KarteSpielerLinks = false;
        gp.mouseH.KarteSpielerMitte = false;
        gp.mouseH.KarteSpielerRechts = false;

        gp.mouseH.doppelt = false;

    }

}
