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
    private double     COUNTS_PER_INCH = 59.4178;  // 89.7158 is the orriginal number
    private final double MIN_SPEED = 0.11;
    private final double MAX_ACEL_PER_INCH = 0.15;
    private final double MAX_DECEL_PER_INCH = 0.15;

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
        if (teamUtil.theOpMode.opModeIsActive()) {
            //resets the motors
            motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            //sets the number of desired inches on both motors
            motorLeft.setTargetPosition((int) (COUNTS_PER_INCH * inches));
            motorRight.setTargetPosition((int) (COUNTS_PER_INCH * inches));

            //runs to the set number of inches at the desired speed
            motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //sets the desired speed on both motors
            motorLeft.setPower(speed);
            motorRight.setPower(speed);

            //lets the two moving motors finish the task
            while (teamUtil.theOpMode.opModeIsActive() && motorLeft.isBusy() && motorRight.isBusy()) {
                // TODO: Add some telemetry output here so we can see what's happening on the driver station phone
            }

            //turns off both motors
            motorLeft.setPower(0);
            motorRight.setPower(0);

            //sets it back to normal
            motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

    }

    public void motorsOn(double speed) {
        if (teamUtil.theOpMode.opModeIsActive()) {
            motorLeft.setPower(speed);
            motorRight.setPower(speed);
        }
    }
    public void motorsOff() {
        if (teamUtil.theOpMode.opModeIsActive()) {
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }
    }

    public float getHeading() {
        return gyro.getHeading();
    }


    //this block will let the user do a right turn with the desired # of degrees and speed

    public void rightTurn (double speed, double degrees) {
        teamUtil.log("In right Turn");
        if (teamUtil.theOpMode.opModeIsActive()) {
            //here the gyro sensor is reset
            gyro.resetHeading();

            //again, the powers and speeds are set
            motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            teamUtil.log("Starting right Turn");
            motorLeft.setPower(speed);
            motorRight.setPower(0);
            while (gyro.getHeading() < degrees && teamUtil.theOpMode.opModeIsActive()) {

            }
            teamUtil.log("End of right Turn");


            motorLeft.setPower(0);
            motorRight.setPower(0);
            teamUtil.log("Braking");
            motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public void leftTurn (double speed, double degrees) {
        teamUtil.log("In left Turn");
        if (teamUtil.theOpMode.opModeIsActive()) {
            motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            teamUtil.log("Starting left Turn");
            gyro.resetHeading();
            motorLeft.setPower(0);
            motorRight.setPower(speed);
            while ((gyro.getHeading() > -degrees) && teamUtil.theOpMode.opModeIsActive()) {
                // TODO: Add some telemetry output here so we can see what's happening on the driver station phone

            }
            teamUtil.log("End of left Turn");

            motorLeft.setPower(0);
            motorRight.setPower(0);
            teamUtil.log("Braking");


            motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }


    // TODO: need comments on this method and error handling if they pass in bad parameters
    public void leftSpin (double speed, double degrees) {
        if (teamUtil.theOpMode.opModeIsActive()) {
            gyro.resetHeading();
            motorLeft.setPower(-speed);
            motorRight.setPower(speed);
            while ((gyro.getHeading() > -degrees) && teamUtil.theOpMode.opModeIsActive()) {
                // TODO: Add some telemetry output here so we can see what's happening on the driver station phone

            }
            motorLeft.setPower(0);
            motorRight.setPower(0);

            motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }


    public void decelLeftSpin ( double speed, double degrees) {
        if (teamUtil.theOpMode.opModeIsActive()) {
            gyro.resetHeading();
            motorLeft.setPower(-speed);
            motorRight.setPower(speed);
            while ((gyro.getHeading() > -degrees+15) && teamUtil.theOpMode.opModeIsActive()) {
                // TODO: Add some telemetry output here so we can see what's happening on the driver station phone

            }motorLeft.setPower(-0.3);
            motorRight.setPower(0.3);

            while ((gyro.getHeading() > -degrees) && teamUtil.theOpMode.opModeIsActive()) {
                // TODO: Add some telemetry output here so we can see what's happening on the driver station phone

            }
            motorLeft.setPower(0);
            motorRight.setPower(0);

            motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }


    }

    public void decelRightSpin ( double speed, double degrees) {
        if (teamUtil.theOpMode.opModeIsActive()) {
            gyro.resetHeading();
            motorLeft.setPower(speed);
            motorRight.setPower(-speed);
            while ((gyro.getHeading() < degrees-15) && teamUtil.theOpMode.opModeIsActive()) {
                // TODO: Add some telemetry output here so we can see what's happening on the driver station phone

            }
            motorLeft.setPower(0.3);
            motorRight.setPower(-0.3);

            while ((gyro.getHeading() < degrees) && teamUtil.theOpMode.opModeIsActive()) {
                // TODO: Add some telemetry output here so we can see what's happening on the driver station phone

            }
            motorLeft.setPower(0);
            motorRight.setPower(0);

            motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }


    }




    // TODO: need comments on this method and error handling if they pass in bad parameters
    public void rightSpin (double speed, double degrees) {
        if (teamUtil.theOpMode.opModeIsActive()) {
            gyro.resetHeading();
            motorLeft.setPower(speed);
            motorRight.setPower(-speed);
            while (gyro.getHeading() < degrees && teamUtil.theOpMode.opModeIsActive()) {
                // TODO: Add some telemetry output here so we can see what's happening on the driver station phone

            }
            motorLeft.setPower(0);
            motorRight.setPower(0);

            motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        }
    }
    public void forwardMovement(double speed, double distance) {
        smoothMovement(speed, distance);
    }


    public void backwardMovement (double speed, double distance){
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        final double MIN_SPEED = -0.15;
        int startPosition = 0;
        double maxSpeed = -speed;
        double inputDistance =-distance;
        int totalEncoders = (int) (inputDistance * COUNTS_PER_INCH);
        int endPosition = (int) (startPosition + totalEncoders);
        double totalAcceleration = maxSpeed - MIN_SPEED;
        teamUtil.log("smoothMovement...Inital Calcs ");
        teamUtil.log("startPosition... "+ startPosition);
        teamUtil.log("MIN_SPEED... "+ MIN_SPEED);
        teamUtil.log("maxSpeed... "+ maxSpeed);
        teamUtil.log("totalEncoders... "+ totalEncoders);
        teamUtil.log("endPosition... "+ endPosition);
        teamUtil.log("totalAcceleration... "+ totalAcceleration);
        // TODO: There is still a bug here as this assumes max acel and max decel rates are the same...
        if ((totalEncoders/2) > ((totalAcceleration / MAX_ACEL_PER_INCH) * COUNTS_PER_INCH)) {
            maxSpeed = MIN_SPEED + (totalEncoders/2) * (MAX_ACEL_PER_INCH / COUNTS_PER_INCH);
            totalAcceleration = maxSpeed - MIN_SPEED;
            teamUtil.log("Adjusting Max Speed (not enough distance for accel and decel)");
            teamUtil.log("maxSpeed... "+ maxSpeed);
            teamUtil.log("totalAcceleration... "+ totalAcceleration);
        }
        double acellerationInchs = totalAcceleration/MAX_ACEL_PER_INCH;
        int acellerationEncoders = (int) (acellerationInchs * COUNTS_PER_INCH);
        double totalDecell = maxSpeed - MIN_SPEED;
        double decellerationInch = totalDecell/MAX_DECEL_PER_INCH;
        int decelerationEncoders = (int) (decellerationInch * COUNTS_PER_INCH);
        int cruisePosition = (int) (startPosition + acellerationEncoders);
        int cruiseEncoders = (int) (totalEncoders - acellerationEncoders - decelerationEncoders);
        int decelerationSpot = (int) (startPosition + acellerationEncoders + cruiseEncoders);
        double motorSpeed = speed;
        telemetry.addData("Check", decelerationSpot);
        telemetry.addData("speed", maxSpeed);
        telemetry.update();
        //Need Loop
        //telemetry.addData("description", data);
        //telemetry.update();
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        teamUtil.log("cruisePosition: "+ cruisePosition);
        teamUtil.log("decelerationSpot: "+ decelerationSpot);
        //telemetry.addData("startPosition: ", startPosition);
        //telemetry.addData("cruisePosition: ", cruisePosition);
        //telemetry.addData("decelerationSpot: ", decelerationSpot);
        //telemetry.addData("endSpot: ", endPosition);
        //telemetry.update();

        double lastMotorSpeed = 2;
        while (motorLeft.getCurrentPosition() > cruisePosition) {
            motorSpeed = totalAcceleration/cruisePosition * motorLeft.getCurrentPosition() + MIN_SPEED;
            motorLeft.setPower(motorSpeed);
            motorRight.setPower(motorSpeed);
            if (motorSpeed != lastMotorSpeed) {
                teamUtil.log("Accelerating: " + motorSpeed);
                lastMotorSpeed = motorSpeed;
            }
        }
        teamUtil.log("at cruising speed");
        while (motorLeft.getCurrentPosition() > decelerationSpot) {

        }
        teamUtil.log("decelerating");
        while (motorLeft.getCurrentPosition() > endPosition){
            motorSpeed = (MIN_SPEED - maxSpeed) / (endPosition - decelerationSpot) * (motorLeft.getCurrentPosition()- decelerationSpot) + maxSpeed; // BUG: () were missing
            motorLeft.setPower(motorSpeed); // BUG: This line was missing
            motorRight.setPower(motorSpeed);
            if (motorSpeed != lastMotorSpeed) {
                teamUtil.log("Decelerating: " + motorSpeed);
                lastMotorSpeed = motorSpeed;
            }
        }

        motorLeft.setPower(0);
        motorRight.setPower(0);
        teamUtil.log("turning motors off");
    }

    private void smoothMovement(double speed, double distance) {
        final double MIN_SPEED = 0.15;
        int startPosition = 0;
        double maxSpeed = speed;
        int totalEncoders = (int) (distance * COUNTS_PER_INCH);
        int endPosition = (int) (startPosition + totalEncoders);
        double totalAcceleration = maxSpeed - MIN_SPEED;
        teamUtil.log("smoothMovement...Inital Calcs ");
        teamUtil.log("startPosition... "+ startPosition);
        teamUtil.log("MIN_SPEED... "+ MIN_SPEED);
        teamUtil.log("maxSpeed... "+ maxSpeed);
        teamUtil.log("totalEncoders... "+ totalEncoders);
        teamUtil.log("endPosition... "+ endPosition);
        teamUtil.log("totalAcceleration... "+ totalAcceleration);
        // TODO: There is still a bug here as this assumes max acel and max decel rates are the same...
        if ((totalEncoders/2) < ((totalAcceleration / MAX_ACEL_PER_INCH) * COUNTS_PER_INCH)) {
            maxSpeed = MIN_SPEED + (totalEncoders/2) * (MAX_ACEL_PER_INCH / COUNTS_PER_INCH);
            totalAcceleration = maxSpeed - MIN_SPEED;
            teamUtil.log("Adjusting Max Speed (not enough distance for accel and decel)");
            teamUtil.log("maxSpeed... "+ maxSpeed);
            teamUtil.log("totalAcceleration... "+ totalAcceleration);
        }
        double acellerationInchs = totalAcceleration/MAX_ACEL_PER_INCH;
        int acellerationEncoders = (int) (acellerationInchs * COUNTS_PER_INCH);
        double totalDecell = maxSpeed - MIN_SPEED;
        double decellerationInch = totalDecell/MAX_DECEL_PER_INCH;
        int decelerationEncoders = (int) (decellerationInch * COUNTS_PER_INCH);
        int cruisePosition = (int) (startPosition + acellerationEncoders);
        int cruiseEncoders = (int) (totalEncoders - acellerationEncoders - decelerationEncoders);
        int decelerationSpot = (int) (startPosition + acellerationEncoders + cruiseEncoders);
        double motorSpeed = speed;

        //Need Loop
        //telemetry.addData("description", data);
        //telemetry.update();
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        teamUtil.log("cruisePosition: "+ cruisePosition);
        teamUtil.log("decelerationSpot: "+ decelerationSpot);
        //telemetry.addData("startPosition: ", startPosition);
        //telemetry.addData("cruisePosition: ", cruisePosition);
        //telemetry.addData("decelerationSpot: ", decelerationSpot);
        //telemetry.addData("endSpot: ", endPosition);
        //telemetry.update();

        double lastMotorSpeed = 2;
        while (motorLeft.getCurrentPosition() < cruisePosition) {
            motorSpeed = totalAcceleration/cruisePosition * motorLeft.getCurrentPosition() + MIN_SPEED;
            motorLeft.setPower(motorSpeed);
            motorRight.setPower(motorSpeed);
            if (motorSpeed != lastMotorSpeed) {
                teamUtil.log("Accelerating: " + motorSpeed);
                lastMotorSpeed = motorSpeed;
            }
        }
        teamUtil.log("at cruising speed");
        while (motorLeft.getCurrentPosition() < decelerationSpot) {

        }
        teamUtil.log("decelerating");
        while (motorLeft.getCurrentPosition() < endPosition){
            motorSpeed = (MIN_SPEED - maxSpeed) / (endPosition - decelerationSpot) * (motorLeft.getCurrentPosition()- decelerationSpot) + maxSpeed; // BUG: () were missing
            motorLeft.setPower(motorSpeed); // BUG: This line was missing
            motorRight.setPower(motorSpeed);
            if (motorSpeed != lastMotorSpeed) {
                teamUtil.log("Decelerating: " + motorSpeed);
                lastMotorSpeed = motorSpeed;
            }
        }

        motorLeft.setPower(0);
        motorRight.setPower(0);
        teamUtil.log("turning motors off");
        }
    }



