package org.firstinspires.ftc.teamcode.teamLibs;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class revHubIMUGyro {

    private Telemetry telemetry;
    private BNO055IMU imu;
    private Float currentDirection;

    Orientation anglesCurrent;

    revHubIMUGyro (BNO055IMU theimu,Telemetry theTelemetry){
        telemetry = theTelemetry;
        imu = theimu;
        currentDirection = 0;
    }


    float resetHeading() {


    }

    float getHeading() {
        anglesCurrent = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

    }


}
