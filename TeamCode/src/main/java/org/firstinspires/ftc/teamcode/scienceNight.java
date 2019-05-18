package org.firstinspires.ftc.teamcode;

import android.widget.Space;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.teamLibs.basicMovement;
import org.firstinspires.ftc.teamcode.teamLibs.goBuildAServo2000;
import org.firstinspires.ftc.teamcode.teamLibs.revHubIMUGyro;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.teamLibs.grabberArm;
import org.firstinspires.ftc.teamcode.teamLibs.teamColorSensor;
import org.firstinspires.ftc.teamcode.teamLibs.xRail;
import org.firstinspires.ftc.teamcode.teamLibs.linearActuator;
import org.firstinspires.ftc.teamcode.teamLibs.teamUtil;
import org.firstinspires.ftc.teamcode.teamLibs.sweeperArm;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Sience TeleOp", group="Linear Opmode")
public class scienceNight extends LinearOpMode{
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private grabberArm grabber;
    private xRail xrail;
    private sweeperArm sweeper;
    private boolean sweeperOut = false;

    final static double DPAD_SPEED = .5;
    private boolean grabberOut = false;



    // 0.15 is the threshold that the motor starts to accelerate
    private static final double deadSpot = 0.15;

    @Override
    public void runOpMode() {

        teamUtil.theOpMode = this;
        // Get the objects for the various pieces of hardware
        //imu = hardwareMap.get(Gyroscope.class, "imu");
        motorLeft = hardwareMap.get(DcMotor.class, "motorLeft");
        motorRight = hardwareMap.get(DcMotor.class, "motorRight");
        //when we say go forwards, we really mean backwards
        motorLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        xrail = new xRail(telemetry, hardwareMap.get(DcMotor.class, "xRailMotor"));
        sweeper = new sweeperArm(telemetry, hardwareMap, "retrieveBaseServo", "retrieveArmServo");


        grabber = new grabberArm (telemetry, hardwareMap, "grabberServo", "liftServo");

        telemetry.addData("Status", "TELEOP: Ready to Ruckus!");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        //grabber.initialize(); // don't move servos until play starts!

        // reseet the LA position so we are ready to extend at end of match
        //La.retractFullyNoWait();

        //These are the variables for the motors power at the start of the program
        double tgtPowerRight = 0;
        double tgtPowerLeft = 0;
        double speedFactor = 0.6;


        //gyro.resetHeading();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //telemetry.addData( "Gyro Heading",gyro.getHeading());

            ///////////////////////////////////////////////////////////////////
            // Figure out if we need to change the power to enable a turn
            if (gamepad1.dpad_down){
                tgtPowerLeft = -DPAD_SPEED;
                tgtPowerRight = -DPAD_SPEED;
            }else if (gamepad1.dpad_left){
                tgtPowerLeft = -DPAD_SPEED;
                tgtPowerRight = DPAD_SPEED;
            }else if (gamepad1.dpad_up){
                tgtPowerLeft = DPAD_SPEED;
                tgtPowerRight = DPAD_SPEED;
            }else if (gamepad1.dpad_right) {
                tgtPowerLeft = DPAD_SPEED;
                tgtPowerRight = -DPAD_SPEED;
            }else{
                tgtPowerLeft = 0;
                tgtPowerRight = 0;
            }
            // Set the robots motors to the corresponding power with the high/low gear speed factor included
            motorRight.setPower(tgtPowerRight*speedFactor);
            motorLeft.setPower(tgtPowerLeft*speedFactor);

            ///////////////////////////////////////////////////////////////////
            // Code to control the grabber servos
            if (gamepad2.left_bumper) {
                grabber.deposit();
                grabberOut = false;
            } else if(gamepad2.dpad_down && !sweeperOut) {
                grabber.grabberDown();
                grabberOut = true;
            } else if ((gamepad2.right_trigger > 0) && grabberOut && !grabber.isArmRunning()) {
                grabber.triggerControl(gamepad2.right_trigger);
            }

            ////////////////////////////////////////////////////////////////////
            // Code to control the sweeper servos
            if(gamepad2.x && !grabberOut){
                teamUtil.log("extending to up position");
                sweeperOut = true;
                sweeper.extendUpNoWait();
                //sweeper.craterTop();
            } else
            if(gamepad2.a) {
                sweeper.retract();
                sweeperOut = false;
            } else if(gamepad2.b && !grabberOut) {
                sweeperOut = true;
                sweeper.extendDown();
                //sweeper.extendUpNoWait();
            } else if(gamepad2.y && !grabberOut) {
                sweeperOut = true;
                sweeper.megaSweepNoWait();
                //sweeper.sweep();
            }

            ////////////////////////////////////////////////////////////////////
            // Code to control the xrail system
            if(gamepad2.right_stick_button) {
                xrail.fullDumpNoWait();
            }
            /////////////////////////////////////////////////////////////////////
            //Teclemtry
            telemetry.addData("MotorLeft Power", motorLeft.getPower());
            telemetry.addData("MotorRight Power", motorRight.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();
        }

    }
}
