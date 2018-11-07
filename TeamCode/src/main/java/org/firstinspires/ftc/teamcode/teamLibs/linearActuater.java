package org.firstinspires.ftc.teamcode.teamLibs;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class linearActuater {

    DcMotor motor;
    Telemetry telemetry;

    public linearActuater(Telemetry thetelemetry,DcMotor motor1){
        telemetry=thetelemetry;
        motor=motor1;

    }

    public void lift (){
        //lift the robot off the ground
    }
}
