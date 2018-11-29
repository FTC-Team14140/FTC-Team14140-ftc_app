package org.firstinspires.ftc.teamcode.teamLibs;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//This code will help make maneuvering easier on the autonomous period and possibly when you want
//the robot to do a spin, turn, or just simply move forewords.
public class basicMovement {

    DcMotor motorLeft;
    DcMotor motorRight;
    revHubIMUGyro gyro;
    Telemetry telemetry;

    //The counts per inch is the number of ticks per revolution divided by the circumference of the wheel
    private double     COUNTS_PER_INCH = 89.7158;

    //stashes the values for later
    public basicMovement (DcMotor leftMotor, DcMotor rightMotor, BNO055IMU theimu, Telemetry theTelemetry) {
        motorLeft = leftMotor;
        motorRight = rightMotor;
        motorLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        gyro = new revHubIMUGyro ( theimu, theTelemetry );
        telemetry = theTelemetry;
    }

    // this will make the robot move a chosen number of inches at a chosen speed
    // TODO: need error handling if they pass in bad parameters
    public void moveInches (double speed, double inches) {
        //resets the motors
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //sets the number of desired inches on both motors
        motorLeft.setTargetPosition((int) (COUNTS_PER_INCH * inches));
        motorRight.setTargetPosition((int) (COUNTS_PER_INCH * inches) );

        //runs to the set number of inches at the desired speed
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //sets the desired speed on both motors
        motorLeft.setPower(speed);
        motorRight.setPower(speed);

        //lets the two moving motors finish the task
        while(motorLeft.isBusy() && motorRight.isBusy())
        {
            // TODO: Add some telemetry output here so we can see what's happening on the driver station phone
        }

        //turns off both motors
        motorLeft.setPower(0);
        motorRight.setPower(0);

        //sets it back to normal
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void motorsOn(double speed) {
        motorLeft.setPower(speed);
        motorRight.setPower(speed);
    }
    public void motorsOff() {
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public float getHeading() {
        return gyro.getHeading();
    }

/*  Turns don't work yet...need to lock the 0 power motor and test
    //this block will let the user do a right turn with the desired # of degrees and speed

    public void rightTurn (double speed, double degrees) {
        //here the gyro sensor is reset
        gyro.resetHeading();

        //again, the powers and speeds are set
        motorLeft.setPower(speed);
        motorRight.setPower(0);
        while (gyro.getHeading()<degrees) {
            // TODO: Add some telemetry output here so we can see what's happening on the driver station phone

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
            // TODO: Add some telemetry output here so we can see what's happening on the driver station phone

        }
        motorLeft.setPower(0);
        motorRight.setPower(0);

        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
*/

    // TODO: need comments on this method and error handling if they pass in bad parameters
    public void leftSpin (double speed, double degrees) {
        gyro.resetHeading();
        motorLeft.setPower(-speed);
        motorRight.setPower(speed);
        while (gyro.getHeading()>-degrees) {
            // TODO: Add some telemetry output here so we can see what's happening on the driver station phone

        }
        motorLeft.setPower(0);
        motorRight.setPower(0);

        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

}

    // TODO: need comments on this method and error handling if they pass in bad parameters
    public void rightSpin (double speed, double degrees) {
        gyro.resetHeading();
        motorLeft.setPower(speed);
        motorRight.setPower(-speed);
        while (gyro.getHeading()<degrees) {
            // TODO: Add some telemetry output here so we can see what's happening on the driver station phone

        }
        motorLeft.setPower(0);
        motorRight.setPower(0);

        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

}
