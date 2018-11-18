package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.teamLibs.basicMovement;
import org.firstinspires.ftc.teamcode.teamLibs.revHubIMUGyro;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.teamLibs.grabberArm;
import org.firstinspires.ftc.teamcode.teamLibs.xRail;
import org.firstinspires.ftc.teamcode.teamLibs.linearActuator;

@TeleOp(name="Drive Test", group="Linear Opmode")
public class teamTeleop extends LinearOpMode {
    //private revHubIMUGyro gyro;
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private grabberArm grabber;
    private xRail xrail;
    private linearActuator La;
    private basicMovement basicMove;

    // 0.15 is the threshold that the motor starts to accelerate
    private static final double deadSpot = 0.15;

    @Override
    public void runOpMode() {
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
        xrail.init();
        grabber.initialize();

        // Wait for the game to start (drver presses PLAY)
         waitForStart();

        //These are the variables for the motors power at the start of the program
        double tgtPowerRight = 0;
        double tgtPowerLeft = 0;

        //gyro.resetHeading();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //telemetry.addData( "Gyro Heading",gyro.getHeading());

            //We use these variables to figure out how to control the X or turning for the robot right stick and the Y or movement on the left stick
            double throttle = -this.gamepad1.left_stick_y;
            double steering = this.gamepad1.right_stick_x;

            //  It puts in the throttle variable to figure out how far you need to push the joystick for the corresponding motor speed
            if (throttle > 0) {
                // formula to use figures out what the area of effect for the joysticks is (OK, but what does the formula do?)
                throttle = (1 - deadSpot) * throttle + deadSpot;
            }else if (throttle < 0){
                throttle = (1 - deadSpot) * throttle- deadSpot;
            }

            //What is this next block of code for?
            // If we are turning to the right then we give more power to the left motor
            if (steering > 0){
                tgtPowerRight = throttle-steering*2*throttle;
                tgtPowerLeft = throttle;
            } else if (steering < 0){ // if we are turning to the left then we give more power to the right motor
                tgtPowerRight = throttle;
                tgtPowerLeft =  throttle+steering*2*throttle;
            } else { // if we are not turning at all than it will run this program and just not move
                tgtPowerRight = throttle;
                tgtPowerLeft = throttle;
            }

            // It is setting the robots motors to the corresponding power
            motorRight.setPower(tgtPowerRight);
            motorLeft.setPower(tgtPowerLeft);

            if(gamepad2.y){
                grabber.wideOpen();
            } else if(gamepad2.x) {
                grabber.skinnyOpen();
            } else if(gamepad2.a) {
                grabber.grab();
            } else if(gamepad2.dpad_left) {
                grabber.deposit();
            } else if(gamepad2.dpad_up) {
                grabber.holdUp();
            } else if(gamepad2.dpad_right) {
                grabber.grabberDown();
            } else if (gamepad2.right_trigger > 0) {
                grabber.triggerControl(gamepad2.right_trigger);
            }

            if(gamepad2.right_stick_button) {
                xrail.testExtend();
            } else if(gamepad2.left_stick_button) {
                //xrail.retract();
            } else {
                //xrail.stop();
            }

            if(gamepad2.left_bumper) {
               // La.drop();
            } else if(gamepad2.right_bumper) {
               // La.lift();
            } else {
               // La.stop();
            }



            if(gamepad1.dpad_up){
                basicMove.moveInches(0.25, 12);
            }else if(gamepad1.dpad_left){
                basicMove.leftSpin(0.25, 90);
            }else if(gamepad1.dpad_right){
                basicMove.rightSpin(0.25, 90);
            }else if(gamepad1.dpad_down){
                basicMove.moveInches(-0.25, -12);
            }
/*
            if(gamepad1.x){
                //rotate 45 to left
            } else if (gamepad1.b){
                //rotate 45 to the right
            } else if (gamepad1.a){
                //rotate 180
            } else if (gamepad1.y) {
                //TBD
            } else if (gamepad1.right_stick_x)
*/
            //this.gamepad1.x
            //this.gamepad1.y
            //this.gamepad1.a
            //this.gamepad1.b
            //this.gamepad1.right_bumper
            //this.gamepad1.left_bumper
            //this.gamepad1.right_trigger
            //this.gamepad1.left_trigger
            //this.gamepad1.dpad_down
            //this.gamepad1.dpad_up
            //this.gamepad1.dpad_right
            //this.gamepad1.dpad_left
            //this.gamepad1.right_stick_button
            //this.gamepad1.right_stick_x
            //this.gamepad1.right_stick_y
            //this.gamepad1.left_stick_button
            //this.gamepad1.left_stick_x
            //this.gamepad1.left_stick_y

            //telemetry.addData("Servo Position", servoTest.getPosition());
            //telemetry.addData("MotorLeft Target Power", tgtPowerLeft);
            //telemetry.addData("MotorRight Target Power", tgtPowerRight);
            telemetry.addData("MotorLeft Power", motorLeft.getPower());
            telemetry.addData("MotorRight Power", motorRight.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();
        }

    }

}

