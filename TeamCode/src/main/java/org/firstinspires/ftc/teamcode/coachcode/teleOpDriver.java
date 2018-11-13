package org.firstinspires.ftc.teamcode.coachcode;

        import android.graphics.Color;

        import com.qualcomm.hardware.bosch.BNO055IMU;
        import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
        import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.ColorSensor;
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
        import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
        import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
        import org.firstinspires.ftc.teamcode.teamLibs.revHubIMUGyro;

        import java.util.Locale;


@TeleOp(name="Coach Test", group="Linear Opmode")
public class teleOpDriver extends LinearOpMode{

    private DcMotor leftRearMotor;
    private DcMotor rightRearMotor;
    private DcMotor laMotor;
    //private coachGyro gyro;
    private revHubIMUGyro gyro;
    private autoDriver driver;

    private DigitalChannel digitalTouch;
    private ColorSensor leftColor;
    private ColorSensor rightColor;
    //private DistanceSensor sensorDistance;
    //private DistanceSensor sensorRange2m;


    //private goBuildAServo2000 servoTest = new goBuildAServo2000();

    private Servo reachServoFTC;
    private Servo grabberServoFTC;
    //private goBuildAServo2000 reachServo;
    //private goBuildAServo2000 grabberServo;
    //private coachVu vu;

    @Override
    public void runOpMode() {

        // set up our IMU
        gyro = new revHubIMUGyro(hardwareMap.get(BNO055IMU.class, "imu"), telemetry);

        // Get the objects for the various pieces of hardware
        //imu = hardwareMap.get(Gyroscope.class, "imu");
        leftRearMotor = hardwareMap.get(DcMotor.class, "leftRear");
        rightRearMotor = hardwareMap.get(DcMotor.class, "rightRear");
        laMotor = hardwareMap.get(DcMotor.class, "la");
        //digitalTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        leftColor = hardwareMap.get(ColorSensor.class, "leftColor");
        rightColor = hardwareMap.get(ColorSensor.class, "rightColor");
        //sensorDistance = hardwareMap.get(DistanceSensor.class, "sensorColorRange");
        grabberServoFTC = hardwareMap.get(Servo.class, "grabberServo");
        reachServoFTC = hardwareMap.get(Servo.class, "reachServo");
        //vu = new coachVu(telemetry, hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));

        // you can use this as a regular DistanceSensor.
        //sensorRange2m = hardwareMap.get(DistanceSensor.class, "sensorRange2m");
        // you can also cast this to a Rev2mDistanceSensor if you want to use added
        // methods associated with the Rev2mDistanceSensor class.
        //Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor)sensorRange2m;

        grabberServoFTC.setPosition(0.004 * 0);
        reachServoFTC.setPosition(0.004 * 0);


        // set digital channel to input mode.
        //digitalTouch.setMode(DigitalChannel.Mode.INPUT);

        // reverse the drive on the left rear motor for normal control
        leftRearMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRearMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRearMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driver = new autoDriver (hardwareMap, this, telemetry, leftRearMotor, rightRearMotor, gyro, leftColor, rightColor);
        //vu.init();

        // Output status to the console
        telemetry.addData("Status", "Initialized");
        //telemetry.addData("IMU status", imu.getSystemStatus());
        //telemetry.addData("IMU Calib", imu.getCalibrationStatus());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        //vu.activate(); // start vuforia tracking

        // run until the end of the match (driver presses STOP)
        double tgtPowerLR = 0;
        double tgtPowerRR = 0;
        gyro.resetHeading();
        double reachPos = 0;
        while (opModeIsActive()) {


            tgtPowerLR = -this.gamepad1.left_stick_y;
            tgtPowerRR = -this.gamepad1.right_stick_y;
            leftRearMotor.setPower(tgtPowerLR);
            rightRearMotor.setPower(tgtPowerRR);

            if(gamepad1.x) {
                driver.spin(.2, -90);
            } else if ( gamepad1.b) {
                driver.spin(.2, 90);
            } else if ( gamepad1.y) {
                //driver.spin(.15, -90);
                driver.moveInches(.2,12);
            } else if ( gamepad1.a) {
                //driver.spin(.15, 90);
                driver.moveInches(-0.2,-12);
            } else if (gamepad1.dpad_left) {
                grabberServoFTC.setPosition(0.004 * 0);
            } else if (gamepad1.dpad_up) {
                grabberServoFTC.setPosition(0.004 * 35);
            } else if (gamepad1.dpad_right) {
                grabberServoFTC.setPosition(0.004 * 60);
            } else if (gamepad1.dpad_down) {
                driver.squareOnBlueLine(-.15);
            } else if (gamepad1.left_bumper) {
                laMotor.setPower(1);
                //reachPos = reachPos+5;
                //reachServoFTC.setPosition(0.004 * 150);
                sleep(1000);
            } else if (gamepad1.right_bumper) {
                //reachPos = reachPos-5;
                //reachServoFTC.setPosition(0.004 * 0);
                //sleep(1000);
                laMotor.setPower(-1);
            } else {
                laMotor.setPower(0);
            }
            telemetry.addData("reachPos", reachPos);




            // generic DistanceSensor methods.
            //telemetry.addData("range", String.format("%.01f mm", sensorRange2m.getDistance(DistanceUnit.MM)));
            //telemetry.addData("range", String.format("%.01f cm", sensorRange2m.getDistance(DistanceUnit.CM)));
            //telemetry.addData("range", String.format("%.01f m", sensorRange2m.getDistance(DistanceUnit.METER)));
            //telemetry.addData("range", String.format("%.01f in", sensorRange2m.getDistance(DistanceUnit.INCH)));

            // Rev2mDistanceSensor specific methods.
            //telemetry.addData("ID", String.format("%x", sensorTimeOfFlight.getModelID()));
            //telemetry.addData("did time out", Boolean.toString(sensorTimeOfFlight.didTimeoutOccur()));

            //test out vuforia vumark navigation
            // vu.getLocation();

            //telemetry.addData("Servo Position", servoTest.getPosition());
            //telemetry.addData("Left Motor Target Power", tgtPowerLR);
            //telemetry.addData("Right Motor Target Power", tgtPowerRR);
            //telemetry.addData("Left Motor Power", leftRearMotor.getPower());
            //telemetry.addData("Right Motor Power", rightRearMotor.getPower());
            telemetry.addData("heading", gyro.getHeading());
            telemetry.addData("Drive Encoders",  "L:%7d R:%7d",
                    leftRearMotor.getCurrentPosition(),
                    rightRearMotor.getCurrentPosition());
            telemetry.addData("Status", "Running");
            telemetry.update();
        }

    }

}