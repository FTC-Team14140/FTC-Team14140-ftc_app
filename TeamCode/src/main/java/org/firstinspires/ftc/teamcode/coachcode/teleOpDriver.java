package org.firstinspires.ftc.teamcode.coachcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name="Coach Test", group="Linear Opmode")
public class teleOpDriver extends LinearOpMode{

    private DcMotor leftRearMotor;
    private DcMotor rightRearMotor;
    private coachGyro gyro;
    private autoDriver driver;

    private DigitalChannel digitalTouch;
    private DistanceSensor sensorColorRange;
    //private goBuildAServo2000 servoTest = new goBuildAServo2000();

    @Override
    public void runOpMode() {

        // set up our IMU
        gyro = new coachGyro(hardwareMap, "imu");

        // Get the objects for the various pieces of hardware
        //imu = hardwareMap.get(Gyroscope.class, "imu");
        leftRearMotor = hardwareMap.get(DcMotor.class, "leftRear");
        rightRearMotor = hardwareMap.get(DcMotor.class, "rightRear");
        digitalTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        sensorColorRange = hardwareMap.get(DistanceSensor.class, "sensorColorRange");
        //servoTest.initialize(hardwareMap.get(Servo.class, "servoTest"), telemetry);

        // set digital channel to input mode.
        digitalTouch.setMode(DigitalChannel.Mode.INPUT);

        // reverse the drive on the left rear motor for normal control
        leftRearMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        driver = new autoDriver (hardwareMap, this, telemetry, leftRearMotor, rightRearMotor, gyro);

        // Output status to the console
        telemetry.addData("Status", "Initialized");
        //telemetry.addData("IMU status", imu.getSystemStatus());
        //telemetry.addData("IMU Calib", imu.getCalibrationStatus());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        double tgtPowerLR = 0;
        double tgtPowerRR = 0;
        gyro.resetHeading();
        while (opModeIsActive()) {
            tgtPowerLR = -this.gamepad1.left_stick_y;
            tgtPowerRR = -this.gamepad1.right_stick_y;
            leftRearMotor.setPower(tgtPowerLR);
            rightRearMotor.setPower(tgtPowerRR);

            if(gamepad1.x) {
                driver.turn(.2, -90);
           } else if ( gamepad1.b) {
                driver.turn(.2, 90);
            } else if ( gamepad1.y) {
                driver.spin(.15, -90);
            } else if ( gamepad1.a) {
                driver.spin(.15, 90);
            }
            //telemetry.addData("Servo Position", servoTest.getPosition());
            //telemetry.addData("Left Motor Target Power", tgtPowerLR);
            //telemetry.addData("Right Motor Target Power", tgtPowerRR);
            //telemetry.addData("Left Motor Power", leftRearMotor.getPower());
            //telemetry.addData("Right Motor Power", rightRearMotor.getPower());
            telemetry.addData("heading", gyro.getHeading());
            telemetry.addData("Status", "Running");
            telemetry.update();
        }

    }

}