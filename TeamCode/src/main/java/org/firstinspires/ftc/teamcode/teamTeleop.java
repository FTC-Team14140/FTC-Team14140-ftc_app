package org.firstinspires.ftc.teamcode;

import android.widget.Space;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.teamLibs.basicMovement;
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

@TeleOp(name="Comp TeleOp", group="Linear Opmode")
public class teamTeleop extends LinearOpMode {
    //private revHubIMUGyro gyro;
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private grabberArm grabber;
    private xRail xrail;
    private sweeperArm sweeper;
    private linearActuator La;
    private teamColorSensor leftColor;
    private teamColorSensor rightColor;
    private basicMovement basicMove;
    final static double DPAD_SPEED = .5;
    private boolean grabberOut = false;
    private boolean sweeperOut = false;


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

        grabber = new grabberArm (telemetry, hardwareMap, "grabberServo", "liftServo");
        xrail = new xRail(telemetry, hardwareMap.get(DcMotor.class, "xRailMotor"));
        //gyro = new revHubIMUGyro(hardwareMap.get(BNO055IMU.class, "imu"), telemetry );
        La = new linearActuator(telemetry, hardwareMap.get(DcMotor.class, "LAMotor"));
        basicMove = new basicMovement(motorLeft, motorRight, hardwareMap.get(BNO055IMU.class,"imu"), telemetry);
        teamUtil.log("initializing colorSensors...");
        leftColor = new teamColorSensor(telemetry,hardwareMap.get(ColorSensor.class,"leftRearColor"));
        rightColor = new teamColorSensor(telemetry,hardwareMap.get(ColorSensor.class,"rightRearColor"));
        sweeper = new sweeperArm(telemetry, hardwareMap, "retrieveBaseServo", "retrieveArmServo");
        xrail.init();

        // Wait for the game to start (driver presses PLAY)
         waitForStart();
        //grabber.initialize(); // don't move servos until play starts!

         // reseet the LA position so we are ready to extend at end of match
        //La.retractFullyNoWait();

        //These are the variables for the motors power at the start of the program
        double tgtPowerRight = 0;
        double tgtPowerLeft = 0;
        double speedFactor = 1;


        //gyro.resetHeading();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //telemetry.addData( "Gyro Heading",gyro.getHeading());

            ////////////////////////////////////////////////////////////////////
            // code to control the main motors and steering

            // We use these variables to figure out how to control the X or turning for the robot right stick and the Y or movement on the left stick
            double throttle = -this.gamepad1.left_stick_y;
            double steering = this.gamepad1.right_stick_x;

            //  It puts in the throttle variable to figure out how far you need to push the joystick for the corresponding motor speed
            if (throttle > 0) {
                // formula to use figures out what the area of effect for the joysticks is (OK, but what does the formula do?)
                throttle = (1 - deadSpot) * throttle + deadSpot;
            }else if (throttle < 0){
                throttle = (1 - deadSpot) * throttle- deadSpot;
            }

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
            } else if (steering > 0){ // If we are turning to the right then we give more power to the left motor
                tgtPowerRight = throttle-steering*2*throttle;
                tgtPowerLeft = throttle;
            } else if (steering < 0){ // if we are turning to the left then we give more power to the right motor
                tgtPowerRight = throttle;
                tgtPowerLeft =  throttle+steering*2*throttle;
            } else { // if we are not turning at all than equal power to both motors
                tgtPowerRight = throttle;
                tgtPowerLeft = throttle;
            }

            // Allow the user to "shift" to high or low gear
            if (gamepad1.right_bumper){
                speedFactor = 1; // high gear, up to full motor power
            }else if (gamepad1.left_bumper) {
                speedFactor = 0.6; // low gear, up to 60% motor power
            }
            // Set the robots motors to the corresponding power with the high/low gear speed factor included
            motorRight.setPower(tgtPowerRight*speedFactor);
            motorLeft.setPower(tgtPowerLeft*speedFactor);

            ////////////////////////////////////////////////////////////////////
            // Code to control the sweeper servos
            if(gamepad2.x && !grabberOut){
                sweeper.craterTop();
                sweeperOut = true;
            } else
            if(gamepad2.a) {
                sweeper.retract();
                sweeperOut = false;
            } else if(gamepad2.b && !grabberOut) {
                sweeper.extendUpNoWait();
                sweeperOut = true;
            } else if(gamepad2.y && !grabberOut) {
                sweeper.sweep();
                sweeperOut = true;
            } else if (gamepad2.right_bumper && !grabberOut) {
                sweeper.extendDown();
                sweeperOut = true;
            }
            //else  if ((gamepad2.left_stick_y != 0) && !grabberOut) { // maybe this should only work if the sweeper is already out?
            //  sweeper.stickControl(-gamepad2.left_stick_y, gamepad2.left_stick_button);
            //}

            ///////////////////////////////////////////////////////////////////
            // Code to control the grabber servos
             if (gamepad2.left_bumper && grabberOut) {
                grabber.deposit();
                grabberOut = false;
            } else if(gamepad2.dpad_up) {
                grabber.holdUp();
                grabberOut = false;
            } else if(gamepad2.dpad_down && !sweeperOut ) {
                grabber.grabberDown();
                grabberOut = true;
            } else if ((gamepad2.right_trigger > 0) && grabberOut && !grabber.isArmRunning()) {
                grabber.triggerControl(gamepad2.right_trigger);
            }

            ////////////////////////////////////////////////////////////////////
            // Code to control the xrail system
            if(gamepad2.right_stick_button) {
                xrail.fullDumpNoWait();
            }

            ////////////////////////////////////////////////////////////////////
            // Code to control the linear actuator
            if(gamepad1.a) {
                La.retractFullyNoWait();
            } else if(gamepad1.y) {
                La.extendFullyNoWait();
            } else if(gamepad1.x) {
                La.extend();
            } else if(gamepad1.b) {
                La.retract();
            } else {
                La.stopMotor();
            }

            //telemetry.addData("Servo Position", servoTest.getPosition());
            //telemetry.addData("MotorLeft Target Power", tgtPowerLeft);
            //telemetry.addData("MotorRight Target Power", tgtPowerRight);
            telemetry.addData("MotorLeft Power", motorLeft.getPower());
            telemetry.addData("MotorRight Power", motorRight.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();
        }

    }

    public void autoDump() {
        boolean leftTapeSeen = false;
        boolean rightTapeSeen = false;
        basicMove.motorsOn(-0.2);
        while (!leftTapeSeen || !rightTapeSeen){
            teamUtil.log("sensing for line...");
            if(leftColor.isOnTape()){
                motorLeft.setPower(0);
                leftTapeSeen = true;
                teamUtil.log("Left color sensor on tape");
            }
            if (rightColor.isOnTape()){
                motorRight.setPower(0);
                rightTapeSeen = true;
                teamUtil.log("Left color sensor on tape");
            }
        }
        basicMove.moveInches(-0.3, -4);
        //xrail.fullDumpNoWait();
    }

}

