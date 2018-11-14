package org.firstinspires.ftc.teamcode.teamLibs;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class basicMovement {

    //This code will help make maneuvering easier on the autonomous period and possibly when you want
    //the robot to do a spin, turn, or just simply move forewords.
    DcMotor motorLeft;
    DcMotor motorRight;
    revHubIMUGyro gyro;
    //The counts per inch is the number of ticks per revolution divided by the circumference of the wheel
    private double     COUNTS_PER_INCH = 89.1294;
    //stashes the values
    public basicMovement (DcMotor leftMotor, DcMotor rightMotor, BNO055IMU theimu, Telemetry theTelemetry) {
        motorLeft = leftMotor;
        motorRight = rightMotor;
        gyro = new revHubIMUGyro ( theimu, theTelemetry );
    }

    //this will make the robot move a chosen number of inches at a chosen speed
    public void moveInches (double speed, double inches) {
        //resets the motors
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //sets the number of desired inches on both motors
        motorLeft.setTargetPosition((int) inches);
        motorRight.setTargetPosition( (int) COUNTS_PER_INCH * (int) inches);

        //sets the desired speed on both motors
        motorLeft.setPower(speed);
        motorRight.setPower(speed);

        //runs to the set number of inches at the desired speed
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //lets the two moving motors finish the task
        while(motorLeft.isBusy() && motorRight.isBusy())
        {

        }

        //turns off both motors
        motorLeft.setPower(0);
        motorRight.setPower(0);

        //sets it back to normal
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //this block will let the user do a right turn with the desired # of degrees and speed
    public void rightTurn (double speed, double degrees) {
        //here the gyro sensor is reset
        gyro.resetHeading();

        //again, the powers and speeds are set
        motorLeft.setPower(speed);
        motorRight.setPower(0);
        while (gyro.getHeading()<degrees) {

        }

        //
        motorLeft.setPower(0);
        motorRight.setPower(0);

        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void leftTurn (double speed, double degrees) {
        gyro.resetHeading();
        motorLeft.setPower(0);
        motorRight.setPower(speed);
        while (gyro.getHeading()<-degrees) {

        }
        motorLeft.setPower(0);
        motorRight.setPower(0);

        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void leftSpin (double speed, double degrees) {
        gyro.resetHeading();
        motorLeft.setPower(speed);
        motorRight.setPower(speed);
        while (gyro.getHeading()<degrees) {

    }
        motorLeft.setPower(0);
        motorRight.setPower(0);

        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

}
    public void rightSpin (double speed, double degrees) {
        gyro.resetHeading();
        motorLeft.setPower(speed);
        motorRight.setPower(speed);
        while (gyro.getHeading()<-degrees) {

        }
        motorLeft.setPower(0);
        motorRight.setPower(0);

        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

}
