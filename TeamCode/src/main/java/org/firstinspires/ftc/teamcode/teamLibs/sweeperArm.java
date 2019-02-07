package org.firstinspires.ftc.teamcode.teamLibs;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class sweeperArm {

    private Telemetry telemetry;
    private HiTecServo baseServo;
    private goBuildAServo2000 armServo;
    enum Positions {
        RETRACTED, OUTUP, OUTDOWN, CRATERDOWN, CRATERTOP
    }
    private Positions currentPosition;

    private boolean sweeperRunning = false;
    //variables integers below are degress for servo positions
    private final int RETRACT_ARM = 196;
    private final int RETRACT_BASE = 59;

    // full extended up and down
    private final int BASE_EXTEND = 127;
    private final int ARM_EXTENDUP = 55;
    private final int ARM_EXTENDDOWN = 76;

    // at the base of the crater up and down
    private final int BASE_DCRATER = 111;
    private final int ARM_DCRATER = 135;
    private final int BASE_DCRATER_UP = 190;
    private final int ARM_DCRATER_UP = 70;

    // at the top of the crater up and down
    private final int BASE_TCRATER = 90;
    private final int ARM_TCRATER = 173;
    private final int BASE_TCRATER_UP = 175;
    private final int ARM_TCRATER_UP = 115;


    public sweeperArm(Telemetry theTel, HardwareMap hwMap, String baseServoName, String armServoName) {

        telemetry = theTel;

        baseServo = new HiTecServo(hwMap.get(Servo.class, baseServoName), telemetry);
        armServo = new goBuildAServo2000(hwMap.get(Servo.class, armServoName), telemetry);
        currentPosition = Positions.RETRACTED;
    }
    //methods to move srevos
    public void retract () {
        if (currentPosition == Positions.CRATERTOP) {
            glideBoth(BASE_TCRATER, RETRACT_BASE, ARM_TCRATER, RETRACT_ARM, 500);
        } else if (currentPosition == Positions.OUTUP) {
            glideBoth(BASE_EXTEND, RETRACT_BASE, ARM_EXTENDUP, ((RETRACT_ARM - ARM_EXTENDUP) / 3) * 2 + ARM_EXTENDUP, 1000);
            glideArm(((RETRACT_ARM - ARM_EXTENDUP) / 3) * 2 + ARM_EXTENDUP, RETRACT_ARM, 500);
        } else if (currentPosition == Positions.OUTDOWN) {
            glideBoth(BASE_EXTEND, RETRACT_BASE, ARM_EXTENDDOWN, ((RETRACT_ARM - ARM_EXTENDDOWN) / 3) * 2 + ARM_EXTENDDOWN, 1000);
            glideArm(((RETRACT_ARM - ARM_EXTENDDOWN) / 3) * 2 + ARM_EXTENDDOWN, RETRACT_ARM, 500);
        }
        //baseServo.goTo(RETRACT_BASE);
        //teamUtil.sleep(250);
        //armServo.goTo(RETRACT_ARM);
        currentPosition = Positions.RETRACTED;
        sweeperRunning = false;
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
    public void megaSweepNoWait () {
        if (sweeperRunning == false) {
            sweeperRunning = true;
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    megaSweeper();
                }
            });
            t1.start();
        }
    }

    private void glideBase (double start, double end, int mseconds){
        teamUtil.log("glideBase start:"+start +" end:"+end +" mseconds:"+mseconds);
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        while ((runtime.milliseconds() < mseconds) && teamUtil.theOpMode.opModeIsActive()) {
            baseServo.goTo((int) ((end - start) * (runtime.milliseconds() / mseconds) + start));
        }
    }

    private void glideArm (double start, double end, int mseconds){
        teamUtil.log("glideArm start:"+start +" end:"+end +" mseconds:"+mseconds);
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        while ((runtime.milliseconds() < mseconds) && teamUtil.theOpMode.opModeIsActive()) {
            armServo.goTo((int) ((end - start) * (runtime.milliseconds() / mseconds) + start));
        }
    }
    private void glideBoth (double baseStart, double baseEnd, double armStart, double armEnd,int mseconds){
        teamUtil.log("glideBoth baseStart:"+baseStart +" baseEnd:"+baseEnd +"armStart:"+armEnd +" baseEnd:"+armEnd +" mseconds:"+mseconds);
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        while ((runtime.milliseconds() < mseconds) && teamUtil.theOpMode.opModeIsActive()) {
            armServo.goTo((int) ((armEnd - armStart) * (runtime.milliseconds() / mseconds) + armStart));
            baseServo.goTo((int) ((baseEnd - baseStart) * (runtime.milliseconds() / mseconds) + baseStart));
        }
    }

    public void extendUp () {
        if (currentPosition == Positions.RETRACTED) {
            teamUtil.log("extending from retracted position");

            // for the first second, fully extend the arm and move the base half way
            glideBoth( RETRACT_BASE, (BASE_EXTEND - RETRACT_BASE) / 2 + RETRACT_BASE, RETRACT_ARM,  ARM_EXTENDUP,  1000);
            // then finish moving the arm into position
            glideBase((BASE_EXTEND - RETRACT_BASE) / 2 + RETRACT_BASE, BASE_EXTEND, 1000);
            /*
            ElapsedTime runtime = new ElapsedTime();
            runtime.reset();
            while ((runtime.milliseconds() < 2000) && teamUtil.theOpMode.opModeIsActive()) {
                baseServo.goTo((int) ((BASE_EXTEND - RETRACT_BASE) / (runtime.milliseconds() / 2000) + RETRACT_BASE));
                armServo.goTo(Math.max(ARM_EXTENDUP, (int) ((ARM_EXTENDUP - RETRACT_ARM) / (runtime.milliseconds() / 1000) + RETRACT_ARM)));
            }
            */
        }else if (currentPosition == Positions.OUTDOWN) {
            teamUtil.log("extending from OUTDOWN position");
            glideArm(ARM_EXTENDDOWN, ARM_EXTENDUP, 500);
        }else if (currentPosition == Positions.CRATERTOP) {
            teamUtil.log("extending from CRATERTOP position");
            glideBoth( BASE_TCRATER, (BASE_TCRATER - RETRACT_BASE) / 2 + RETRACT_BASE, RETRACT_ARM,  ARM_EXTENDUP,  1000);
            glideBase((BASE_TCRATER - RETRACT_BASE) / 2 + RETRACT_BASE, BASE_EXTEND, 1000);
        }

        //armServo.goTo(ARM_EXTENDUP);
        //teamUtil.sleep(250);
        //baseServo.goTo(BASE_EXTEND);
        currentPosition = Positions.OUTUP;
        sweeperRunning = false;
    }
    public void extendDown () {
        if (currentPosition == Positions.RETRACTED) {
            glideBoth( RETRACT_BASE, (BASE_EXTEND - RETRACT_BASE) / 2 + RETRACT_BASE, RETRACT_ARM,  ARM_EXTENDDOWN,  1000);
            glideBase((BASE_EXTEND - RETRACT_BASE) / 2 + RETRACT_BASE, BASE_EXTEND, 1000);
        } else if (currentPosition == Positions.CRATERTOP) {
            glideBoth( BASE_TCRATER, (BASE_TCRATER - RETRACT_BASE) / 2 + RETRACT_BASE, RETRACT_ARM,  ARM_EXTENDDOWN,  1000);
            glideBase((BASE_TCRATER - RETRACT_BASE) / 2 + RETRACT_BASE, BASE_EXTEND, 1000);
        } else if (currentPosition == Positions.OUTUP) {
            glideArm(ARM_EXTENDUP, ARM_EXTENDDOWN, 500);
        }

       /// baseServo.goTo(BASE_EXTEND);
        ///armServo.goTo(ARM_EXTENDDOWN);
        currentPosition = Positions.OUTUP;
        sweeperRunning = false;
    }
    public void megaSweeper () {
        if (currentPosition == Positions.RETRACTED) {
            glideBoth( RETRACT_BASE, (BASE_EXTEND - RETRACT_BASE) / 2 + RETRACT_BASE, RETRACT_ARM,  ARM_EXTENDUP,  1000);
            glideBase((BASE_EXTEND - RETRACT_BASE) / 2 + RETRACT_BASE, BASE_EXTEND, 1000);
            glideArm(ARM_EXTENDUP, ARM_EXTENDDOWN, 500);
            glideBoth(BASE_EXTEND, BASE_DCRATER, ARM_EXTENDDOWN, ARM_DCRATER, 1000);
            glideBoth(BASE_DCRATER, BASE_TCRATER, ARM_DCRATER, ARM_TCRATER, 500);
        } else if (currentPosition == Positions.OUTUP) {
            glideArm(ARM_EXTENDUP, ARM_EXTENDDOWN, 500);
            glideBoth(BASE_EXTEND, BASE_DCRATER, ARM_EXTENDDOWN, ARM_DCRATER, 1000);
            glideBoth(BASE_DCRATER, BASE_TCRATER, ARM_DCRATER, ARM_TCRATER, 500);
        } else if (currentPosition == Positions.OUTDOWN) {
            glideBoth(BASE_EXTEND, BASE_DCRATER, ARM_EXTENDDOWN, ARM_DCRATER, 1000);
            glideBoth(BASE_DCRATER, BASE_TCRATER, ARM_DCRATER, ARM_TCRATER, 500);
        } else if (currentPosition == Positions.CRATERTOP) {
            glideBoth( BASE_TCRATER, (BASE_EXTEND - BASE_TCRATER) / 2 + BASE_TCRATER, ARM_TCRATER,  ARM_EXTENDUP,  1000);
            glideBase((BASE_EXTEND - BASE_TCRATER) / 2 + BASE_TCRATER, BASE_EXTEND, 1000);
            glideArm(ARM_EXTENDUP, ARM_EXTENDDOWN, 500);
            glideBoth(BASE_EXTEND, BASE_DCRATER, ARM_EXTENDDOWN, ARM_DCRATER, 1000);
            glideBoth(BASE_DCRATER, BASE_TCRATER, ARM_DCRATER, ARM_TCRATER, 500);
        }
        else if (currentPosition == Positions.CRATERDOWN) {
            glideBoth( BASE_DCRATER, (BASE_EXTEND - BASE_DCRATER) / 2 + BASE_DCRATER, ARM_DCRATER,  ARM_EXTENDUP,  1000);
            glideBase((BASE_EXTEND - BASE_DCRATER) / 2 + BASE_DCRATER, BASE_EXTEND, 1000);
            glideArm(ARM_EXTENDUP, ARM_EXTENDDOWN, 500);
            glideBoth(BASE_EXTEND, BASE_DCRATER, ARM_EXTENDDOWN, ARM_DCRATER, 1000);
            glideBoth(BASE_DCRATER, BASE_TCRATER, ARM_DCRATER, ARM_TCRATER, 500);
        }
        sweeperRunning = false;
        currentPosition = Positions.CRATERTOP;
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
