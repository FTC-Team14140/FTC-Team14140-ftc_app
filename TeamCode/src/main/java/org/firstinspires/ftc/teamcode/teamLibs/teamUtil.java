package org.firstinspires.ftc.teamcode.teamLibs;

public class teamUtil {

    public static final double YELLOW_JACKET_ENCODER_CLICKS=145.6;
    public static final double NEVERREST40_ENCODER_CLICKS=1120;

    /**
     * This method puts the current thread to sleep for the given time in msec.
     * It handles InterruptException where it recalculates the remaining time
     * and calls sleep again repeatedly until the specified sleep time has past.
     *
     * @param sleepTime specifies sleep time in msec.
     */
    public static void sleep(long sleepTime) {
        long wakeupTime=System.currentTimeMillis()+sleepTime;
        while(sleepTime>0){
            try {
                Thread.sleep(sleepTime);
            } catch(InterruptedException e) {
            }
            sleepTime=wakeupTime-System.currentTimeMillis();
        }
    }   //sleep

}
