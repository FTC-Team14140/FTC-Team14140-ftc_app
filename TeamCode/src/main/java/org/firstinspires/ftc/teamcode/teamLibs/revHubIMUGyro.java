package org.firstinspires.ftc.teamcode.teamLibs;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/* Trey: We could use some nice comments here explaining what this class is and how
   you expect people to use it.  Provide some simple use cases that explain to someone
   else how they should use this class in their code
 */

public class revHubIMUGyro {

    //Trey: Need some comments here explaining what these variables are for
    private Telemetry telemetry;
    private BNO055IMU imu;
    private int currentDirection;
    float currentHeading;
    Orientation anglesCurrent;
    Orientation anglesLast;




    public revHubIMUGyro (BNO055IMU theimu, Telemetry theTelemetry){
        telemetry = theTelemetry;
        imu = theimu;
        currentDirection = 0;

        // set up our IMU
        //These are the parameters that the imu uses in the code to name and keep track of the data
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imu.initialize(parameters);
    }



     //Trey: Need a comment here explaining what this method does
     public float resetHeading() {
        currentHeading = 0;
        anglesLast = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        //The code resets the current heading to 0 and keep wwhat the current angle is as you reset it as angles last to
        //use in the code in the future
        return currentHeading;
    }

    //Trey: Need a comment here explaining what this method does
    public float getHeading() {
        anglesCurrent = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        telemetry.addData("IMU Heading", anglesCurrent.firstAngle);
        // if we were near the cutoff between the positive and negitive angles
        // and appear to have turned between the positive and negitive sides to the right
        // than see if the last amgle is greater than or equal to 90 and the current angle is less than 0 than do
        // the math to figure out how much degress the robot has turned between the postive and negitive numbers
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
                // Note: The IMU heading gets larger to the left and smaller to the right.

            currentHeading = currentHeading - (anglesCurrent.firstAngle - anglesLast.firstAngle);
        }
        anglesLast = anglesCurrent;

        return currentHeading;
    }


}