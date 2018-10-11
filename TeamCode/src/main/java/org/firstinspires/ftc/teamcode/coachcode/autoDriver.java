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