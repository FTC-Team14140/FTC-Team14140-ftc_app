package org.firstinspires.ftc.teamcode.teamLibs;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class linearActuater {

    private DcMotor motor;
    private Telemetry telemetry;

    private final int GROUND_POSITION=25800;


    public linearActuater(Telemetry thetelemetry, DcMotor motor1) {
        telemetry = thetelemetry;
        motor = motor1;
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    public void lowerRobot() {

        retractFully();
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendActuater(1, GROUND_POSITION);
    }

    public void retractFully() {
        int lastPosition;

        motor.setPower(-1);
        lastPosition = motor.getCurrentPosition();
        teamUtil.sleep(100);
        while (lastPosition != motor.getCurrentPosition()) {
            lastPosition=motor.getCurrentPosition();
            teamUtil.sleep(100);
        }
        motor.setPower(0);

        }
     public void extendForLatch()

    {
        extendActuater(-1, GROUND_POSITION);
    }




    public void lift() {
        //lift's the liner actuater
        motor.setPower(1);
        telemetry.addData("actuaterPosition", motor.getCurrentPosition());


    }

        private void extendActuater(double power, int encoderPosition)  {

            motor.setPower(power);
            motor.setTargetPosition(encoderPosition);
            while (motor.isBusy()) {

            }
            motor.setPower(0);
        }


        public void stop () {
        //stop's the liner actuater
        motor.setPower(0);
        telemetry.addData ( "actuaterPosition", motor.getCurrentPosition());





    }


}








