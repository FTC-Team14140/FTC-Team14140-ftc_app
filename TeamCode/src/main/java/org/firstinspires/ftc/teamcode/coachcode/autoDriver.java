package org.firstinspires.ftc.teamcode.coachcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class autoDriver {

    private HardwareMap hardwareMap;
    private LinearOpMode opMode;
    private Telemetry telemetry;
    private coachGyro gyro;
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    autoDriver(HardwareMap map, LinearOpMode mode, Telemetry tel, DcMotor left, DcMotor right, coachGyro g){
        hardwareMap = map;
        opMode = mode;
        telemetry = tel;
        leftMotor = left;
        rightMotor = right;
        gyro = g;
    }

    // move a specified number of inches
    void moveInches(double speed, double inches){

         final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // NeverRest 40 at 1:1
         final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
         final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
         final double     COUNTS_PER_INCH         =
                (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opMode.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = leftMotor.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
            newRightTarget = rightMotor.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
            leftMotor.setTargetPosition(newLeftTarget);
            rightMotor.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // start motion.
            leftMotor.setPower(speed);
            rightMotor.setPower(speed);

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opMode.opModeIsActive() &&
                    (leftMotor.isBusy() && rightMotor.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        leftMotor.getCurrentPosition(),
                        rightMotor.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            leftMotor.setPower(0);
            rightMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

    // turn # of degrees at specified speed.  negative degrees is left
    void turn(double speed, float degrees) {
        float currentAngle = gyro.resetHeading();
        float goalAngle = currentAngle + degrees;
        // turn until we have made it
        if (degrees < 0) { // turning left
            leftMotor.setPower(0);
            rightMotor.setPower(speed);
            while ((currentAngle > goalAngle) && (opMode.opModeIsActive())) {
                telemetry.addData("turning left", currentAngle);
                currentAngle = gyro.getHeading();
                telemetry.update();
            }
        } else { // turning right
            leftMotor.setPower(speed);
            rightMotor.setPower(0);
            while ((currentAngle < goalAngle) && (opMode.opModeIsActive())) {
                telemetry.addData("turning right", currentAngle);
                currentAngle = gyro.getHeading();
                telemetry.update();
            }
        }
        rightMotor.setPower(0);
        leftMotor.setPower(0);
    }

    // turn # of degrees at specified speed.  negative degrees is left
    void spin (double speed, float degrees) {
        float currentAngle = gyro.resetHeading();
        float goalAngle = currentAngle + degrees;
        // turn until we have made it
        if (degrees < 0) { // spinning left
            leftMotor.setPower(-speed);
            rightMotor.setPower(speed);
            while ((currentAngle > goalAngle) && (opMode.opModeIsActive())) {
                telemetry.addData("turning left", currentAngle);
                currentAngle = gyro.getHeading();
                telemetry.update();
            }
        } else { // spining right
            leftMotor.setPower(speed);
            rightMotor.setPower(-speed);
            while ((currentAngle < goalAngle) && (opMode.opModeIsActive())) {
                telemetry.addData("turning right", currentAngle);
                currentAngle = gyro.getHeading();
                telemetry.update();
            }
            rightMotor.setPower(0);
            leftMotor.setPower(0);
        }
    }
}