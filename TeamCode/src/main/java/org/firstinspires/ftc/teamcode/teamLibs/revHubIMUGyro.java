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
        anglesLast = anglesCurrent;

        return currentHeading;
    }


}
