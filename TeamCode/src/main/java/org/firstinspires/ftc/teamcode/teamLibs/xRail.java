package org.firstinspires.ftc.teamcode.teamLibs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class xRail {
    DcMotor motor;
    Telemetry telemetry;

    private final int EXTEND_ACTUAL = 810;
    private final int EXTEND_TARGET = EXTEND_ACTUAL+80;
    private final double TIMEOUT_SECONDS = 5;

    //private final int RETRACT = ;

    public xRail(Telemetry thetelemetry,DcMotor motor1){
        telemetry=thetelemetry;
        motor=motor1;

    }

    public void init () {

        // use BRAKE mode to control retraction
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // wind up any slack in the string
        motor.setPower(.07);
        teamUtil.sleep(1000);
        motor.setPower(0);

        // reset the encoder and get ready to lift
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void  extend () {
        motor.setPower(-0.50);
        telemetry.addData("xRailPosition",motor.getCurrentPosition());
    }
    public void retract () {
        motor.setPower(1);
        telemetry.addData("xRailPosition",motor.getCurrentPosition());
    }
    public void stop () {
        motor.setPower(-.15);
        telemetry.addData("xRailPosition",motor.getCurrentPosition());
    }
    public void loadLander() {

        ElapsedTime runtime = new ElapsedTime();

        // lift and wait for it to get to the top
        runtime.reset();
        motor.setTargetPosition(EXTEND_TARGET);
        motor.setPower(0.50);
        while ((runtime.seconds() < TIMEOUT_SECONDS) && (motor.getCurrentPosition()<EXTEND_ACTUAL)) {
            telemetry.addData("xrail", motor.getCurrentPosition());
            telemetry.update();
        }

        // drop it back down gently
        motor.setPower(0); // brake mode will slow it down
        motor.setTargetPosition(0); // slowly back up to starting spot
        motor.setPower(-.15);
        while ((runtime.seconds() < TIMEOUT_SECONDS) && (motor.getCurrentPosition()>5)) {
            telemetry.addData("xrail", motor.getCurrentPosition());
            telemetry.update();
        }
        motor.setPower(0); // otherwise we will drain the battery...
    }
}
