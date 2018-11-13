package org.firstinspires.ftc.teamcode.teamLibs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class xRail {
    DcMotor motor;
    Telemetry telemetry;

    public xRail(Telemetry thetelemetry,DcMotor motor1){
        telemetry=thetelemetry;
        motor=motor1;

    }
    //private final int EXTEND = ;
    //private final int RETRACT = ;

    public void  extend () {
        motor.setPower(1);
        telemetry.addData("xRailPosition",motor.getCurrentPosition());
    }
    public void retract () {
        motor.setPower(-1);
        telemetry.addData("xRailPosition",motor.getCurrentPosition());
    }
    public void stop () {
        motor.setPower(0);
        telemetry.addData("xRailPosition",motor.getCurrentPosition());
    }
}
