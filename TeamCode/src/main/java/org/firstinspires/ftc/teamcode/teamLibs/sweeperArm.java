package org.firstinspires.ftc.teamcode.teamLibs;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class sweeperArm {

    private Telemetry telemetry;
    private goBuildAServo2000 baseServo;
    private goBuildAServo2000 armServo;
    private boolean sweeperRunning = false;
    //variables integers below are degress for servo positions
    private final int RETRACT = 140;

    // full extended up and down
    private final int BASE_EXTEND = 215;
    private final int ARM_EXTENDUP = 20;
    private final int ARM_EXTENDDOWN = 45;

    // at the base of the crater up and down
    private final int BASE_DCRATER = 200;
    private final int ARM_DCRATER = 80;
    private final int BASE_DCRATER_UP = 190;
    private final int ARM_DCRATER_UP = 70;

    // at the top of the crater up and down
    private final int BASE_TCRATER = 175;
    private final int ARM_TCRATER = 115;
    private final int BASE_TCRATER_UP = 175;
    private final int ARM_TCRATER_UP = 115;


    public sweeperArm(Telemetry theTel, HardwareMap hwMap, String baseServoName, String armServoName) {

        telemetry = theTel;

        baseServo = new goBuildAServo2000(hwMap.get(Servo.class, baseServoName), telemetry);
        armServo = new goBuildAServo2000(hwMap.get(Servo.class, armServoName), telemetry);
    }
    //methods to move srevos
    public void retract () {
        baseServo.goTo(RETRACT);
        teamUtil.sleep(250);
        armServo.goTo(RETRACT);
    }
    public void extendUpNoWait () {
        if (sweeperRunning == false) {
            sweeperRunning = true;
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    extendUp();
                }
            });
            t1.start();
        }
    }

    public void extendUp () {
        armServo.goTo(ARM_EXTENDUP);
        teamUtil.sleep(250);
        baseServo.goTo(BASE_EXTEND);
        sweeperRunning = false;

    }
    public void extendDown () {
        baseServo.goTo(BASE_EXTEND);
        armServo.goTo(ARM_EXTENDDOWN);
    }
    public void craterBase () {
        baseServo.goTo(BASE_DCRATER);
        armServo.goTo(ARM_DCRATER);
    }
    public void craterTop () {
        baseServo.goTo(BASE_TCRATER);
        armServo.goTo(ARM_TCRATER);
    }
    public void sweep () {
        baseServo.goTo(BASE_DCRATER);
        armServo.goTo(ARM_DCRATER);
        teamUtil.sleep(200);
        baseServo.goTo(BASE_TCRATER);
        armServo.goTo(ARM_TCRATER);
    }

    // forces the servos to the specified position...be careful!!
    public void goTo(int base, int arm) {
        baseServo.goTo(base);
        armServo.goTo(arm);

    }
    public void stickControl(float stick, boolean down){
        int base = 0;
        int arm = 0;
        if (stick > 0) {
            if (down) {
                base = (int) (BASE_DCRATER + (BASE_EXTEND - BASE_DCRATER) * stick);
                arm = (int) (ARM_DCRATER - (ARM_DCRATER - ARM_EXTENDDOWN) * stick);
            } else {
                base = (int) (BASE_DCRATER_UP + (BASE_EXTEND - BASE_DCRATER_UP) * stick);
                arm = (int) (ARM_DCRATER_UP - (ARM_DCRATER_UP - ARM_EXTENDUP) * stick);
            }
        } else {
            if (down) {
                base = (int) (BASE_TCRATER + (BASE_DCRATER - BASE_TCRATER) * stick);
                arm = (int) (ARM_DCRATER + (ARM_TCRATER - ARM_DCRATER) * stick *-1);
            } else {
                base = (int) (BASE_TCRATER + (BASE_DCRATER - BASE_TCRATER) * stick);
                arm = (int) (ARM_DCRATER + (ARM_TCRATER - ARM_DCRATER) * stick *-1);
            }

        }
        baseServo.goTo(base);
        armServo.goTo(arm);
    }


}
