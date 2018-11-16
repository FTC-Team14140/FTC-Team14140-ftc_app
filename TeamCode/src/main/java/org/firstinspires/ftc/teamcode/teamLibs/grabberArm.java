package org.firstinspires.ftc.teamcode.teamLibs;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class grabberArm {

    private Telemetry telemetry;
    private goBuildAServo2000 grabberServo;
    private goBuildAServo2000 liftServo;
    //variables integers below are degress for servo positions
    private final int DOWN_SPOT = 150;
    private final int UP_SPOT = 40;
    private final int GRAB = 65;
    private final int SKINNY = 35;
    private final int WIDE = 0;
    private final int DROP = 0;

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
        liftServo.goTo(UP_SPOT);
    }

    public void deposit () {
        liftServo.goTo(DROP);
        teamUtil.sleep(1000);
        grabberServo.goTo(SKINNY);
    }

    public void grabberDown () {
        liftServo.goTo(DOWN_SPOT);
    }

   // private grabberArm = new grabberArm (hardwaremap, telemetery, "grabberServo", "liftServo")
}
