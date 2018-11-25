package org.firstinspires.ftc.teamcode.teamLibs;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class teamColorSensor {
    private ColorSensor colorSensor;
    private Telemetry telemetry;

    public teamColorSensor(Telemetry theTelemetry, ColorSensor theColorSensor) {
        telemetry = theTelemetry;
        colorSensor = theColorSensor;
    }

    public void detectColor() {


    }

    public void calibrate() {


    }
    public boolean onBlue() {
        return false;
    }
    public boolean onRed() {
        return false;



    }
    public int redValue() {
        return colorSensor.red();
    }





    public int blueValue() {
        return colorSensor.blue();
    }





}







