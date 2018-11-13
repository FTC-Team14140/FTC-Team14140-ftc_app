package org.firstinspires.ftc.teamcode.teamLibs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;

public class basicMovement {

    DcMotor motorLeft;
    DcMotor motorRight;

    private double     COUNTS_PER_MOTOR_REV = 1120 ;
    private double     WHEEL_DIAMETER_INCHES =  4;
    private double     COUNTS_PER_INCH = COUNTS_PER_MOTOR_REV / (WHEEL_DIAMETER_INCHES * 3.1415);
    //private double     DRIVE_SPEED = ;
    //private double     TURN_SPEED = ;

    public void moveInches (double speed, int inches) {
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorLeft.setTargetPosition(inches);
        motorRight.setTargetPosition(inches);

        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(motorLeft.isBusy() && motorRight.isBusy())
        {

        }
        motorLeft.setPower(0);
        motorRight.setPower(0);

        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void rightTurn (double speed, double degrees) {

    }
    public void leftTurn (double speed, double degrees) {

    }
    public void leftSpin (double speed, double degrees) {

    }
    public void rightSpin (double speed, double degrees) {

    }

}
