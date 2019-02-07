package org.firstinspires.ftc.teamcode.teamLibs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class xRail {
    DcMotor motor;
    Telemetry telemetry;

    private final int EXTEND_ACTUAL = 830; // was 830
    private final int EXTEND_TARGET = EXTEND_ACTUAL+22;
    private final int DECEL_TARGET = EXTEND_ACTUAL-175;
    private boolean xrailRunning = false;

    private final double TIMEOUT_SECONDS = 2.5;

    //private final int RETRACT = ;

    public xRail(Telemetry thetelemetry,DcMotor motor1){
        telemetry=thetelemetry;
        motor=motor1;

    }

    public void init () {

        // use BRAKE mode to control retraction
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // wind up any slack in the string
        motor.setPower(.07);
        teamUtil.sleep(1000);
        motor.setPower(0);

        // reset the encoder and get ready to lift
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setTargetPosition(0);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void  extend () {
        motor.setPower(-0.50);
        telemetry.addData("xRailPosition",motor.getCurrentPosition());
    }
    public void retract () {
        motor.setPower(1);
        telemetry.addData("xRailPosition",motor.getCurrentPosition());
    }
    public void stop () {
        motor.setPower(-.15);
        telemetry.addData("xRailPosition",motor.getCurrentPosition());
    }
    public void loadLander() {

        ElapsedTime runtime = new ElapsedTime();

        // lift and wait for it to get to the top
        runtime.reset();
        motor.setTargetPosition(EXTEND_TARGET);
        motor.setPower(0.50);
        while ((runtime.seconds() < TIMEOUT_SECONDS) && (motor.getCurrentPosition()<EXTEND_ACTUAL)) {
            telemetry.addData("xrail", motor.getCurrentPosition());
            telemetry.update();
        }

        // drop it back down gently
        motor.setPower(0); // brake mode will slow it down
        motor.setTargetPosition(0); // slowly back up to starting spot
        motor.setPower(-.15);
        while ((runtime.seconds() < TIMEOUT_SECONDS) && (motor.getCurrentPosition()>5)) {
            telemetry.addData("xrail", motor.getCurrentPosition());
            telemetry.update();
        }
        motor.setPower(0); // otherwise we will drain the battery...
    }
    public void loadLanderV2() {

        ElapsedTime runtime = new ElapsedTime();

        // lift and wait for it to get to the top
        runtime.reset();
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setPower(0.990);
        while ((runtime.seconds() < TIMEOUT_SECONDS) && (motor.getCurrentPosition()<DECEL_TARGET)) {
            //telemetry.addData("xrail", motor.getCurrentPosition());
            //telemetry.update();
        }
        motor.setPower(0.99);
        while ((runtime.seconds() < TIMEOUT_SECONDS) && (motor.getCurrentPosition()< 2000 /*EXTEND_ACTUAL*/)) {
            //telemetry.addData("xrail", motor.getCurrentPosition());
            //telemetry.update();
        }

        // drop it back down gently

        motor.setPower(-.15);
        while ((runtime.seconds() < TIMEOUT_SECONDS) && (motor.getCurrentPosition()>5)) {
            telemetry.addData("xrail", motor.getCurrentPosition());
            telemetry.update();
        }
        motor.setPower(0); // otherwise we will drain the battery...
    }





    public void loadLander2(float initialSpeed) {

        ElapsedTime runtime = new ElapsedTime();
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // lift and wait for it to get to the top
        runtime.reset();
        motor.setTargetPosition(EXTEND_TARGET);
        teamUtil.log("starting at:" + initialSpeed);
        motor.setPower(initialSpeed);
        while ((runtime.seconds() < TIMEOUT_SECONDS) && (motor.getCurrentPosition() < DECEL_TARGET)) {
            //telemetry.addData("xrail", motor.getCurrentPosition());
            //telemetry.update();
        }
        teamUtil.log("decelerating to .2 at :" + motor.getCurrentPosition());
        motor.setPower(.20);
        while ((runtime.seconds() < TIMEOUT_SECONDS) && (motor.getCurrentPosition() < EXTEND_ACTUAL)) {
            //telemetry.addData("xrail", motor.getCurrentPosition());
            //telemetry.update();
        }
        teamUtil.log("retracting at :" + motor.getCurrentPosition());

        // drop it back down gently
        motor.setPower(0); // brake mode will slow it down
        motor.setTargetPosition(0); // slowly back up to starting spot
        motor.setPower(-.15);
        while ((motor.getCurrentPosition() > 5)) {
            //telemetry.addData("xrail", motor.getCurrentPosition());
            //telemetry.update();
        }
        motor.setPower(0); // otherwise we will drain the battery...
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        teamUtil.log("done at :" + motor.getCurrentPosition());
    }
        public void fullDumpNoWait () {
            if (xrailRunning == false) {
                xrailRunning = true;
                Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    fullDump(1);
                }
            });
                t1.start();
        }
    }
        public void fullDump(float initialSpeed)  {
            ElapsedTime runtime = new ElapsedTime();
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            // lift and wait for it to get to the top
            runtime.reset();
            teamUtil.log("starting at:"+initialSpeed);
            motor.setPower(initialSpeed);
            while ((runtime.seconds() < TIMEOUT_SECONDS) && (motor.getCurrentPosition()<DECEL_TARGET)) {
                //telemetry.addData("xrail", motor.getCurrentPosition());
                //telemetry.update();
            }

            int lastPosition;
            motor.setPower(.5);
            lastPosition = motor.getCurrentPosition();//sets last position to the current encoder position of the robot
            teamUtil.sleep(500);//wait's 100ms so the motor has time to run a little
            while (teamUtil.theOpMode.opModeIsActive() && (lastPosition != motor.getCurrentPosition())) {
                lastPosition = motor.getCurrentPosition();//This while loop makes it so if the positon of the motor is the same
                teamUtil.sleep(250);           //after 100ms than we know it hit the bottom and stalled
            }

            teamUtil.log("retracting at :" + motor.getCurrentPosition());

            // drop it back down gently
            motor.setPower(0); // brake mode will slow it down
            motor.setTargetPosition(0); // slowly back up to starting spot
            motor.setPower(-.15);
            while ((motor.getCurrentPosition() > 5)) {
                //telemetry.addData("xrail", motor.getCurrentPosition());
                //telemetry.update();
            }
            motor.setPower(0); // otherwise we will drain the battery...
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            teamUtil.log("done at :" + motor.getCurrentPosition());
            xrailRunning = false;
        }
    }


