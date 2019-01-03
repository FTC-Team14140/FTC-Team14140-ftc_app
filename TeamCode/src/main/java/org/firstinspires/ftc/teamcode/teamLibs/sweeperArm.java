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
    private final int ARM_EXTENDUP = 35;
    private final int BASE_EXTEND = 210;
    private final int ARM_EXTENDDOWN = 50;
    private final int BASE_DCRATER = 200;
    private final int ARM_DCRATER = 80;
    private final int BASE_TCRATER = 175;
    private final int ARM_TCRATER = 115;


    public sweeperArm(Telemetry theTel, HardwareMap hwMap, String baseServoName, String armServoName) {

        telemetry = theTel;

        baseServo = new goBuildAServo2000(hwMap.get(Servo.class, baseServoName), telemetry);
        armServo = new goBuildAServo2000(hwMap.get(Servo.class, armServoName), telemetry);
    }
    //methods to move srevos
    public void retract () {
        baseServo.goTo(RETRACT);
        armServo.goTo(RETRACT);
    }
    public void extendUp () {
        baseServo.goTo(BASE_EXTEND);
        armServo.goTo(ARM_EXTENDUP);
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

}
