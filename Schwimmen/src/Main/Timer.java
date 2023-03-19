package Main;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

class Timer {

    GamePanel gp;

    public Timer (GamePanel gp) {

        this.gp = gp;

    }


    public void pause(double sec) {

        gp.drawToTempScreen(); //draw buffered image (not on screen)
        gp.drawToScreen(); // draw on screen (resized)


        try {
            //System.out.printf("Start Time: %s\n", LocalTime.now());
            TimeUnit.SECONDS.sleep((long) sec);  // Wait 2 seconds
            //System.out.printf("End Time: %s\n", LocalTime.now());
        } catch (InterruptedException e) {

            e.printStackTrace();

        }

        gp.bot.pause = false;

    }

}
