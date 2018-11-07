package org.firstinspires.ftc.teamcode.teamLibs;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class grabberArm {

    private Telemetry telemetry;
    private goBuildAServo2000 grabberServo;
    private goBuildAServo2000 liftServo;
    //variables below are degress for servo positions
    private final int DOWN_SPOT = 150;
    private final int UP = 30;
    private final int GRAB = 60:
    private final int SKINNY = 35:
    private final int WIDE = 0;
    private final int DROP = 0;

    grabberArm(Telemetry theTel, HardwareMap hwMap, string grabServoName, string liftServoName) {

        telemetry = theTel;

        grabberServo = new goBuildAServo2000(telemetry, hwMap.get(Servo.class, grabServoName);
        liftServo = new goBuildAServo2000(telemetry, hwMap.get(Servo.class, liftServoName);
    }
//methods to move srevos
    void initialize () {
        liftServo.goTo(UP);
        grabberServo.goTo(WIDE);
    }

    void grab () {
        grabberServo.goTo(GRAB);
    }

    void skinnyOpen () {
        grabberServo.goTo(SKINNY);
    }

    void wideOpen () {
        grabberServo.goTo(WIDE);
    }

    void holdUp () {
        liftServo.goTo(UP);
    }

    void deposit () {
        liftServo.goTo(DROP)
    }

    void grabberDown () {
        liftServo.goTo(DOWN_SPOT);
    }

   // private grabberArm = new grabberArm (hardwaremap, telemetery, "grabberServo", "liftServo")
}
