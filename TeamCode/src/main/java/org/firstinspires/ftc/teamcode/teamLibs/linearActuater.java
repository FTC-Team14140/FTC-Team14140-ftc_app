package org.firstinspires.ftc.teamcode.teamLibs;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class linearActuater {

    private DcMotor motor;
    private Telemetry telemetry;

    public linearActuater(Telemetry thetelemetry,DcMotor motor1){
        telemetry=thetelemetry;
        motor=motor1;

    }

    public void lift () {
        //lift's the liner actuater
        motor.setPower(1);
        telemetry.addData ( "actuaterPosition", motor.getCurrentPosition());

    }

    public void drop (){
        //drop's the liner actuater
        motor.setPower(-1);
        telemetry.addData ( "actuaterPosition", motor.getCurrentPosition());

    }

    public void stop () {
        //stop's the liner actuater
        motor.setPower(0);
        telemetry.addData ( "actuaterPosition", motor.getCurrentPosition());
    }

}








