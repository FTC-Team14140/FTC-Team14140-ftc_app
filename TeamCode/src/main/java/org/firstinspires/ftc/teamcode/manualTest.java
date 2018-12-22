package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.teamLibs.basicMovement;
import org.firstinspires.ftc.teamcode.teamLibs.revHubIMUGyro;
import org.firstinspires.ftc.teamcode.teamLibs.distanceSensors;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.teamLibs.grabberArm;
import org.firstinspires.ftc.teamcode.teamLibs.xRail;
import org.firstinspires.ftc.teamcode.teamLibs.linearActuator;
import org.firstinspires.ftc.teamcode.teamLibs.teamColorSensor;

@TeleOp(name="Manual Test", group="Linear Opmode")
public class manualTest extends LinearOpMode {
    //private revHubIMUGyro gyro;
    private grabberArm grabber;
    private xRail xrail;
    private linearActuator La;
    private distanceSensors leftDisSensor;
    private distanceSensors rightDisSensor;
    private teamColorSensor leftColSensor;
    private teamColorSensor rightColSensor;
    private basicMovement basicMove;


    @Override
    public void runOpMode() {
        // Get the objects for the various pieces of hardware
        //imu = hardwareMap.get(Gyroscope.class, "imu");
        //when we say go forwards, we really mean backwards


        //grabber = new grabberArm (telemetry, hardwareMap, "grabberServo", "liftServo");
        xrail = new xRail(telemetry, hardwareMap.get(DcMotor.class, "xRailMotor"));
        //gyro = new revHubIMUGyro(hardwareMap.get(BNO055IMU.class, "imu"), telemetry );
        La = new linearActuator(telemetry, hardwareMap.get(DcMotor.class, "LAMotor"));
        xrail.init();
        //grabber.initialize();
        leftDisSensor = new distanceSensors(telemetry, hardwareMap.get(Rev2mDistanceSensor.class, "leftFront2M"));
        rightDisSensor = new distanceSensors(telemetry, hardwareMap.get(Rev2mDistanceSensor.class, "rightFront2M"));
        leftColSensor = new teamColorSensor(telemetry, hardwareMap.get(ColorSensor.class, "leftRearColor"));
        rightColSensor = new teamColorSensor(telemetry, hardwareMap.get(ColorSensor.class, "rightRearColor"));
        basicMove = new basicMovement(hardwareMap.get(DcMotor.class, "motorLeft"), hardwareMap.get(DcMotor.class, "motorRight"), hardwareMap.get(BNO055IMU.class,"imu"), telemetry);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        //gyro.resetHeading();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //telemetry.addData( "Gyro Heading",gyro.getHeading());
            /*
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
*/
            if(gamepad1.right_stick_button) {
                xrail.loadLander();
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
            } else if(gamepad1.dpad_down) {
                La.retractFully();
            } else {
                La.stopMotor();
            }



            if(gamepad2.x){
                //turn 90 to left
                //teamutil.log
                basicMove.leftTurn(.4, 90);
            } else if (gamepad2.y) {
                basicMove.rightTurn(.4, 90);
                //turn 90 to the right
            }

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
           // telemetry.addData("MotorLeft Power", motorLeft.getPower());
           // telemetry.addData("MotorRight Power", motorRight.getPower());
            telemetry.addData("Status", "Running");
            telemetry.addData("distanceLeft", leftDisSensor.getDistance()  );
            telemetry.addData("distanceRight", rightDisSensor.getDistance()  );
            telemetry.addData("colorLeftRed", leftColSensor.redValue() );
            telemetry.addData("colorRightRed", rightColSensor.redValue());
            telemetry.addData("colorLeftBlue", leftColSensor.blueValue() );
            telemetry.addData("colorRightblue", rightColSensor.blueValue());
            telemetry.update();

        }

    }

}






