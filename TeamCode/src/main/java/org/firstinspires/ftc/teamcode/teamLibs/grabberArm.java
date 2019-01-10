package org.firstinspires.ftc.teamcode.teamLibs;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class grabberArm {

    private Telemetry telemetry;
    private goBuildAServo2000 grabberServo;
    private goBuildAServo2000 liftServo;
    private boolean armRunning = false;
    //variables integers below are degress for servo positions
    private final int DOWN_SPOT = 240;
    private final int UP_SPOT = 115;
    private final int GRAB = 65;
    private final int SKINNY = 35;
    private final int WIDE = 0;
    private final int DROP = 90;


    public grabberArm(Telemetry theTel, HardwareMap hwMap, String grabServoName, String liftServoName) {

        telemetry = theTel;

        grabberServo = new goBuildAServo2000(hwMap.get(Servo.class, grabServoName), telemetry);
        liftServo = new goBuildAServo2000(hwMap.get(Servo.class, liftServoName), telemetry);
    }
//methods to move srevos on grabber
    public void initialize () {
        liftServo.goTo(UP_SPOT);
        grabberServo.goTo(WIDE);
    }

    public boolean isArmRunning() {
        return armRunning;
    }
    public void grab () {
        grabberServo.goTo(GRAB);
    }

    public void skinnyOpen () {
        grabberServo.goTo(SKINNY);
    }

    public void wideOpen () {
        grabberServo.goTo(WIDE);
    }

    public void holdUp () {
        grabberServo.goTo(GRAB);
        liftServo.goTo(UP_SPOT);
    }
    public void up () {
        liftServo.goTo(UP_SPOT);
        grabberServo.goTo(SKINNY);
    }

    public void deposit () {
        if (armRunning == false) {
            armRunning = true;
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                   depositWait();
                }
            });
            t1.start();
        }
    }

    public void depositWait () {
        grabberServo.goTo(GRAB); //
        liftServo.goTo(DROP);
        teamUtil.sleep(1000);
        grabberServo.goTo(SKINNY); //
        teamUtil.sleep(250);
        holdUp();
        armRunning = false;
    }

    public void grabberDown () {
        liftServo.goTo(DOWN_SPOT);
    }

    public void autoInitialize () {
        //grabberServo.goTo(WIDE);
        //teamUtil.sleep(500);
        //liftServo.goTo(DROP);
        //teamUtil.sleep(1000);
        //grabberServo.goTo(50);

        //new code
        grabberServo.goTo(GRAB);
        liftServo.goTo(UP_SPOT);
    }

    public void triggerControl(float trigger){
        grabberServo.goTo((int) (WIDE+(GRAB-WIDE)*trigger));
    }
    public void extend () {
        liftServo.goTo(165);
    }

   // private grabberArm = new grabberArm (hardwaremap, telemetery, "grabberServo", "liftServo")
}
