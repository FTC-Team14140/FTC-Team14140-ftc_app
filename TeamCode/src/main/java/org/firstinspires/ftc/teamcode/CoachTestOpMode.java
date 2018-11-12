package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.teamLibs.goBuildAServo2000;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;
//

//Trey was here.....                                                        ....or was he.......
@TeleOp(name="Test", group="Linear Opmode")
public class CoachTestOpMode extends LinearOpMode {
    private Gyroscope imu;
    private DcMotor motorTest;
    private DigitalChannel digitalTouch;
    private DistanceSensor sensorColorRange;
    //private goBuildAServo2000 servoTest;

    @Override
    public void runOpMode() {
        // Get the objects for the various pieces of hardware
        imu = hardwareMap.get(Gyroscope.class, "imu");
        motorTest = hardwareMap.get(DcMotor.class, "motorTest");
        digitalTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        sensorColorRange = hardwareMap.get(DistanceSensor.class, "sensorColorRange");
        //servoTest.initialize(hardwareMap.get(Servo.class, "servoTest"), telemetry);
        // set digital channel to input mode.
        digitalTouch.setMode(DigitalChannel.Mode.INPUT);
        // Output status to the console
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //servoTest = new goBuildAServo2000()
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        double tgtPower = 0;
        while (opModeIsActive()) {
            tgtPower = -this.gamepad1.right_stick_y;
            motorTest.setPower(tgtPower);
            // check to see if we need to move the servo.
            if(gamepad1.y) {
                // move to 0 degrees.
                //servoTest.goTo(0);
            } else if (gamepad1.x || gamepad1.b) {
                // move to 90 degrees.
                //servoTest.goTo(90);
            } else if (gamepad1.a) {
                // move to 180 degrees.
                //servoTest.goTo(180);
            }
            // is button pressed?
            if (digitalTouch.getState() == false) {
                // button is pressed.
                telemetry.addData("Button", "PRESSED"); } else {
                // button is not pressed.
                telemetry.addData("Button", "NOT PRESSED");
            }


            //telemetry.addData("Servo Position", servoTest.getPosition());
            telemetry.addData("Motor Target Power", tgtPower);
            telemetry.addData("Motor Power", motorTest.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();
        }

    }

}
