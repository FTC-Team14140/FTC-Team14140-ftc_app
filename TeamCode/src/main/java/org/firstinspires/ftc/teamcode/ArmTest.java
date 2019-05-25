package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.teamLibs.goBuildAServo2000;


import org.firstinspires.ftc.teamcode.teamLibs.HiTecServo;
import org.firstinspires.ftc.teamcode.teamLibs.basicMovement;
import org.firstinspires.ftc.teamcode.teamLibs.teamUtil;

@TeleOp(name="ArmTest", group="Linear Opmode")
public class ArmTest extends LinearOpMode {
    private DcMotor BaseMotor;
    private DcMotor joint2Motor;
    private HiTecServo TurnTable;
    private goBuildAServo2000 grabber;

    public void runOpMode() {
        teamUtil.log("Gettign motor 0 from hardware map");
        BaseMotor = hardwareMap.get(DcMotor.class, "motor0");
        teamUtil.log("Gettign motor 1 from hardware map");
        joint2Motor = hardwareMap.get(DcMotor.class, "motor1");
        grabber = new goBuildAServo2000(hardwareMap.get(Servo.class, "Servo1"), telemetry);
        TurnTable = new HiTecServo(hardwareMap.get(Servo.class, "Servo0"), telemetry);

        int gDegrees = 215; // start open
        int tDegrees = 75; // start at middle
        TurnTable.goTo(tDegrees);
        grabber.goTo(gDegrees);

        joint2Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        joint2Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        telemetry.addData("Status", "Waiting for Start...");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {

            //base motor
            if (gamepad2.right_bumper) {
                BaseMotor.setPower(0.5);
                telemetry.addData("moving base", .5);
            } else if (gamepad2.left_bumper) {
                BaseMotor.setPower(-0.5);
                telemetry.addData("moving base", -.5);
            } else {
                BaseMotor.setPower(0);
            }

            //arm motor
            if (gamepad2.x) {
                joint2Motor.setPower(0.3);
                telemetry.addData("moving arm", .3);
            } else if (gamepad2.b) {
                joint2Motor.setPower(-0.3);
                telemetry.addData("moving arm", -.3);
            } else {
                joint2Motor.setPower(0);
            }

            //grabber Servo
            if (gamepad2.dpad_right) {
                grabber.goTo(250);
            } else if (gamepad2.dpad_left) {
                grabber.goTo(215);
            }

            //turn table
            if (gamepad2.a) {
                tDegrees--;
                TurnTable.goTo(tDegrees);
                teamUtil.sleep(50);
            } else if (gamepad2.y){
                tDegrees++;
                TurnTable.goTo(tDegrees);
                teamUtil.sleep(50);
            }


            telemetry.addData("gDegrees", gDegrees);
            telemetry.addData("tDegrees", tDegrees);
            telemetry.update();

        }
    }
}
