package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.teamLibs.teamUtil;


@TeleOp(name="Dev Control", group="Linear Opmode")
public class DevControl extends LinearOpMode {

    private DcMotor motor0;
    private DcMotor motor1;


    @Override
    public void runOpMode() {
        // Get the objects for the various pieces of hardware
        teamUtil.theOpMode = this;

        teamUtil.log("Gettign motor 0 from hardware map");
        motor0 = hardwareMap.get(DcMotor.class, "motor0");

        teamUtil.log("Gettign motor 1 from hardware map");
        motor1 = hardwareMap.get(DcMotor.class, "motor1");

        telemetry.addData("Status", "Waiting for Start...");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        teamUtil.log("waiting for start");
        waitForStart();

        while (opModeIsActive()) {
            double speed = -this.gamepad1.left_stick_y;
            if ((speed < -.1) || (speed > .1)) {
                motor0.setPower(-speed);
            } else {
                motor0.setPower(0);
            }
            speed = -this.gamepad1.right_stick_y;
            if ((speed < -.1) || (speed > .1)) {
                motor1.setPower(speed);
            } else {
                motor1.setPower(0);
            }

        }
    }
}