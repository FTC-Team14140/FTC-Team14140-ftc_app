package org.firstinspires.ftc.teamcode.teamLibs;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class revHubIMUGyro {

    private Telemetry telemetry;
    private BNO055IMU imu;
    private int currentDirection;
    float currentHeading;
    Orientation anglesCurrent;
    Orientation anglesLast;




    revHubIMUGyro (BNO055IMU theimu,Telemetry theTelemetry){
        telemetry = theTelemetry;
        imu = theimu;
        currentDirection = 0;
        // set up our IMU
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imu.initialize(parameters);
    }


    float resetHeading() {
        currentHeading = 0;
        anglesLast = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        return currentHeading;
    }

    float getHeading() {
        anglesCurrent = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        telemetry.addData("Heading", anglesCurrent.firstAngle);
        // if we were near the cutoff between the positive and negitive angles
        // and appear to have turned between the positive and negitive sides to the right
        //than see if the last amgle is greater than or equal to 90 and the current angle is less than 0 than do
        //the math to figure out how much degress the robot has turned between the postive and negitive numbers
        if ((anglesLast.firstAngle >= 90) && (anglesCurrent.firstAngle < 0)) {
            currentHeading = currentHeading + (180 - anglesLast.firstAngle) + (anglesCurrent.firstAngle + 180);
            // if we were near the cutoff and between the positive and negitive angles
            // and appear to have turned between the positive and negitive sides to the left
            //than see if the last angle is less than -90 and the current angle is greater than 0 than do
            //the math to figure out how much degress the robot has turned between the postive and negitive numbers
        } else if ((anglesLast.firstAngle < -90) && (anglesCurrent.firstAngle > 0)) {
            currentHeading = currentHeading - (180 - anglesCurrent.firstAngle) - (anglesLast.firstAngle + 180);

        } else { // if the angles has not jumped between the positive and negitive than just so the math to subtract the current
                 //angle and the last angle to find the number between these values to find the current heading of the IMU

            currentHeading = currentHeading + (anglesCurrent.firstAngle - anglesLast.firstAngle);
        }
        anglesLast = anglesCurrent;

        return currentHeading;
    }


}
