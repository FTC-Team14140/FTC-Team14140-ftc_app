package org.firstinspires.ftc.teamcode.teamLibs;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class distanceSensors {
    private Telemetry telemetry;
    private Rev2mDistanceSensor rev2mDistanceSensor;


    public distanceSensors(Telemetry thetelemetry, Rev2mDistanceSensor theDistanceSensor) {
            telemetry = thetelemetry;
            rev2mDistanceSensor = theDistanceSensor;


    }

    public double getDistance() {
        double distance = rev2mDistanceSensor.getDistance(DistanceUnit.INCH);
        return distance;


    }



}
















