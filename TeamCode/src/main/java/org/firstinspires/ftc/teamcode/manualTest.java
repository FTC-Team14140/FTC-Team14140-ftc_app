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

@TeleOp(name="Manual Test", group="Linear Opmode")
public class manualTest extends LinearOpMode {
    //private revHubIMUGyro gyro;
    private grabberArm grabber;
    private xRail xrail;
    private linearActuator La;

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

        // Wait for the game to start (drver presses PLAY)
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
            if(gamepad2.right_stick_button) {
                xrail.testExtend();
            }

            if(gamepad2.dpad_down) {
                La.retractMoving();
            } else if(gamepad2.dpad_up) {
                La.extendActuatorWhileMoving();
            }
            else if(gamepad2.dpad_right) {
                La.lift();
            }
            else if(gamepad2.dpad_left) {
                La.lowerRobot();
            }
            else if(gamepad2.left_bumper) {
                La.retractFully();
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
           // telemetry.addData("MotorLeft Power", motorLeft.getPower());
           // telemetry.addData("MotorRight Power", motorRight.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();
        }

    }

}






