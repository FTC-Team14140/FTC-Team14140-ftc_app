package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.teamLibs.revHubIMUGyro;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name="Drive Test", group="Linear Opmode")
public class teamTeleop extends LinearOpMode {
    private revHubIMUGyro gyro;
    private DcMotor motorLeft;
    private DcMotor motorRight;
    Orientation anglesCurrent;
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

        gyro = new revHubIMUGyro(hardwareMap.get(BNO055IMU.class, "imu"), telemetry );


        // Wait for the game to start (drver presses PLAY)
         waitForStart();

        //Yuto: What are these two variables for?
        double tgtPowerRight = 0;
        double tgtPowerLeft = 0;

        gyro.resetHeading();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            telemetry.addData( "Gyro Heading",gyro.getHeading());

            //Yuto: What are these two variables for and where are we getting their values?
            double throttle = -this.gamepad1.left_stick_y;
            double steering = this.gamepad1.right_stick_x;

            //  Yuto: What is going on here?
            if (throttle > 0) {
                // formula to use (OK, but what does the formula do?)
                throttle = (1 - deadSpot) * throttle + deadSpot;
            }else if (throttle < 0){
                throttle = (1 - deadSpot) * throttle- deadSpot;
            }

            //What is this next block of code for?
            // If we are turning to the right then we...
            if (steering > 0){
                tgtPowerRight = throttle-steering*2*throttle;
                tgtPowerLeft = throttle;
            } else if (steering < 0){ // if we are turning to the left then we...
                tgtPowerRight = throttle;
                tgtPowerLeft =  throttle+steering*2*throttle;
            } else { // if we are not turning at all than we ...
                tgtPowerRight = throttle;
                tgtPowerLeft = throttle;
            }

            //Yuto: What is happening here?
            motorRight.setPower(tgtPowerRight);
            motorLeft.setPower(tgtPowerLeft);

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
            telemetry.addData("MotorLeft Target Power", tgtPowerLeft);
            telemetry.addData("MotorRight Target Power", tgtPowerRight);
            telemetry.addData("MotorLeft Power", motorLeft.getPower());
            telemetry.addData("MotorRight Power", motorRight.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();
        }

    }

}

